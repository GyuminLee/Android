package org.tacademy.web.hellowebapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class C2DMSend extends HttpServlet {

	String mAuthToken = null;
	private final static String EMAIL_ID = "dongja94@gmail.com";
	private final static String EMAIL_PW = "ehdwk941";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String message = req.getParameter("message");
		RegistrationJDO db = new RegistrationJDO();
		List<RegistrationItem> list = db.listRegistrationItems(name);
		if (list.size() > 0) {
			RegistrationItem item = list.get(0);
			// send google server
			
			if (mAuthToken == null) {
				mAuthToken = getAuthToken();
			}
			boolean result = sender(item.registrationId,mAuthToken,message);
			
			resp.setContentType("text/xml");
			PrintWriter writer = resp.getWriter();
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<Result>");
			if (result == true) {
				writer.println("\t<ResultCode>1</ResultCode>");
			} else {
				writer.println("\t<ResultCode>0</ResultCode>");
			}
			writer.print("</Result>");			
		} else {
			resp.setContentType("text/xml");
			PrintWriter writer = resp.getWriter();
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<Result>");
			writer.println("\t<ResultCode>-1</ResultCode>");
			writer.print("</Result>");			
		}
	}
	
	public boolean sender(String regId,String authToken,String msg) {
		try {
			StringBuffer postDataBuilder = new StringBuffer();
			postDataBuilder.append("registration_id="+regId);
			postDataBuilder.append("&collapse_key=1");
			postDataBuilder.append("&delay_wile_idle=1");
			postDataBuilder.append("&data.msg="+URLEncoder.encode(msg, "UTF-8"));
			byte[] postData = postDataBuilder.toString().getBytes("UTF8");
			
			URL url = new URL("https://android.apis.google.com/c2dm/send");
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setRequestProperty("Authorization", "GoogleLogin auth="+authToken);
			OutputStream out = conn.getOutputStream();
			out.write(postData);
			out.close();
			
			int responseCode = conn.getResponseCode();
	        if (responseCode == HttpServletResponse.SC_UNAUTHORIZED ||
                responseCode == HttpServletResponse.SC_FORBIDDEN) {
	        	mAuthToken = null;
	        	return false;
	        }
			
	        String updatedAuthToken = conn.getHeaderField("Update-Client-Auth");
	        if (updatedAuthToken != null && !authToken.equals(updatedAuthToken)) {
	        	mAuthToken = updatedAuthToken;
	        }

	        String responseLine = new BufferedReader(new InputStreamReader(conn.getInputStream()))
            .readLine();
	        
	        if (responseLine == null || responseLine.equals("")) {
	        	return false;
	        }
	        
	        String[] responseParts = responseLine.split("=", 2);
	        if (responseParts.length != 2) {
	        	return false;
	        }
	        
	        if (responseParts[0].equals("id")) {
	        	return true;
	        }
	        
	        
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

	public String getAuthToken() {
		String authToken = "";
		StringBuffer postDataBuilder = new StringBuffer();
		postDataBuilder.append("accountType=HOSTED_OR_GOOGLE");
		postDataBuilder.append("&Email=" + EMAIL_ID);
		postDataBuilder.append("&Passwd=" + EMAIL_PW);
		postDataBuilder.append("&service=ac2dm");
		postDataBuilder.append("&source=beintelligentgroup-testc2dm-1.0");
		
		try {
			byte[] postData = postDataBuilder.toString().getBytes("UTF8");
			URL url = new URL("https://www.google.com/accounts/clientLogin");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			
			OutputStream out = (OutputStream)conn.getOutputStream();
			out.write(postData);
			out.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String sidLine = br.readLine();
			String lsidline = br.readLine();
			String authLine = br.readLine();
			
			authToken = authLine.substring(5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return authToken;
	}
}
