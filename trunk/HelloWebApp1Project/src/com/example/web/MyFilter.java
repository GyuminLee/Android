package com.example.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class MyFilter
 */
public class MyFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public MyFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		mContext.log("RequestUrl : " + httpRequest.getRequestURL()
				+ ", User : " + httpRequest.getRemoteUser() + ", Date : "
				+ new Date());
		FilterStringRequest requestWrapper = new FilterStringRequest(httpRequest);
		CharArrayWrapper responseWrapper = new CharArrayWrapper((HttpServletResponse)response);
		chain.doFilter(requestWrapper, responseWrapper);
		String text = responseWrapper.toString();
		text.replace("One", "1");
		response.getWriter().print(text);
	}

	ServletContext mContext;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		mContext = fConfig.getServletContext();
	}

}
