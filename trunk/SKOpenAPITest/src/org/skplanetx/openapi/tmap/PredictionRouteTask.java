package org.skplanetx.openapi.tmap;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;
import com.skp.openplatform.android.sdk.common.RequestBundle;

public class PredictionRouteTask extends GeoTask<PredictionRouteInfo> {

	RequestRoutesInfo routesInfo;
	
	public PredictionRouteTask(OnResultListener<PredictionRouteInfo> listener, RequestRoutesInfo routesInfo) {
		super(listener);
		this.routesInfo = routesInfo;
	}

	@Override
	public String getUrl() {
		return "https://apis.skplanetx.com/tmap/routes";
	}

	@Override
	public RequestBundle getBundle() {
		RequestBundle bundle = super.getBundle();
		bundle.setHttpMethod(HttpMethod.POST);
		bundle.setRequestType(CONTENT_TYPE.JSON);
		bundle.setResponseType(CONTENT_TYPE.JSON);
		Gson gson = new Gson();
		String payload = gson.toJson(routesInfo);
		bundle.setPayload(payload);
		return super.getBundle();
	}

	@Override
	public PredictionRouteInfo parse(String message) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeserializer()).create();
		PredictionRouteInfo info = gson.fromJson(message, PredictionRouteInfo.class);
		return info;
	}
}
