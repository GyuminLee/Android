package org.tacademy.web.hellowebapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationDelete extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		RegistrationItem item = new RegistrationItem();
		item.id = (long)id;
		RegistrationJDO db = new RegistrationJDO();
		db.removeRegistrationItem(item);
		resp.setContentType("text/plain");
		resp.getWriter().println("delete ok");
	}

}
