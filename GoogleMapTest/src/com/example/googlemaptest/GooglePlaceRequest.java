package com.example.googlemaptest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.googlemaptest.network.NetworkRequest;
import com.example.googlemaptest.parser.GsonResultParser;

public class GooglePlaceRequest extends NetworkRequest {

	public GooglePlaceRequest(double lat, double lng, String keyword) {
		try {
			this.urlString = GooglePlaceConstant.GOOGLE_PLACE_URL +
					GooglePlaceConstant.OUTPUT_JSON +
					GooglePlaceConstant.REQUEST_FIELD_LOCATION + lat + "," + lng + "&" +
					GooglePlaceConstant.REQUEST_FIELD_RADIUS + GooglePlaceConstant.DEFAULT_RADIUS + "&" +
					GooglePlaceConstant.REQUEST_FIELD_SENSOR + "true" + "&" +
					GooglePlaceConstant.REQUEST_FIELD_KEY + GooglePlaceConstant.API_KEY + "&" +
					GooglePlaceConstant.REQUEST_FIELD_TYPES + "food" + "&" +
					GooglePlaceConstant.REQUEST_FIELD_KEYWORD + URLEncoder.encode(keyword, "utf8");
			this.parser = new GsonResultParser<GooglePlaces>(GooglePlaces.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
