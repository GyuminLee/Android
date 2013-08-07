package com.example.samplenavermovies.model;

import java.util.ArrayList;

public class GooglePlaceItem {
	Geometry geometry;
	String icon;
	String id;
	String name;
	ArrayList<GooglePhoto> photos;
	int price_level;
	float rating;
	String reference;
	ArrayList<String> types;
	String vicinity;
	
	@Override
	public String toString() {
		return "name : " + name + "\n\rvicinity : " + vicinity;
	}
}
