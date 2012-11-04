package com.example.samplearcamera.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class PostRequest extends NetworkRequest {

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
	public String getMethod() {
		return "POST";
	}

	@Override
	public boolean setConnectionConfig(HttpURLConnection conn) {
		//conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);		
		return true;
	}

	@Override
	public boolean setHeader(HttpURLConnection conn) {
		conn.setRequestProperty("Connection","Keep-Alive");
		conn.setRequestProperty("Accept-Charset", mCharset);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + mCharset);
		return true;
	}

	@Override
	public boolean setOutput(HttpURLConnection conn) throws IOException {
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
		return true;
	}
	
	
	
	
}
