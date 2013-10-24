package org.skplanetx.openapi.tmap;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;
import com.skp.openplatform.android.sdk.common.RequestBundle;

public class TrafficInfoTask extends GeoTask<TrafficInfo> {

	double centerLatitude;
	double centerLongitude;
	
	public TrafficInfoTask(OnResultListener<TrafficInfo> listener, double centerLatitude, double centerLongitude) {
		super(listener);
		this.centerLatitude = centerLatitude;
		this.centerLongitude = centerLongitude;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/routes";
	}

	@Override
	public HashMap<String, Object> getParameter() {
		HashMap<String,Object> param = super.getParameter();
		param.put("centerLat", ""+centerLongitude);
		param.put("centerLon", "" + centerLatitude);
		param.put("trafficType", "AROUND");
		param.put("zoomLevel", "10");
		return param;
	}
	@Override
	public RequestBundle getBundle() {
		RequestBundle bundle = super.getBundle();
		bundle.setHttpMethod(HttpMethod.POST);
		bundle.setRequestType(CONTENT_TYPE.FORM);
		bundle.setResponseType(CONTENT_TYPE.JSON);
		return super.getBundle();
	}

	@Override
	public TrafficInfo parse(String message) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeserializer()).create();
		TrafficInfo info = gson.fromJson(message, TrafficInfo.class);
		return info;
	}

}
