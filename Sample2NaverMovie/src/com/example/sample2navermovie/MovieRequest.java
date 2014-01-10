package com.example.sample2navermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

public class MovieRequest extends NetworkRequest<NaverMovies> {
	String keyword;
	public MovieRequest(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public URL getURL() throws MalformedURLException,
			UnsupportedEncodingException {
		return new URL(
				"http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query="
				+ URLEncoder.encode(keyword, "utf8"));
	}

	@Override
	public void process(InputStream is) {
		XMLParser parser = new XMLParser();
		result = parser.fromXml(is, "channel",
				NaverMovies.class);
	}

}
