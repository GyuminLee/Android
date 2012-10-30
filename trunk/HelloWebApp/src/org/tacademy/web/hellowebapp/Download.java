package org.tacademy.web.hellowebapp;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class Download extends HttpServlet {
	  private BlobstoreService blobstoreService =
		    BlobstoreServiceFactory.getBlobstoreService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Image image = null;
	    ImagesService imagesService = ImagesServiceFactory.getImagesService();
		BlobKey blobKey = new BlobKey(req.getParameter("key"));
	    image = ImagesServiceFactory.makeImageFromBlob(blobKey);

//	    PersistenceManager pm = PMF.get().getPersistenceManager();

//	    Query query = pm.newQuery(MediaObject.class, "blob == blobParam");
//	    query.declareImports("import " +
//	      "com.google.appengine.api.blobstore.BlobKey");
//	    query.declareParameters("BlobKey blobParam");
//
//	    List<MediaObject> results = (List<MediaObject>) query.execute(blobKey);
//	    if (results.isEmpty()) {
//	        resp.getWriter().println("<html><head></head><body>error</body></html>");
//	      return;
//	    }
//
//	    MediaObject result = results.get(0);
//
//	    String rotation = req.getParameter("rotate");
//	    if (rotation != null && !"".equals(rotation) && !"null".equals(rotation)) {
//	      int degrees = Integer.parseInt(rotation);
//
	      int width = 50;
	      int height = 50;
	      if (req.getParameter("width") != null) {
	    	  width = Integer.parseInt(req.getParameter("width"));
	      }
	      if (req.getParameter("height") != null) {
	    	  height = Integer.parseInt(req.getParameter("height"));
	      }
////	      Transform rotate = ImagesServiceFactory.makeRotate(degrees);
//	      Image newImage = image;
//	      if (req.getParameter("width") != null && req.getParameter("height") != null) {
//	    	  int width = Integer.parseInt(req.getParameter("width"));
//	    	  int height = Integer.parseInt(req.getParameter("height"));
	    	  Transform resize = ImagesServiceFactory.makeResize(width, height);
	    	  Image newImage = imagesService.applyTransform(resize, image,ImagesService.OutputEncoding.JPEG);
//	      }
	      byte[] imgbyte = newImage.getImageData();
//
	      resp.setContentType("image/jpeg");
	      resp.getOutputStream().write(imgbyte);
//	      return;
//	    }
//	    blobstoreService.serve(blobKey, resp);
	}

}
