package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.rdbms.AppEngineDriver;

public class BoardList extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		Connection c = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new AppEngineDriver());
			c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
			String startStr = req.getParameter("start");
			String countStr = req.getParameter("count");
			int start = 0;
			int count = 10;
			if (startStr != null && !startStr.equals("")) {
				start = Integer.parseInt(startStr);
			}
			if (countStr != null && !countStr.equals("")) {
				count = Integer.parseInt(countStr);
			}
			String sql = "SELECT id,title,content,imageurl,writer FROM boardtbl";
			rs = c.createStatement().executeQuery(sql);
			rs.last();
			int total = rs.getRow();
			resp.setContentType("text/xml");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<rss>");
			writer.println("<channel>");
			writer.println("<start>" + start + "</start>");
			writer.println("<count>" + count + "</count>");
			writer.println("<total>" + total + "</total>");
			
			int i;
			for (rs.relative(start), i = 0; i < count; rs.next(), i++) {
				writer.println("<item>");
				writer.println("<id>" + rs.getInt("id") + "</id>" );
				writer.println("<title>" + rs.getString("title") + "</title>");
				writer.println("<content>" + rs.getString("content") + "</content>");
				writer.println("<imageUrl>" + rs.getString("imageurl") + "</imageUrl>");
				writer.println("<writer>" + rs.getString("writer") + "</writer>");
				writer.println("</item>");
			}
			writer.println("</channel>");
			writer.print("</rss>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

}
