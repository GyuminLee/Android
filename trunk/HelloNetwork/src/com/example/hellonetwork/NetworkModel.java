package com.example.hellonetwork;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Handler;

public class NetworkModel {

	
	private static NetworkModel instance;
	private static final int MAX_THREAD_SIZE = 5;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private NetworkModel() {
		final String username = "";
		final String password = "";
		Authenticator.setDefault(new Authenticator() {
		     protected PasswordAuthentication getPasswordAuthentication() {
		    	 // callLoginPopup
		    	 callwait();
		       return new PasswordAuthentication(username, password.toCharArray());
		     
		   };
		   
		   public synchronized void callwait() {
			   try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		});
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);

		 
		
		for (int i = 0; i < MAX_THREAD_SIZE; i++) {
			new Thread(imageDownaloder).start();
		}
	}
	
	ArrayList<ImageRequest> mImageRequests = new ArrayList<ImageRequest>();
	ArrayList<ImageRequest> mRunningRequests = new ArrayList<ImageRequest>();
	
	boolean mIsRunning = true;
	
	public boolean getNetworkData(final NetworkRequest request,NetworkRequest.OnProcessCompletedListener listener, Handler handler) {
		// 1. request listener. handler.
		request.setHandler(handler);
		request.setOnProcessCompletedListener(listener);
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				URL url = request.getURL();
				// ...
				HttpURLConnection conn;
				try {
					conn = (HttpURLConnection)url.openConnection();
					conn.setRequestMethod(request.getRequestMethod());
					request.setConnectionConfig(conn);
					request.setHeader(conn);
					request.setOutput(conn);
					request.setTimeout(conn);
					if (request.isCanceled()) {
						return;
					}
					conn.connect();
					if (request.isCanceled()) {
						conn.disconnect();
						return;
					}
					request.setConnection(conn);
			
					int respCode = conn.getResponseCode();
					if (respCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						request.process(is);
					} else {
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		th.start();
		return true;
	}
	
	public void getImageData(final ImageRequest request, final NetworkRequest.OnProcessCompletedListener listener, Handler handler) {
		request.setHandler(handler);
		request.setOnProcessCompletedListener(listener);
		Bitmap bitmap = ImageCache.getInstance().getCacheBitmap(request.getKey());
		if (bitmap != null) {
			request.setBitmapAndPost(bitmap);
		} else {
			// Thread  
			// IS 
			// request.process(is);
			for (ImageRequest rq : mImageRequests) {
				if (rq.addNotifyRequest(request)) {
					return;
				}
			}
			for (ImageRequest rq2 : mRunningRequests) {
				if (rq2.addNotifyRequest(request)) {
					return;
				}
			}
			enqueue(request);
		}
	}
	
	public synchronized void enqueue(ImageRequest request) {
		mImageRequests.add(request);
		notify();
	}
	
	public synchronized ImageRequest dequeue() {
		ImageRequest request;
		while(mImageRequests.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request = mImageRequests.remove(0);
		return request;
	}
	
	public void remove(ImageRequest request) {
		mImageRequests.remove(request);
	}
	
	public Runnable imageDownaloder = new Runnable() {
		public void run() {
			while(mIsRunning) {
				ImageRequest request = dequeue();
				mRunningRequests.add(request);
				URL url = request.getURL();
				HttpURLConnection conn;
				try {
					conn = (HttpURLConnection)url.openConnection();
					conn.setRequestMethod(request.getRequestMethod());
					if (request.isCanceled()) {
						continue;
					}
					conn.connect();
					if (request.isCanceled()) {
						conn.disconnect();
						continue;
					}
					request.setConnection(conn);
					int respCode = conn.getResponseCode();
					if (respCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						request.process(is);
					} else {
						//...
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mRunningRequests.remove(request);
			}
		};
	};
	
}
