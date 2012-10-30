package org.tacademy.network.rss.google;

import java.net.HttpURLConnection;

import org.tacademy.network.rss.Nework.PostRequest;

public class AccessTokenRequest extends PostRequest {
	String code;
	String state;
	public AccessTokenRequest(String code) {
		this.urlString = GoogleConstants.ACCESS_TOKEN_URL;
		this.parser = new AccessTokenParser();
		addFormData("code",code);
		addFormData("redirect_uri",GoogleConstants.REDIRECT_URI);
		addFormData("client_id",GoogleConstants.CLIENT_ID);
		addFormData("scope",GoogleConstants.SCOPE);
		addFormData("client_secret",GoogleConstants.CLIENT_SECRET);
		addFormData("grant_type",GoogleConstants.GRANT_TYPE_AUTH_CODE);
	}
	
	@Override
	public boolean setHeader(HttpURLConnection conn) {
		// TODO Auto-generated method stub
		conn.setRequestProperty("user-agent", GoogleConstants.USER_AGENT);
		return super.setHeader(conn);
	}
	
	
}
