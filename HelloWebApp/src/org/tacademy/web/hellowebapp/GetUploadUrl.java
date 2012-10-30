package org.tacademy.web.hellowebapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class GetUploadUrl extends HttpServlet {
	private BlobstoreService blobstoreService =
		    BlobstoreServiceFactory.getBlobstoreService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");
		String uploadURL = null;
		if (type.equals("test")) {
			uploadURL = blobstoreService.createUploadUrl("/uploadfile");
		} else if (type.equals("login")) {
			uploadURL = blobstoreService.createUploadUrl("/logininfoupload");			
		} else if (type.equals("boardinsert")) {
			uploadURL = blobstoreService.createUploadUrl("/boardinsert");			
		} else if (type.equals("boardupdate")) {
			uploadURL = blobstoreService.createUploadUrl("/boardupdate");		
		} else { // default "test"
			uploadURL = blobstoreService.createUploadUrl("/uploadfile");
		}

	    resp.setContentType("text/xml");
	    
	    PrintWriter writer = resp.getWriter();
	    
	    writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    writer.println("<uploadUrl>"+uploadURL+"</uploadUrl>");
	}

}
