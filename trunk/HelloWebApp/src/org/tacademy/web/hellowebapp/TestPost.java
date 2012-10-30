package org.tacademy.web.hellowebapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestPost extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		Enumeration en = req.getHeaderNames();
		while(en.hasMoreElements()) {
			String key = (String)en.nextElement();
			resp.getWriter().println(key + " : " + req.getHeader(key));
		}
		InputStream is = req.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String str;
		while(( str = br.readLine()) != null) {
			resp.getWriter().println(str);
		}
	}

}
