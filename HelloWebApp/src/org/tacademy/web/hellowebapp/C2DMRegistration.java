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

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class C2DMRegistration extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService userservice = UserServiceFactory.getUserService();
		User user = userservice.getCurrentUser();
		Connection c = null;
		String result = "Fail";
		int resultid = -1;
		String userid = null;
		String registrationId = req.getParameter("regid");
		if (user != null) {
			userid = user.getEmail();
		} else {
			userid = req.getParameter("email");
		}

		try {
			DriverManager.registerDriver(new AppEngineDriver());
			c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");

			String statement = "UPDATE usertbl SET registrationid = ? WHERE userid = ?";
			PreparedStatement upstmt = c.prepareStatement(statement);
			upstmt.setString(1, registrationId);
			upstmt.setString(2, userid);
			upstmt.executeUpdate();
			result = "Success";			
		} catch (SQLException e) {
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
		doGet(req,resp);
	}

}
