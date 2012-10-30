package com.example.hellonetwork;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.example.hellonetwork.parser.InputStreamParserException;

public class NaverMovieRequest extends NetworkRequest {

	String url;
	NaverMovies movies;
	public NaverMovieRequest(String keyword) {
		try {
			url = "http://openapi.naver.com/search?"+
					"key=55f1e342c5bce1cac340ebb6032c7d9a&target=movie&query=" + 
					URLEncoder.encode(keyword,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void parsing(InputStream is) {
		// TODO Auto-generated method stub
		NaverMovieParser parser = new NaverMovieParser();
		try {
			parser.doParse(is);
			movies = (NaverMovies)parser.getResult();
		} catch (InputStreamParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return movies;
	}

}
