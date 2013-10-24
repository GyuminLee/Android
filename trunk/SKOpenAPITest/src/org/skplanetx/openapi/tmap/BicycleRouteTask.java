package org.skplanetx.openapi.tmap;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;
import com.skp.openplatform.android.sdk.common.RequestBundle;

public class BicycleRouteTask extends GeoTask<BicycleRouteInfo> {

	double startLatitude;
	double startLongitude;
	double endLatitude;
	double endLongitude;
	
	public BicycleRouteTask(OnResultListener<BicycleRouteInfo> listener, double startLatitude, double startLongitude,
			double endLatitude, double endLogitude) {
		super(listener);
		this.startLatitude = startLatitude;
		this.startLongitude = startLongitude;
		this.endLatitude = endLatitude;
		this.endLongitude = endLogitude;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/routes/bicycle";
	}

	@Override
	public HashMap<String, Object> getParameter() {
		HashMap<String,Object> param = super.getParameter();
		param.put("startX", ""+startLongitude);
		param.put("startY", "" + startLatitude);
		param.put("endX", "" + endLongitude);
		param.put("endY", "" + endLatitude);
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
	public BicycleRouteInfo parse(String message) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeserializer()).create();
		BicycleRouteInfo info = gson.fromJson(message, BicycleRouteInfo.class);
		return info;
	}

}
