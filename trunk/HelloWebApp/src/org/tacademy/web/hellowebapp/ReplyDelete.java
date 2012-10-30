package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ReplyDelete extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		Connection c = null;
		ResultSet rs = null;
		String result = "Fail";
		int resultid = -1;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			int id = Integer.parseInt(req.getParameter("id"));
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
					String statement = "DELETE FROM replytbl WHERE id = ? AND userid = ?";
					PreparedStatement dlstmt = c.prepareStatement(statement);
					dlstmt.setInt(1, id);
					dlstmt.setInt(2, userid);
					int count = dlstmt.executeUpdate();
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
