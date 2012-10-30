package org.tacademy.network.rss.npr;

import java.net.HttpURLConnection;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class RssRequest extends NetworkRequest {

	public RssRequest(String url,NetworkRequest.OnDownloadCompletedListener listener){
		this.parser = new RssParser();
		this.urlString = url;
		this.listener = listener;
		isNeedSign = true;
	}
	
	public RssRequest(String url) {
		this(url,null);
	}

	@Override
	public boolean setHeader(HttpURLConnection conn) {
		return super.setHeader(conn);
	}
	
	
}
