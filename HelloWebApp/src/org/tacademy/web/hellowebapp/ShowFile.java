package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;

public class ShowFile extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PersistenceManager pm = PMF.get().getPersistenceManager();
	    Query query = pm.newQuery(MediaObject.class);

	    // 만약 조건을 주어야 하는 경우는 아래와 같이 처리함.
//	    Query query = pm.newQuery(MediaObject.class, "owner == userParam");
//	    query.declareImports("import com.google.appengine.api.users.User");
//	    query.declareParameters("User userParam");
//	    List<MediaObject> results = (List<MediaObject>) query.execute(user);
	    
	    List<MediaObject> results = (List<MediaObject>) query.execute();

	    req.setAttribute("files", results);

	    RequestDispatcher dispatcher =
	      req.getRequestDispatcher("main.jsp");
	    dispatcher.forward(req, resp);
	    
	}

}
