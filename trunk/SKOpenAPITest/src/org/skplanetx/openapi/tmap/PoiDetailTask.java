package org.skplanetx.openapi.tmap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gson.Gson;

public class PoiDetailTask extends GeoTask<PoiDetailInfo> {

	String poiId;
	
	public PoiDetailTask(OnResultListener<PoiDetailInfo> listener, String poiId) {
		super(listener);
		this.poiId = poiId;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/pois/" + poiId;
	}
	
	@Override
	public PoiDetailInfo parse(String message) {
		ResultPoiDetailInfo rs = new Gson().fromJson(message, ResultPoiDetailInfo.class);
		return rs.poiDetailInfo;
	}

}
