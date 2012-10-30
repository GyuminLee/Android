package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.rdbms.AppEngineDriver;


public class InsertUserId extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		Connection c = null;
		try {
			DriverManager.registerDriver(new AppEngineDriver());
			c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
			String userid = req.getParameter("userid");
			String password = req.getParameter("password");
			String name = req.getParameter("name");
			String registrationId = req.getParameter("registrationId");
			if (userid == null || password == null || name == null || registrationId == null) {
				out.println("<html><head></head><body>error</body></html>");
			} else {
				String statement = "INSERT INTO usertbl (userid,password,name,registrationId) VALUES(?,?,?,?)";
				PreparedStatement stmt = c.prepareStatement(statement);
				stmt.setString(1, userid);
				stmt.setString(2, password);
				stmt.setString(3, name);
				stmt.setString(4, registrationId);
				int success = stmt.executeUpdate();
				if (success == 1) {
					out.println("<html><head></head><body>success</body></html>");
				} else {
					out.println("<html><head></head><body>fail</body></html>");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
