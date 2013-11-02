package com.example.hellonaveropenapi;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

public class NaverMovieRequest extends NetworkRequest<NaverMovies> {
	String urlString;
	public NaverMovieRequest(String keyword) {
		try {
			urlString = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&display=10&start=1&target=movie&query=" + URLEncoder.encode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public URL getURL() {
		
		try {
			return new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected NaverMovies parsing(InputStream is) {
		XMLParser parser = new XMLParser();
		return parser.fromXml(is, "channel", NaverMovies.class);
	}

}
