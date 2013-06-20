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
			String url = mRequest.getRequestURI();
			int startIndex = url.indexOf("/board/");
			startIndex += 7;
			String sub = url.substring(startIndex);
			int endIndex = sub.indexOf("/");
			String id = sub.substring(0, endIndex - 1);
			return id;
		}
		return value;
	}

}
