package com.example.myfirstweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

import com.example.shared.GCMSend;

@SuppressWarnings("serial")
public class HelloMyFirstWebServlet extends HttpServlet {
	JsonRpcExecutor executor;
	public HelloMyFirstWebServlet() {
		executor = new JsonRpcExecutor();
		
		executor.addHandler("gcm", new GCMSendImpl(), GCMSend.class);
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
