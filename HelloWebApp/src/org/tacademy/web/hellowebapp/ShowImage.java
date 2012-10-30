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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ShowImage extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String blobKeyString = req.getParameter("key");
	    if (blobKeyString == null || blobKeyString.equals("")) {
	        resp.getWriter().println("<html><head></head><body>error</body></html>");
	      return;
	    }

	    BlobKey blobKey = new BlobKey(blobKeyString);
	    PersistenceManager pm = PMF.get().getPersistenceManager();

	    Query query = pm.newQuery(MediaObject.class, "blob == blobParam");
	    query.declareImports("import " +
	      "com.google.appengine.api.blobstore.BlobKey");
	    query.declareParameters("BlobKey blobParam");

	    List<MediaObject> results = (List<MediaObject>) query.execute(blobKey);
	    if (results.isEmpty()) {
	        resp.getWriter().println("<html><head></head><body>error</body></html>");
	      return;
	    }

	    MediaObject result = results.get(0);

	    String rotation = req.getParameter("rotate");
	    String displayURL = result.getURLPath() + "&rotate=" + rotation;

	    req.setAttribute("displayURL", displayURL);
	    req.setAttribute("rotation", rotation);
	    req.setAttribute("item", result);
	    req.setAttribute("blobkey", blobKeyString);

	    RequestDispatcher dispatcher =
	      req.getRequestDispatcher("display.jsp");
	    dispatcher.forward(req, resp);
	}

}
