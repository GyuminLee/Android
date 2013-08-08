package com.example.samplenavermovies.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GoogleAtomFeedRequest extends NetworkRequest {

	GoogleAccessToken mToken;
	
	public GoogleAtomFeedRequest(GoogleAccessToken token) {
		mToken = token;
	}
	
	@Override
	public URL getURL() {
		try {
			return new URL("https://mail.google.com/mail/feed/atom");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setRequestHeader(HttpURLConnection conn) {
		super.setRequestHeader(conn);
		String auth = mToken.token_type + " " + mToken.access_token;
		conn.setRequestProperty("Authorization", auth);
	}

	@Override
	Object parse(InputStream is) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		try {
			while((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n\r");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

}
