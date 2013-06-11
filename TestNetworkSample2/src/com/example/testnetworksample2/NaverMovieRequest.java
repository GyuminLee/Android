package com.example.testnetworksample2;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.example.testnetworksample2.parser.InputStreamParserException;

public class NaverMovieRequest extends NetworkRequest {

	String keyword;
	
	public NaverMovieRequest(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public URL getRequestURL() {
		// TODO Auto-generated method stub
		try {
			String urlString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&query=" + URLEncoder.encode(keyword, "utf-8") + "&display=10&start=1&target=movie";
			URL url = new URL(urlString);
			return url;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	NaverMovies movies;
	
	@Override
	public void parsing(InputStream is) throws ParsingException {
		NaverMovieSaxParser parser = new NaverMovieSaxParser();
		try {
			parser.doParse(is);
			movies = parser.getResult();
		} catch (InputStreamParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public NaverMovies getResult() {
		return movies;
	}

}
