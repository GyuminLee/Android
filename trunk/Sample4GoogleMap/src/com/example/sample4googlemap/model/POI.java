package com.example.sample4googlemap.model;

public class POI {
	public String id;
	public String name;
	public String telNo;
	public String frontLat;
	public String frontLon;
	public String noorLat;
	public String noorLon;
	public String upperAddrName;
	public String middleAddrName;
	public String lowerAddrName;
	public String detailAddrName;
	public String firstNo;
	public String secondNo;
	public String desc;
	
	public double getCenterLatitude() {
		double front = Double.parseDouble(frontLat);
		double noor = Double.parseDouble(noorLat);
		return ( front + noor ) / 2;
	}
	
	public double getCenterLongitude() {
		double front = Double.parseDouble(frontLon);
		double noor = Double.parseDouble(noorLon);
		return ( front + noor ) / 2;
	}
	
	@Override
	public String toString() {
		return name + "(" + lowerAddrName + ")";
	}
	
}
