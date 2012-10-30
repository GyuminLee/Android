package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class UploadFile extends HttpServlet {

	  private BlobstoreService blobstoreService = 
		    BlobstoreServiceFactory.getBlobstoreService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  //  Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
	    Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
	    String result;

	    if (blobs.keySet().isEmpty()) {
	    	result = "Fail";
	    } else {
		    Iterator<String> names = blobs.keySet().iterator();
		    String blobName = names.next();
//		    List<BlobKey> blobKeys = blobs.get(blobName);
//		    BlobKey blobKey = blobKeys.get(0);
		    BlobKey blobKey = blobs.get(blobName);
		    
		    BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		    BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
	
		    String contentType = blobInfo.getContentType();
		    long size = blobInfo.getSize();
		    Date creation = blobInfo.getCreation();
		    String fileName = blobInfo.getFilename();
	
		    String title = req.getParameter("title");
		    String description = req.getParameter("description");
	
		    try {
		        MediaObject mediaObj = new MediaObject(blobKey, creation, 
		          contentType, fileName, size, title, description);
		        PMF.get().getPersistenceManager().makePersistent(mediaObj);
		        result = "Success";
		    } catch (Exception e) {
		        blobstoreService.delete(blobKey);
		        result = "Fail";
		    }
	    }

	    req.setAttribute("result", result);
	    try {
            req.getRequestDispatcher("/UploadResult.jsp").forward(req, resp);
        } catch (ServletException e) {
        }
	}

}
