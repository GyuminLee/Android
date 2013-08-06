package com.example.samplenavermovies.model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;

public abstract class NetworkRequest {

	public static final int CONNECTION_TIME_OUT = 30000;
	public static final int READ_TIME_OUT = 30000;
	
	HttpURLConnection mConn;
	
	public interface OnCompletedListener {
		public void onSuccess(NetworkRequest request, Object result);
		public void onFail(NetworkRequest request, int errorCode, String errorMsg);
	}
	OnCompletedListener mListener;
	Handler mHandler;
	Object mResult;
	
	public void setOnCompletedListener(OnCompletedListener listener) {
		mListener = listener;
	}
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	private boolean bCancel = false;
	
	public abstract URL getURL();
	
	public void setRequestHeader(HttpURLConnection conn) {
		try {
			conn.setRequestMethod(getRequestMethod());
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setConnectionConfig(HttpURLConnection conn) {
		conn.setConnectTimeout(getConnectionTimeOut());
		conn.setReadTimeout(getReadTimeOut());
	}
	
	protected int getConnectionTimeOut() {
		return CONNECTION_TIME_OUT; 
	}
	
	protected int getReadTimeOut() {
		return READ_TIME_OUT;
	}
	
	public String getRequestMethod() {
		return "GET";
	}
	
	public void setOutput(HttpURLConnection conn) {
		
	}
	
	public boolean cancel() {
		bCancel = true;
		if (mConn != null) {
			mConn.disconnect();
		}
		return true;
	}
	
	public void setConnection(HttpURLConnection conn) {
		mConn = conn;
	}
	
	public boolean isCancel() {
		return bCancel;
	}
	
	public void process(InputStream is) {
		mResult = parse(is);
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mListener != null && !bCancel) {
						mListener.onSuccess(NetworkRequest.this, mResult);
					}
				}
			});
		} else {
			if (mListener != null && !bCancel) {
				mListener.onSuccess(NetworkRequest.this, mResult);
			}
		}		
	}
	
	abstract Object parse(InputStream is);
	
	public Object getResult() {
		return mResult;
	}
	
	public void sendError(final int code, final String message) {
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if (mListener != null && !bCancel) {
						mListener.onFail(NetworkRequest.this, code, message);
					}
					
				}
			});
		} else {
			if (mListener != null && !bCancel) {
				mListener.onFail(NetworkRequest.this, code, message);
			}
		}
	}
}
