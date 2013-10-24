package org.skplanetx.openapi.tmap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gson.Gson;

public class AroundPoiTask extends GeoTask<SearchPoiInfo> {

	String mCategory;
	double latitude;
	double longitude;
	
	public AroundPoiTask(OnResultListener<SearchPoiInfo> listener, String category, double latitude, double longitude) {
		super(listener);
		mCategory = category;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/pois/search/around";
	}
	
	@Override
	public HashMap<String, Object> getParameter() {
		HashMap<String,Object> param = super.getParameter();
		param.put("categories", mCategory);
		param.put("centerLon", ""+longitude);
		param.put("centerLat", ""+latitude);
		return param;
	}

	@Override
	public SearchPoiInfo parse(String message) {
		ResultSearchPoiInfo rs = new Gson().fromJson(message, ResultSearchPoiInfo.class);
		return rs.searchPoiInfo;
	}

}
