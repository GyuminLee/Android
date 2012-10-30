package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Registration extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String regid = req.getParameter("regid");
		RegistrationItem item = new RegistrationItem();
		item.name = name;
		item.registrationId = regid;
		RegistrationJDO db = new RegistrationJDO();
		db.addRegistrationItem(item);
		resp.setContentType("text/xml");
		PrintWriter writer = resp.getWriter();
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writer.println("<Result>");
		writer.println("\t<ResultCode>1</ResultCode>");
		writer.print("</Result>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
	
}
