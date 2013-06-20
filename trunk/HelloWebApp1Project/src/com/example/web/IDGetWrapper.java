package com.example.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class IDGetWrapper extends HttpServletRequestWrapper {
	HttpServletRequest mRequest;
	public IDGetWrapper(HttpServletRequest request) {
		super(request);
		mRequest = request;;
	}
	
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (name.equals("id")) {
			String url = mRequest.getRequestURL().toString();
			int endIndex = url.indexOf("/");
			int startIndex = endIndex;
			while(endIndex != -1) {
				startIndex = endIndex + 1;
				endIndex = url.indexOf("/", startIndex);
			}			
			String id = url.substring(startIndex);
			return id;
		}
		return value;
	}

}
