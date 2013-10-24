package org.skplanetx.openapi.tmap;

import java.util.HashMap;

import org.skplanetx.openapi.BaseTask;

public abstract class GeoTask<Result> extends BaseTask<Result> {

	public GeoTask(OnResultListener<Result> listener) {
		super(listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<String, Object> getParameter() {
		HashMap<String, Object> param = super.getParameter();
		param.put("reqCoordType", "WGS84GEO");
		param.put("resCoordType", "WGS84GEO");		
		return param;
	}
}
