package com.example.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyFirstSevlet
 */
@WebServlet(
		urlPatterns = { "/myfirstservlet" }, 
		initParams = { 
				@WebInitParam(name = "param1", value = "value1"), 
				@WebInitParam(name = "param2", value = "value2")
		})
public class MyFirstSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyFirstSevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param1 = request.getParameter("param1");
		String ageStr = request.getParameter("age");
		int age = Integer.parseInt(ageStr);
		
		
		response.setContentType("text/plain;charset=euc-kr");
		response.getWriter().println("¾È³ç, Servlet");
		response.getWriter().println("Param1 : " + param1);
		response.getWriter().println("age : " + age);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
