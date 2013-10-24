package org.skplanetx.openapi.tmap;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;
import com.skp.openplatform.android.sdk.common.RequestBundle;

public class PedestrainRouteTask extends GeoTask<PedestrainRouteInfo> {

	double startLatitude;
	double startLongitude;
	double endLatitude;
	double endLongitude;
	String startName;
	String endName;
	
	public PedestrainRouteTask(OnResultListener<PedestrainRouteInfo> listener, double startLatitude, double startLongitude,
			double endLatitude, double endLogitude,String startName, String endName) {
		super(listener);
		this.startLatitude = startLatitude;
		this.startLongitude = startLongitude;
		this.endLatitude = endLatitude;
		this.endLongitude = endLogitude;
		this.startName = startName;
		this.endName = endName;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/routes/pedestrian";
	}

	@Override
	public HashMap<String, Object> getParameter() {
		HashMap<String,Object> param = super.getParameter();
		param.put("startX", ""+startLongitude);
		param.put("startY", "" + startLatitude);
		param.put("endX", "" + endLongitude);
		param.put("endY", "" + endLatitude);
		param.put("startName", startName);
		param.put("endName", endName);
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
	public PedestrainRouteInfo parse(String message) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeserializer()).create();
		PedestrainRouteInfo info = gson.fromJson(message, PedestrainRouteInfo.class);
		return info;
	}

}
