package com.example.sample2navermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.content.Context;

public abstract class NetworkRequest<T> {
	T result;
	HttpURLConnection conn = null;
	Context context;

	public static final int CONNECTION_TIMEOUT = 30000;
	public static final int READ_TIMEOUT = 30000;

	public interface OnResultListener<T> {
		public void onSuccess(NetworkRequest request, T result);

		public void onError(NetworkRequest request, int error);
	}

	OnResultListener<T> mListener;

	public void setOnResultListener(OnResultListener<T> listener) {
		mListener = listener;
	}

	boolean bCancel = false;

	public void cancel() {
		cancel(true);
	}
	
	public void cancel(boolean isRemoveRequestMap) {
		bCancel = true;
		if (conn != null) {
			conn.disconnect();
			conn = null;
		}
		if (isRemoveRequestMap) {
			NetworkModel.getInstance().removeRequestMap(this);
		}
	}

	public boolean isCanceled() {
		return bCancel;
	}

	public void setHttpURLConnection(HttpURLConnection conn) {
		this.conn = conn;
	}

	public void setRequestMethod(HttpURLConnection conn) {
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRequestProperty(HttpURLConnection conn) {

	}

	public void setConnectionConfig(HttpURLConnection conn) {

	}

	public void setOutput(HttpURLConnection conn) {

	}

	public void setTimeout(HttpURLConnection conn) {
		conn.setConnectTimeout(CONNECTION_TIMEOUT);
		conn.setReadTimeout(READ_TIMEOUT);
	}

	public abstract URL getURL() throws MalformedURLException,
			UnsupportedEncodingException;

	public abstract void process(InputStream is);

	// {
	// XMLParser parser = new XMLParser();
	// movies = parser.fromXml(is, "channel",
	// NaverMovies.class);
	// }

	public T getResult() {
		return result;
	}

	public void sendSuccess() {
		if (mListener != null && !bCancel) {
			mListener.onSuccess(this, result);
		}
	}

	public void sendError(int error) {
		if (mListener != null && !bCancel) {
			mListener.onError(this, error);
		}
	}

}
