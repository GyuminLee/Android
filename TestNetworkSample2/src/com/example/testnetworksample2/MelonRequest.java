package com.example.testnetworksample2;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.testnetworksample2.parser.InputStreamParserException;

public class MelonRequest extends NetworkRequest {

	@Override
	public URL getRequestURL() {
		URL url = null;
		try {
			url = new URL("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	@Override
	public void setRequestProperty(HttpURLConnection conn) {
		super.setRequestProperty(conn);
		conn.setRequestProperty("Accept", "application/xml");
		conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
	}

	Melon melon;
	
	@Override
	public void parsing(InputStream is) throws ParsingException {
		MelonSaxParser parser = new MelonSaxParser();
		try {
			parser.doParse(is);
			melon = (Melon)parser.getResult();
		} catch (InputStreamParserException e) {
			e.printStackTrace();
			throw new ParsingException();
		}
	}

	@Override
	public Melon getResult() {
		return melon;
	}

}
