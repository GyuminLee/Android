package org.tacademy.web.hellowebapp;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class TestUserInfoUpload extends HttpServlet {
	  private BlobstoreService blobstoreService =
			    BlobstoreServiceFactory.getBlobstoreService();

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		    String uploadURL = blobstoreService.createUploadUrl("/logininfoupload");
		    req.setAttribute("uploadURL", uploadURL);
		    RequestDispatcher dispatcher = 
		        req.getRequestDispatcher("userinfoupload.jsp");
		      dispatcher.forward(req, resp);
		    
		}
}
