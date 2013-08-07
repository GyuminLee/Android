package com.example.samplenavermovies.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public abstract class PostRequest extends NetworkRequest {

	protected String mCharset = "UTF-8";
	
	public void setCharset(String charset) {
		mCharset = charset;
	}
	
	public String getCharset() {
		return mCharset;
	}
	
	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public void setConnectionConfig(HttpURLConnection conn) {
		super.setConnectionConfig(conn);
		conn.setDoOutput(true);
		conn.setUseCaches(false);		
	}
	
	@Override
	public void setRequestHeader(HttpURLConnection conn) {
		super.setRequestHeader(conn);
		if (isKeepAlive()) {
			conn.setRequestProperty("Connection","Keep-Alive");
		} else {
			conn.setRequestProperty("Connection", "Close");
		}
		conn.setRequestProperty("Accept-Charset", mCharset);
		conn.setRequestProperty("Content-Type", getMessageContentType());		
	}
	
	protected boolean isKeepAlive() {
		return true;
	}
	
	protected abstract String getMessageContentType();
	
}
