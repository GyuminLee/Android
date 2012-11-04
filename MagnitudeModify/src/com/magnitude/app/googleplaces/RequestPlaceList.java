package com.magnitude.app.googleplaces;

import java.net.URLEncoder;

import com.magnitude.app.network.NetworkRequest;
import com.magnitude.app.util.Config;


public class RequestPlaceList extends NetworkRequest {

	public static final int DEFAULT_RADIUS = 500;
	
	public RequestPlaceList(double latitude, double longitude) {
		this(latitude,longitude,DEFAULT_RADIUS, false, null, null, null);
	}
	
	public RequestPlaceList(double latitude, double longitude, int radius) {
		this(latitude,longitude, radius, false, null, null, null);
	}
	
	public RequestPlaceList(double latitude, double longitude, int radius, boolean sensor) {
		this(latitude, longitude, radius, sensor, null, null, null);
	}
	
	public RequestPlaceList(double latitude, double longitude, int radius, boolean sensor, String type) {
		this(latitude, longitude, radius, sensor, type, null, null);
	}
	
	public RequestPlaceList(double latitude, double longitude, int radius, boolean sensor, String type, String keyword) {
		this(latitude, longitude, radius, sensor, type, keyword, null);
	}
	
	public RequestPlaceList(double latitude, double longitude, int radius, boolean sensor, String type, String keyword, String rankby) {
		String param;
		param = "key=" + Config.KEY;
		param += "&location="+latitude+","+longitude;
		param += "&radius="+radius;
		param += "&sensor="+sensor;
		if (type != null && !type.equals("")) {
			param += "&type=" + type;
		}
		if (keyword != null && !type.equals("")) {
			param += "&keyword=" + URLEncoder.encode(keyword);
		}
		if (rankby != null && !rankby.equals("")) {
			param += "&rankby=" + rankby;
		}
		
		this.urlString = GooglePlaceConstants.PLACE_SERARCH_URL + param;
		this.parser = new GooglePlaceListParser();
	}
}
