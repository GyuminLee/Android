package com.example.samplegooglemap3.network;

import org.json.JSONException;
import org.json.JSONObject;

public class Properties implements ParseJsonHandler {
	public int totalDistance;
	public int totalTime;
	public int totalFare;
	public int index;
	public int pointIndex;
	public String name;
	public String description;
	public String nextRoadName;
	public int turnType;
	public String pointType;
	public int lineIndex;
	public int distance;
	public int time;
	public int roadType;
	public int facilityType;

	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		if (!jobject.isNull("totalDistance")) {
			totalDistance = jobject.getInt("totalDistance");
		} 
		if (!jobject.isNull("totalTime")) {
			totalTime = jobject.getInt("totalTime");
		} 
		if (!jobject.isNull("totalFare")) {
			totalFare = jobject.getInt("totalFare");
		} 
		if (!jobject.isNull("index")) {
			index = jobject.getInt("index");
		} 
		if (!jobject.isNull("pointIndex")) {
			pointIndex = jobject.getInt("pointIndex");
		} 
		if (!jobject.isNull("turnType")) {
			turnType = jobject.getInt("turnType");
		} 
		if (!jobject.isNull("lineIndex")) {
			lineIndex = jobject.getInt("lineIndex");
		} 
		if (!jobject.isNull("distance")) {
			distance = jobject.getInt("distance");
		} 
		if (!jobject.isNull("time")) {
			time = jobject.getInt("time");
		} 
		if (!jobject.isNull("roadType")) {
			roadType = jobject.getInt("roadType");
		} 
		if (!jobject.isNull("facilityType")) {
			facilityType = jobject.getInt("facilityType");
		} 
		if (!jobject.isNull("name")) {
			name = jobject.getString("name");
		} 
		if (!jobject.isNull("description")) {
			description = jobject.getString("description");
		} 
		if (!jobject.isNull("nextRoadName")) {
			nextRoadName = jobject.getString("nextRoadName");
		} 
		if (!jobject.isNull("pointType")) {
			pointType = jobject.getString("pointType");
		} 
		
		
		
	}
}
