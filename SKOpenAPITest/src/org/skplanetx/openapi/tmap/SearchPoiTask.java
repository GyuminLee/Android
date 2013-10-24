package org.skplanetx.openapi.tmap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gson.Gson;

public class SearchPoiTask extends GeoTask<SearchPoiInfo> {

	String mKeyword;
	
	public SearchPoiTask(OnResultListener<SearchPoiInfo> listener, String keyword) {
		super(listener);
		try {
			mKeyword = URLEncoder.encode(keyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/pois";
	}
	
	@Override
	public HashMap<String, Object> getParameter() {
		HashMap<String,Object> param = super.getParameter();
		param.put("searchKeyword", mKeyword);
		return param;
	}

	@Override
	public SearchPoiInfo parse(String message) {
		ResultSearchPoiInfo rs = new Gson().fromJson(message, ResultSearchPoiInfo.class);
		return rs.searchPoiInfo;
	}

}
