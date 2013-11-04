package com.example.hellotemptest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class NetworkRequest<T> {

	T result;
	
	public interface OnResultListener<T> {
		public void onSuccess(NetworkRequest request,T result);
		public void onError(NetworkRequest request, int error);
	}
	
	OnResultListener<T> mListener;
	
	public void setOnResultListener(OnResultListener<T> listener) {
		mListener = listener;
	}
	
	abstract public URL getURL();

	public int getConnctionTimeout() {
		return 30000;
	}

	public int getReadTimeout() {
		return 30000;
	}

	public String getRequestMethod() {
		return "GET";
	}

	public void setRequestProperty(HttpURLConnection conn) {
		
	}

	public void setOutput(HttpURLConnection conn) {
		
	}

	public void process(InputStream is) {
		result = parsing(is);
	}

	abstract T parsing(InputStream is);
	
	public void sendResult() {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(this, result);
			} else {
				mListener.onError(this, 0);
			}
		}
	}
	
	public void sendResult(T data) {
		result = data;
		sendResult();
	}
	
	public void sendError(int code) {
		if (mListener != null) {
			mListener.onError(this, code);
		}
	}
}
