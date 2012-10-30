package org.tacademy.web.hellowebapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class C2DMMessageSend extends HttpServlet {

	String mAuthToken = null;
	private final static String EMAIL_ID = "@";
	private final static String EMAIL_PW = "";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		Connection c = null;
		ResultSet rs = null;
		ResultSet senderrs = null;
		String result = "Fail";
		int resultid = -1;
		UserService userservice = UserServiceFactory.getUserService();
		User user = userservice.getCurrentUser();
		if (user != null) {
			int receiverid = Integer.parseInt(req.getParameter("receiverid"));
			String type = req.getParameter("type");
			String message = req.getParameter("message");
			try {
				DriverManager.registerDriver(new AppEngineDriver());
				c = DriverManager.getConnection("jdbc:google:rdbms://dongjawebapp:boarddb/boarddb");
				if (session.getAttribute("userid") == null) {
					String query = "SELECT id FROM usertbl WHERE userid = ?";
					PreparedStatement stmt = c.prepareStatement(query);
					stmt.setString(1, user.getEmail());		
					rs = stmt.executeQuery();
					if (rs.next()) {
						session.setAttribute("userid", rs.getInt("id"));
					}
					rs.close();
				}
				
				if (session.getAttribute("userid") != null) {
					// update user info
					int userid = (Integer)session.getAttribute("userid");
					
					
					
					String query = "SELECT id, username, registrationid FROM id = ? or id = ?";
					PreparedStatement stmt = c.prepareStatement(query);
					stmt.setInt(1, receiverid);
					stmt.setInt(2, userid);
					senderrs = stmt.executeQuery();
					String registrationid = null;
					String sendername = null;
					while (senderrs.next()) {
						if (senderrs.getInt("id") == receiverid) {
							registrationid = senderrs.getString("registrationid");
						} else if (senderrs.getInt("id") == userid) {
							sendername = senderrs.getString("username");
						}
					}
					
					if (registrationid != null && !registrationid.equals("")) {
						String statementFormat = "INSERT INTO msgtbl(fromid,toid,type,message) VALUES(%d,%d,'%s','%s')";
						String statement = String.format(statementFormat, userid,receiverid,type,message);
						Statement instmt = c.createStatement();
						instmt.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
						ResultSet rrs = instmt.getGeneratedKeys();
						if (rrs.next()) {
							resultid = rrs.getInt(1);
						}
						if (mAuthToken == null) {
							mAuthToken = getAuthToken();
						}
						boolean sendresult = sender(registrationid,mAuthToken,userid,sendername, type,message);
						if (sendresult) {
							result = "Success";
						} else {
							result = "NotSended";
						}
					} else {
						result = "Fail";
						
					}
				} else {
					// insert user info
					result = "NotAdded";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "Fail";
			} finally {
				if (c != null) {
					try {
						c.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}				
			}
		} else {
			result = "NotLogin";
		}

	    req.setAttribute("result", result);
	    req.setAttribute("resultid", resultid);
	    try {
            req.getRequestDispatcher("/UploadResult.jsp").forward(req, resp);
        } catch (ServletException e) {
        }
			
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	public boolean sender(String regId,String authToken,int sender,String senderName, String type, String msg) {
		try {
			StringBuffer postDataBuilder = new StringBuffer();
			postDataBuilder.append("registration_id="+regId);
			postDataBuilder.append("&collapse_key=1");
			postDataBuilder.append("&delay_wile_idle=1");
			postDataBuilder.append("&data.sender="+sender);
			postDataBuilder.append("&data.name="+URLEncoder.encode(type, "UTF-8"));
			postDataBuilder.append("&data.type="+URLEncoder.encode(type, "UTF-8"));
			postDataBuilder.append("&data.message="+URLEncoder.encode(msg, "UTF-8"));
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
