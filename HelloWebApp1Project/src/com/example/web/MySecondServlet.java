package com.example.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.jws.soap.InitParam;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MySecondServlet
 */
@WebServlet(
		urlPatterns = {"/mysecondservlet"}, 
		initParams = {@WebInitParam(name="logfile", value="log.txt")}
		)
public class MySecondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ServletConfig mConfig;   
	PrintWriter writer;
	int count;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MySecondServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		mConfig = config;
		ServletContext context = config.getServletContext();
		String logfilename = config.getInitParameter("logfile");
		try {
			writer = new PrintWriter(context.getRealPath("/WEB-INF/" + logfilename));
			count = 0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		writer.close();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		writer.println("MySecondServlet called " + new Date());
		writer.flush();
		response.setContentType("text/plain;charset=euc-kr");
		response.getWriter().println("SecondServlit called " + count++);
		response.getWriter().println("init param : " + mConfig.getInitParameter("logfile"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
