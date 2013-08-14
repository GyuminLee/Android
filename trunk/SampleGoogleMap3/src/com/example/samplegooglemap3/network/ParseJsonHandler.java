package com.example.samplegooglemap3.network;

import org.json.JSONException;
import org.json.JSONObject;

public interface ParseJsonHandler {
	public void parseJson(JSONObject jobject) throws JSONException;
}
