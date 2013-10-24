package org.skplanetx.openapi.tmap;

import java.util.HashMap;

import org.skplanetx.openapi.BaseTask;

import com.google.gson.Gson;

public class ReverseGeocodingTask extends BaseTask<AddressInfo> {

	double latitude;
	double longitude;
	
	public ReverseGeocodingTask(OnResultListener<AddressInfo> listener,double latitude, double longitude) {
		super(listener);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/geo/reversegeocoding";
	}
	
	@Override
	public HashMap<String, Object> getParameter() {
		HashMap<String,Object> param = super.getParameter();
		param.put("lat", ""+latitude);
		param.put("lon", ""+longitude);
		param.put("coordType", "WGS84GEO");
		return param;
	}

	@Override
	public AddressInfo parse(String message) {
		Gson gson = new Gson();
		ResultAddressInfo ra = gson.fromJson(message, ResultAddressInfo.class);
		return ra.addressInfo;
	}

}
