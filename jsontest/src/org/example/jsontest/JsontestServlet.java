package org.example.jsontest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.jsontest.shared.Calculator;
import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

@SuppressWarnings("serial")
public class JsontestServlet extends HttpServlet {
	
	private final JsonRpcExecutor executor;
	
	public JsontestServlet() {
		executor = bind();
	}
	
	private JsonRpcExecutor bind() {
		JsonRpcExecutor executor = new JsonRpcExecutor();
		
		Calculator calcImpl = new SimpleCalculatorImpl();
		
		executor.addHandler("calc", calcImpl, Calculator.class);
		
		return executor;
		
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
