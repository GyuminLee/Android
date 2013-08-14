package com.example.samplegooglemap3.network;

import org.json.JSONException;
import org.json.JSONObject;

public class Feature implements ParseJsonHandler {
	public String type;
	public Geometry geometry;
	public Properties properties;
	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		type = jobject.getString("type");
		JSONObject jgeomtry = jobject.getJSONObject("geometry");
		geometry = new Geometry();
		geometry.parseJson(jgeomtry);
		JSONObject jproperties = jobject.getJSONObject("properties");
		properties = new Properties();
		properties.parseJson(jproperties);
	}
}
