package com.example.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class DataStoreSearchServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filter = new Query.FilterPredicate("firstName", FilterOperator.EQUAL, "Yon");
		Filter filter2 = new Query.FilterPredicate("lastName", FilterOperator.EQUAL, "SeungIk");
		Filter composeFilter = Query.CompositeFilterOperator.or(filter,filter2);
		Query q = new Query("EMPLOYEE").setFilter(composeFilter).addSort("firstName", Query.SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		resp.setContentType("text/plain");
		for (Entity result : pq.asIterable()) {
			String firstName = (String)result.getProperty("firstName");
			String lastName = (String)result.getProperty("lastName");
			resp.getWriter().println(" name : " +firstName + " " +lastName);
			Key key = result.getKey();
			result.setProperty("newfield", firstName + lastName);
			datastore.put(result);
			Query qq = new Query("ADDRESS").setAncestor(key);
			PreparedQuery pqq = datastore.prepare(qq);
			for (Entity rr : pqq.asIterable()) {
				resp.getWriter().println(" state : " + (String)rr.getProperty("state"));
			}
		}
	}
}
