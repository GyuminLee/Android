package com.example.hellotemptest;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.begentgroup.xmlparser.XMLParser;

public class CityRequest extends NetworkRequest<Cities> {

	URL url;
	
	public CityRequest(String city) {
		try {
			url = new URL("http://api.openweathermap.org/data/2.5/find?units=metric&mode=xml&q="+city);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public URL getURL() {
		
		return url;
	}

	@Override
	Cities parsing(InputStream is) {
		Cities data = new XMLParser().fromXml(is, "cities", Cities.class);
		return data;
	}

}
