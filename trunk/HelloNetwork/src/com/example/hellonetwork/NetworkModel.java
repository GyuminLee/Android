package com.example.hellonetwork;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
		
		for (int i = 0; i < MAX_THREAD_SIZE; i++) {
			new Thread(imageDownaloder).start();
		}
	}
	
	ArrayList<ImageRequest> mImageRequests = new ArrayList<ImageRequest>();
	boolean mIsRunning = false;
	
	public boolean getNetworkData(final NetworkRequest request,NetworkRequest.OnProcessCompletedListener listener, Handler handler) {
		// 1. request¿¡ listenerµî·Ï. handlerµî·Ï.
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
			// Thread »ý¼º 
			// IS È¹µæ
			// request.process(is);
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
						}
		};
	};
	
}
