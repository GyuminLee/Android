package org.tacademy.network.rss.google;

import java.net.HttpURLConnection;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class GmailAtomFeedRequest extends NetworkRequest {

	String mAccessToken;
	public GmailAtomFeedRequest(String accessToken) {
		this.urlString = GoogleConstants.SCOPE;
		this.parser = new GmailFeedParser();
		mAccessToken = accessToken;
	}
	@Override
	public boolean setHeader(HttpURLConnection conn) {
		// TODO Auto-generated method stub
		conn.addRequestProperty("Authorization", "OAuth "+mAccessToken);
		return true;
	}
	
	
}
