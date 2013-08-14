package com.example.samplegooglemap3.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoadSearchResult implements ParseJsonHandler {
	public String type;
	public ArrayList<Feature> features;
	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		type = jobject.getString("type");
		JSONArray jarray = jobject.getJSONArray("features");
		features = new ArrayList<Feature>();
		for (int i = 0; i < jarray.length(); i++) {
			JSONObject jobj = jarray.getJSONObject(i);
			Feature feature = new Feature();
			feature.parseJson(jobj);
			features.add(feature);
		}
	}
}
