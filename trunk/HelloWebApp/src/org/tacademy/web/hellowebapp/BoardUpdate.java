package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class BoardUpdate extends HttpServlet {
	  private BlobstoreService blobstoreService = 
			    BlobstoreServiceFactory.getBlobstoreService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		Connection c = null;
		ResultSet rs = null;
		String result = "Fail";
		String boardImageKey = "";
		int resultid = -1;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			Map<String,List<BlobKey>> blobs = blobstoreService.getUploads(req);
			Iterator<String> names = blobs.keySet().iterator();
			while(names.hasNext()) {
				String name = names.next();
				if (name.equals("boardImage")) {
					List<BlobKey> blobkeys = blobs.get(name);
					if (blobkeys.size() > 0) {
						BlobKey blobkey = blobkeys.get(0);
						boardImageKey = blobkey.getKeyString();
					}
					break;
				}
			}
			int id = Integer.parseInt(req.getParameter("id"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			try {
				DriverManager.registerDriver(new AppEngineDriver());
				c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
				if (session.getAttribute("userid") == null) {
					String query = "SELECT id FROM usertbl WHERE userid = ?";
					PreparedStatement stmt = c.prepareStatement(query);
					stmt.setString(1, user.getEmail());
					rs = stmt.executeQuery();
					if (rs.next()) {
						session.setAttribute("userid", rs.getInt("id"));
					}
				}
				if (session.getAttribute("userid")!=null) {
					// update user info
					int userid = (Integer)session.getAttribute("userid");
					String statement = null;
					if (boardImageKey.equals("")) {
						statement = "UPDATE boardtbl SET title = ?, content = ? WHERE id = ? AND userid = ?";
					} else {
						statement = "UPDATE boardtbl SET title = ?, content = ? , imagekey = ? WHERE id = ? AND userid = ?";
					}
					PreparedStatement upstmt = c.prepareStatement(statement);
					upstmt.setString(1, title);
					upstmt.setString(2, content);
					if (boardImageKey.equals("")) {
						upstmt.setInt(3, id);
						upstmt.setInt(4, userid);
					} else {
						upstmt.setString(3, boardImageKey);
						upstmt.setInt(4, id);
						upstmt.setInt(5, userid);
					}
					int count = upstmt.executeUpdate();
					if (count == 1) {
						result = "Success";
						resultid = id;
					} else {
						result = "Fail";
					}
				} else {
					// insert user info
					result = "NotAdded";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "Fail";
			} finally {
				if (c != null) {
					try {
						c.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}				
			}
		} else {
			result = "NotLogin";
		}

	    req.setAttribute("result", result);
	    req.setAttribute("resultid", resultid);
	    try {
          req.getRequestDispatcher("/UploadResult.jsp").forward(req, resp);
        } catch (ServletException e) {
        }
	}

}
