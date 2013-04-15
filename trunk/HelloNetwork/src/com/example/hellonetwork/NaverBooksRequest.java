package com.example.hellonetwork;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.example.hellonetwork.parser.GsonResultParser;
import com.example.hellonetwork.parser.InputStreamParserException;

public class NaverBooksRequest extends NetworkRequest {

	public static final String REQUEST_URL = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=book&query=";
	String url;
	NaverBooks result;
	public NaverBooksRequest(String keyword) {
		try {
			url = REQUEST_URL + URLEncoder.encode(keyword, "UTF8");
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
		NaverBookParser parser = new NaverBookParser();
//		GsonResultParser<NaverBooks> parser = new GsonResultParser<NaverBooks>(NaverBooks.class);
		
		try {
			parser.doParse(is);
			result = (NaverBooks)parser.getResult();
		} catch (InputStreamParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return result;
	}

}
