package org.tacademy.web.gcm;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Gcm_demo_appengine1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
