package com.example.hellogae;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

import com.example.shared.Calculator;

@SuppressWarnings("serial")
public class HelloGAEServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	JsonRpcExecutor executor;
	
	public HelloGAEServlet() {
		executor = new JsonRpcExecutor();
		CalculatorImpl impl = new CalculatorImpl();
		executor.addHandler("cals", impl, Calculator.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		executor.execute(new JsonRpcServletTransport(req, resp));
	}
}
