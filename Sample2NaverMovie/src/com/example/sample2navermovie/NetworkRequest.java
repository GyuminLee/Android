package com.example.sample2navermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

public abstract class NetworkRequest<T> {
	T result;
	
	public interface OnResultListener<T> {
		public void onSuccess(NetworkRequest request, T movies);
		public void onError(NetworkRequest request,int error);
	}
	OnResultListener<T> mListener;
		
	public void setOnResultListener(OnResultListener<T> listener) {
		mListener = listener;
	}
	
	public abstract URL getURL() throws MalformedURLException, UnsupportedEncodingException;
	
	public abstract void process(InputStream is);
//	{
//		XMLParser parser = new XMLParser();
//		movies = parser.fromXml(is, "channel",
//				NaverMovies.class);
//	}
	
	public T getResult() {
		return result;
	}
	
	public void sendSuccess(){
		if (mListener != null) {
			mListener.onSuccess(this, result);
		}
	}
	
	public void sendError(int error) {
		if (mListener != null) {
			mListener.onError(this, error);
		}
	}
	
}
