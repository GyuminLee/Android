package com.example.hellomyfirstweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

import com.example.shared.MyWebInterface;

@SuppressWarnings("serial")
public class HelloMyFirstWebServlet extends HttpServlet {
	JsonRpcExecutor executor;
	MyWebInterfaceImpl mImpl;
	
	public HelloMyFirstWebServlet() {
		executor = new JsonRpcExecutor();
		mImpl = new MyWebInterfaceImpl();
		MyWebInterface inter = mImpl;
		executor.addHandler("myweb", inter, MyWebInterface.class);
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {		
		mImpl.sendAll();
		resp.setContentType("text/plain");
		resp.getWriter().println("Sended All Message");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		executor.execute(new JsonRpcServletTransport(req, resp));
	}
}
