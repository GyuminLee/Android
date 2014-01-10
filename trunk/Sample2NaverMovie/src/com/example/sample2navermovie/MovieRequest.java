package com.example.sample2navermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

public class MovieRequest {
	String keyword;
	NaverMovies movies;
	
	public interface OnResultListener {
		public void onSuccess(MovieRequest request, NaverMovies movies);
		public void onError(MovieRequest request,int error);
	}
	OnResultListener mListener;
	
	public MovieRequest(String keyword) {
		this.keyword = keyword;
	}
	
	public void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}
	
	public URL getURL() throws MalformedURLException, UnsupportedEncodingException {
		return new URL(
				"http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query="
						+ URLEncoder.encode(keyword, "utf8"));
	}
	
	public void process(InputStream is) {
		XMLParser parser = new XMLParser();
		movies = parser.fromXml(is, "channel",
				NaverMovies.class);
	}
	
	public NaverMovies getResult() {
		return movies;
	}
	
	public void sendSuccess(){
		if (mListener != null) {
			mListener.onSuccess(this, movies);
		}
	}
	
	public void sendError() {
		if (mListener != null) {
			mListener.onError(this, -1);
		}
	}
	
}
