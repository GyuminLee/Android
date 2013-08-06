package com.example.samplenavermovies.model;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.example.samplenavermovies.parser.InputStreamParserException;

public class NaverMovieRequest extends NetworkRequest {
	
	String mURLString;
	public NaverMovieRequest(String keyword, int start, int display) {
		try {
			mURLString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&query="+URLEncoder.encode(keyword, "utf-8")+"&display="+display+"&start="+start+"&target=movie";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public URL getURL() {
		URL url = null;
		try {
			url = new URL(mURLString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	@Override
	Object parse(InputStream is) {
		NaverMovieParser parser = new NaverMovieParser();
		try {
			parser.doParse(is);
		} catch (InputStreamParserException e) {
			e.printStackTrace();
		}
		return parser.getResult();
	}

}
