package com.example.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class BasicAuthServlet
 */
@WebServlet("/basicauth")
public class BasicAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BasicAuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		if (authorization == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setHeader("WWW-Authenticate", "BASIC realm=\"Intranet\"");
		} else {
			String userinfo = authorization.substring(6).trim();
			BASE64Decoder decoder = new BASE64Decoder();
			String nameAndPassword = new String(decoder.decodeBuffer(userinfo));
			int index = nameAndPassword.indexOf(":");
			String user = nameAndPassword.substring(0, index);
			String password = nameAndPassword.substring(index + 1);
			if (isLogin(user, password)) {
				response.setContentType("text/plain");
				response.getWriter().println("login Success");
			} else {
				response.setContentType("text/plain");
				response.getWriter().println("login Fail");
			}
		}
	}
	
	private boolean isLogin(String user,String password) {
		if (user.equals("dongja94")) return true;
		return false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
