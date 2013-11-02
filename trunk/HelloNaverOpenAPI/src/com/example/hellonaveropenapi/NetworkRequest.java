package com.example.hellonaveropenapi;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;

public abstract class NetworkRequest<T> {
	Context context;
	
	T mResult;
	boolean isCanceled = false;
	HttpURLConnection conn;
	
	public abstract URL getURL();
	public interface OnResultListener<T> {
		public void onSuccess(NetworkRequest request, T result);
		public void onError(NetworkRequest request, int code);
	}
	
	OnResultListener<T> mListener;
	
	public void setOnResultListener(OnResultListener<T> listener) {
		mListener = listener;
	}
	
	public int getConnectionTimeout() {
		return 30000;
	}
	
	public int getReadTimeout() {
		return 30000;
	}
	
	public String getRequestMethod() {
		return "GET";
	}
	
	public void setRequestHeader(HttpURLConnection conn) {
		
	}
	
	public void setOutput(HttpURLConnection conn) {
		
	}
	
	public void process(InputStream is) {
		mResult = parsing(is);
	}
	
	protected abstract T parsing(InputStream is);
	
	public void setCancel() {
		isCanceled = true;
		if (conn != null) {
			conn.disconnect();
		}
	}
	public void sendResult() {
		if (isCanceled) return;
		if (mListener != null) {
			if (mResult != null) {
				mListener.onSuccess(this, mResult);
			} else {
				mListener.onError(this, 0);
			}
		}
	}
	
	public void setConnection(HttpURLConnection conn) {
		this.conn = conn;
	}
	
	public boolean isCancel() {
		return isCanceled;
	}
	
	public void sendError(int code) {
		if (isCanceled) return;
		if (mListener != null) {
			mListener.onError(this, code);
		}
	}
}
