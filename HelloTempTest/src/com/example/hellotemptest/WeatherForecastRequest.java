package com.example.hellotemptest;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.begentgroup.xmlparser.XMLParser;

public class WeatherForecastRequest extends NetworkRequest<WeatherData> {

	URL url;
	
	public WeatherForecastRequest(String city) {
		String urlText = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=xml&units=metric&cnt=7&q="
				+ city;
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public URL getURL() {
		return url;
	}

	@Override
	WeatherData parsing(InputStream is) {
		WeatherData data = new XMLParser().fromXml(is, "weatherdata", WeatherData.class);
		return data;
	}

}
