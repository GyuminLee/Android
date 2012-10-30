package org.tacademy.network.rss.navernews;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class NaverSearchNewsRequest extends NetworkRequest {
	public NaverSearchNewsRequest(String url) {
		this.urlString = "http://openapi.naver.com/search?key=test&query="+url+"&target=news&start=1&display=10";
		this.parser = new NaverSearchNewsParser();
	}

}
