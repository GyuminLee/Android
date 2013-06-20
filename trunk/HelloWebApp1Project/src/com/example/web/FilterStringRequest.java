package com.example.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class FilterStringRequest extends HttpServletRequestWrapper {

	HttpServletRequest mRequest;
	
	public FilterStringRequest(HttpServletRequest request) {
		super(request);
		mRequest = request;
	}
	
	@Override
	public String getParameter(String name) {
		if (name.equals("MYID")) {
			HttpSession session = mRequest.getSession();
			String myId = (String)session.getAttribute("MYID");
			return myId;
		}
		String value = super.getParameter(name);
		String result = value;
		if (value != null && value.startsWith("I")) {
			result = "You" + value.substring(2);
		}
		return result;
	}

}
