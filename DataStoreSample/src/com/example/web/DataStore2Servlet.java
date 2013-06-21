package com.example.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;

public class DataStore2Servlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity test = new Entity("TEST");
		test.setProperty("param1","value1");
		EmbeddedEntity subtest = new EmbeddedEntity();
		subtest.setProperty("subparam1", "subvalue1");
		test.setProperty("sub", subtest);
		datastore.put(test);
		resp.setContentType("text/plain");
		resp.getWriter().println("OK");
		
		
	}
}
