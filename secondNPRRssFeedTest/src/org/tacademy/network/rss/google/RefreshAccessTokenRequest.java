package org.tacademy.network.rss.google;

import java.net.HttpURLConnection;

import org.tacademy.network.rss.Nework.PostRequest;

public class RefreshAccessTokenRequest extends PostRequest {
	
	public RefreshAccessTokenRequest(String refreshToken) {
		this.urlString = GoogleConstants.ACCESS_TOKEN_URL;
		this.parser = new RefreshTokenParser();
		addFormData("client_id",GoogleConstants.CLIENT_ID);
		addFormData("grant_type", GoogleConstants.GRANT_TYPE_REFRESH);
		addFormData("client_secret",GoogleConstants.CLIENT_SECRET);
		addFormData("refresh_token",refreshToken);
		
	}
	
	@Override
	public boolean setHeader(HttpURLConnection conn) {
		// TODO Auto-generated method stub
		conn.setRequestProperty("user-agent", GoogleConstants.USER_AGENT);
		return super.setHeader(conn);
	}

}
