package com.example.samplegooglemap3.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Geometry implements ParseJsonHandler {
	public String type;
	public double[] coordinates;
	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		type = jobject.getString("type");
		JSONArray jarray = jobject.getJSONArray("coordinates");
		if (type.equals("Point")) {
			coordinates = new double[2];
			coordinates[0] = jarray.getDouble(0);
			coordinates[1] = jarray.getDouble(1);
		} else if (type.equals("LineString")) {
			coordinates = new double[jarray.length() * 2];
			for (int i = 0; i < jarray.length(); i++) {
				JSONArray valArray = jarray.getJSONArray(i);
				coordinates[i * 2] = valArray.getDouble(0);
				coordinates[i * 2 + 1] = valArray.getDouble(1);
			}
		}
		
	}
}
