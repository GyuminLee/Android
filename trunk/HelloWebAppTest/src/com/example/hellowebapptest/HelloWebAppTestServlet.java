package com.example.hellowebapptest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

import com.example.shared.MyServerMethod;

@SuppressWarnings("serial")
public class HelloWebAppTestServlet extends HttpServlet {
	JsonRpcExecutor executor;
	public HelloWebAppTestServlet() {
		executor = new JsonRpcExecutor();
		MyServerMethodImpl impl = new MyServerMethodImpl();
		executor.addHandler("mymethod", impl, MyServerMethod.class);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		executor.execute(new JsonRpcServletTransport(req, resp));
	}
}
