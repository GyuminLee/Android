package com.example.testnetworksample2;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import android.os.Handler;

public class NetworkModel {

	private static NetworkModel instance;
	
	private String userName;
	private String password;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private NetworkModel() {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password.toCharArray());
			}
		});
		
		CookieHandler.setDefault(new CookieManager());
	}
	
	public boolean getNetworkData(final NetworkRequest request, 
			NetworkRequest.OnProcessCompleteListener listener, 
			Handler handler) {
		request.setOnProcessCompleteListener(listener);
		request.setHandler(handler);
		new Thread(new Runnable(){
			@Override
			public void run() {
				URL url = request.getRequestURL();
				try {
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					request.setRequestMethod(conn);
					request.setRequestProperty(conn);
					request.setOutput(conn);
					conn.setConnectTimeout(request.getConnectionTimeout());
					conn.setReadTimeout(request.getReadTimeout());
					
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						request.process(is);
						return;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.notifyError("Netowrk Error");
			}
		}).start();
		return true;
	}
}
