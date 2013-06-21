package com.example.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class DataStoreSampleServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity employee = new Entity("EMPLOYEE");
		employee.setProperty("firstName", "Yon");
		employee.setProperty("lastName", "SeungIk");
		Date hireDate = new Date();
		employee.setProperty("hireDate", hireDate);
		employee.setProperty("training", true);
		datastore.put(employee);
		Entity address = new Entity("ADDRESS", employee.getKey());
		address.setProperty("state", "seoul");
		address.setProperty("locally", "123-4");
		datastore.put(address);
		
		resp.setContentType("text/plain");
		resp.getWriter().println("OK");
		
	}
}
