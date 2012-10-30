package com.example.hellonetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DummyRequest extends NetworkRequest {

	ArrayList<ItemData> result = new ArrayList<ItemData>();
	String text;
	
	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		
		try {
			return new URL("http://www.google.com");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	protected void parsing(InputStream is) {
		// TODO Auto-generated method stub
		result.add(new ItemData(R.drawable.ic_launcher,"item1"));
		result.add(new ItemData(R.drawable.ic_launcher,"item2"));
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text = sb.toString();
	}

}
