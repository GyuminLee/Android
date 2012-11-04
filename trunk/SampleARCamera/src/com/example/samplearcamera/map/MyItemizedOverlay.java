package com.example.samplearcamera.map;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.drawable.Drawable;

import com.example.samplearcamera.googleplaces.GooglePlaceItem;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;

public class MyItemizedOverlay extends ItemizedOverlay<MyOverlayItem> {

	Drawable mMarker;
	Drawable mFocusMarker;
	ArrayList<GooglePlaceItem> mData = new ArrayList<GooglePlaceItem>();
	HashMap<GooglePlaceItem,MyOverlayItem> maps = new HashMap<GooglePlaceItem,MyOverlayItem>();
	
	public MyItemizedOverlay(Drawable marker) {
		super(marker);
		mMarker = marker;
		boundCenter(marker);
		populate();
	}

	public void add(GooglePlaceItem item) {
		mData.add(item);
		populate();
	}
	
	public void add(ArrayList<GooglePlaceItem> items) {
		mData.addAll(items);
		populate();
	}
	
	public void clear() {
		mData.clear();
		maps.clear();
		populate();
	}
	
	public void setFocusMarker(Drawable drawable) {
		mFocusMarker = drawable;
	}
	
	@Override
	protected MyOverlayItem createItem(int position) {
		// TODO Auto-generated method stub
		GooglePlaceItem item = mData.get(position);
		MyOverlayItem poi = maps.get(item);
		if (poi == null) {
			GeoPoint point = new GeoPoint((int)(item.geometry.location.lat * 1E6),(int)(item.geometry.location.lng * 1E6));			
			poi = new MyOverlayItem(point,item.name,item.vicinity);
			poi.setMarker(mMarker);
			poi.setFocusMarker(mFocusMarker);
			maps.put(item, poi);
		}
		return poi;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mData.size();
	}
	
	

}
