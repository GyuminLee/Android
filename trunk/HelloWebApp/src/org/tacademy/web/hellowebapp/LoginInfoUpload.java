package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class LoginInfoUpload extends HttpServlet {
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
		String userImageKey = "";
		int resultid = -1;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			Map<String,List<BlobKey>> blobs = blobstoreService.getUploads(req);
			Iterator<String> names = blobs.keySet().iterator();
			while(names.hasNext()) {
				String name = names.next();
				if (name.equals("userImage")) {
					List<BlobKey> blobkeys = blobs.get(name);
					if (blobkeys.size() > 0) {
						BlobKey blobkey = blobkeys.get(0);
						userImageKey = blobkey.getKeyString();
					}
					break;
				}
			}
			String name = req.getParameter("name");
			if (name == null || name.equals("") ) {
				name = "UserName";
			}
			String registrationId = req.getParameter("regid");
			if (registrationId == null) {
				registrationId = "";
			}
			try {
				DriverManager.registerDriver(new AppEngineDriver());
				c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
				String query = "SELECT id,imagekey FROM usertbl WHERE userid = ?";
				PreparedStatement stmt = c.prepareStatement(query);
				stmt.setString(1, user.getEmail());		
				rs = stmt.executeQuery();
				if (rs.next()) {
					// update user info
					int userid = rs.getInt("id");
					resultid = userid;
					if (userImageKey.equals("")) {
						userImageKey = rs.getString("imagekey");
					}
					rs.close();
					
					String statement = "UPDATE usertbl SET userid = ?, username = ?, imagekey = ?, registrationid = ? WHERE id = ?";
					PreparedStatement upstmt = c.prepareStatement(statement);
					upstmt.setString(1, user.getEmail());
					upstmt.setString(2, name);
					upstmt.setString(3, userImageKey);
					upstmt.setString(4, registrationId);
					upstmt.setInt(5, userid);
					upstmt.executeUpdate();
					if (session.getAttribute("userid") == null) {
						session.setAttribute("userid", userid);
					}
					result = "Success";
					resultid = userid;
				} else {
					// insert user info
					String statementFormat = "INSERT INTO usertbl(userid,username,imagekey,registrationid) VALUES('%s','%s','%s','%s')";
					String statement = String.format(statementFormat, user.getEmail(),name,userImageKey,registrationId);
					Statement instmt = c.createStatement();
					instmt.executeUpdate(statement,Statement.RETURN_GENERATED_KEYS);
					ResultSet rs2 = instmt.getGeneratedKeys();
					if (rs2.next()) {
						int userid = rs2.getInt(1);
						session.setAttribute("userid", userid);
						result = "Success";
						resultid = userid;
					} else {
						result = "Fail";
					}
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
