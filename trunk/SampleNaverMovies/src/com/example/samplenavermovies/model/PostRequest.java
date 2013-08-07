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
	
	protected ArrayList<FormData> formdatas = new ArrayList<FormData>();
	
	public void setCharset(String charset) {
		mCharset = charset;
	}
	
	public void addFormData(String key,String value) {
		addFormData(new FormData(key,value));
	}
	
	public void addFormData(FormData formdata) {
		formdatas.add(formdata);
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
		conn.setRequestProperty("Connection","Keep-Alive");
		conn.setRequestProperty("Accept-Charset", mCharset);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + mCharset);		
	}
	
	@Override
	public void setOutput(HttpURLConnection conn) {
		super.setOutput(conn);
		try {
			OutputStream output = conn.getOutputStream();
			StringBuilder sb = new StringBuilder();
			boolean isAmp = false;
			for (FormData formdata : formdatas) {
				if (isAmp) {
					sb.append("&");
				}
				if (formdata.type == FormData.FORM_DATA_TYPE_NORMAL) {
					sb.append(formdata.key).append("=").append(URLEncoder.encode(formdata.value,mCharset));
					isAmp = true;
				}
			}
			output.write(sb.toString().getBytes(mCharset));
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
