package com.example.samplegooglemap3.network;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class RoadSearchResult implements ParseJsonHandler {
	public String type;
	public ArrayList<Feature> features;
	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		type = jobject.getString("type");
		
	}
}
