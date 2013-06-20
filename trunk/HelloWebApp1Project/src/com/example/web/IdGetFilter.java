package com.example.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class IdGetFilter
 */
@WebFilter({ "/board/*/boardshow.jsp", "/board/*/boardupdate",
		"/board/*/boarddelete", "/board/*/replyForm.jsp",
		"/board/*/replyinsert" })
public class IdGetFilter implements Filter {

	ServletContext mContext;

	/**
	 * Default constructor.
	 */
	public IdGetFilter() {
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
		IDGetWrapper wrapper = new IDGetWrapper((HttpServletRequest) request);
		chain.doFilter(wrapper, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		mContext = fConfig.getServletContext();
	}

}
