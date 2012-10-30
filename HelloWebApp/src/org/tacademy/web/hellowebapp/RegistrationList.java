package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationList extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<HTML>");
		resp.getWriter().println("<HEAD><TITLE>Grid List</TITLE></HEAD>");
		resp.getWriter().println("<BODY>");
		resp.getWriter().println("<H3>Grid List</H3>");
		resp.getWriter().println("<TABLE border=\"1\">");
		RegistrationJDO db = new RegistrationJDO();
		List<RegistrationItem> items = db.listRegistrationItems();
		for (int i = 0; i < items.size(); i++) {
			RegistrationItem item = items.get(i);
			resp.getWriter().println("<TR>");
			resp.getWriter().println("<TD>" + item.name + "</TD>");
			resp.getWriter().println("<TD>" + item.registrationId + "</TD>");
			resp.getWriter().println("<TD>" + "<A HREF=\"registrationdelete?id="+item.id+"\">Delete</A>" + "</TD>");
			resp.getWriter().println("</TR>");
		}
		resp.getWriter().println("</TABLE>");
		resp.getWriter().println("</BODY>");
		resp.getWriter().println("</HTML>");
	}

}
