package com.example.testnetworksample2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlRequest extends NetworkRequest {

	URL url;
	String result;
	public UrlRequest(String urlText) {
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		super.setRequestProperty(conn);
	}
	
	@Override
	public URL getRequestURL() {
		// TODO Auto-generated method stub
		return url;
	}

	@Override
	public void parsing(InputStream is) throws ParsingException {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line=br.readLine())!= null) {
				sb.append(line);
				sb.append("\n\r");
			}
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParsingException();
		}
	}

	@Override
	public String getResult() {
		return result;
	}

}
