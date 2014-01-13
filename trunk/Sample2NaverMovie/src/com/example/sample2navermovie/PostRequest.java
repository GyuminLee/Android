package com.example.sample2navermovie;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.ArrayList;

public abstract class PostRequest<T> extends NetworkRequest<T> {
	
	ArrayList<FormValue> mValues = new ArrayList<FormValue>();
	String mCharset = "UTF-8";
	
	public void addFormValue(String name,String v) {
		FormValue value = new FormValue();
		value.name = name;
		value.value = v;
		mValues.add(value);
	}
	
	public void setCharset(String charset) {
		mCharset = charset;
	}
	
	@Override
	public void setRequestMethod(java.net.HttpURLConnection conn) {
		try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="+mCharset);
	}
	
	@Override
	public void setConnectionConfig(HttpURLConnection conn) {
		conn.setDoOutput(true);
	}
	
	@Override
	public void setOutput(HttpURLConnection conn) {
		try {
			OutputStream out = conn.getOutputStream();
			StringBuilder sb = new StringBuilder();
			boolean isFirst = true;
			for(FormValue v : mValues) {
				if (!isFirst) {
					sb.append("&");
				}
				sb.append(v.name).append("=").append(URLEncoder.encode(v.value, mCharset));
				isFirst = true;
			}
			out.write(sb.toString().getBytes(mCharset));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
