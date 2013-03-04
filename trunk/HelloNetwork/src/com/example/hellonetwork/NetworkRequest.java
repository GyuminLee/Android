package com.example.hellonetwork;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;

public abstract class NetworkRequest {

	private boolean isCancel = false;
	private HttpURLConnection mConn;
	
	public boolean isCanceled() {
		return isCancel;
	}
	
	public void cancel() {
		isCancel = true;
		if (mConn != null) {
			mConn.disconnect();
		}
	}
	
	public void setConnection(HttpURLConnection conn) {
		mConn = conn;
	}

	public interface OnProcessCompletedListener {
		public void onCompleted(NetworkRequest request);
	}
	
	OnProcessCompletedListener mListener;
	Handler mHandler;
	
	public void setOnProcessCompletedListener(OnProcessCompletedListener listener) {
		mListener = listener;
	}
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	public abstract URL getURL();
	
	public void process(InputStream is) {
		// io read data
		// data parasing
		// object
		
		parsing(is);
		
		if (mHandler != null) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mListener != null) {
						mListener.onCompleted(NetworkRequest.this);
					}
				}
				
			});
		} else {
			if (mListener != null) {
				mListener.onCompleted(this);
			}
		}
	}
	
	protected abstract void parsing(InputStream is);
	
	public abstract Object getResult();
	
	public String getRequestMethod() {
		return "GET";
	}
	
	public boolean setConnectionConfig(HttpURLConnection conn) {
		return true;
	}

	public boolean setHeader(HttpURLConnection conn) {
		return true;
	}

	public boolean setTimeout(HttpURLConnection conn) {
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		return true;
	}

	public boolean setOutput(HttpURLConnection conn)  throws IOException {
		return true;
	}
	
}
