package org.tacademy.network.rss.navermovie;

import java.net.URLEncoder;

import org.tacademy.network.rss.Nework.NetworkRequest;

public class NaverMovieRequest extends NetworkRequest {
	static final String key = "55f1e342c5bce1cac340ebb6032c7d9a";
	public NaverMovieRequest(String keyward) {
		String encodedKeyward = URLEncoder.encode(keyward);
		urlString = "http://openapi.naver.com/search?key="+key+"&query="+encodedKeyward+"&display=10&start=1&target=movie";
		parser = new NaverMovieSaxParser();
	}
}
