package com.example.testnetworksample;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GoogleRequest extends NetworkRequest {

	GoogleResult result;
	
	@Override
	public URL getRequestURL() {
		// TODO Auto-generated method stub
		URL url = null;
		try {
			url = new URL("http://www.google.com");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public void dataProcessing(InputStream is) {
		// TODO Auto-generated method stub

		result = new GoogleResult();
		result.result = "test";
		
	}

}
