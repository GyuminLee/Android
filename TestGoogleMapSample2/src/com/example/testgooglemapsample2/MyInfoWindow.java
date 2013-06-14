package com.example.testgooglemapsample2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindow implements InfoWindowAdapter {

	View infoWindow;
	TextView titleView;
	TextView snippetView;
	
	public MyInfoWindow(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		infoWindow = inflater.inflate(R.layout.info_window, null);
		titleView = (TextView)infoWindow.findViewById(R.id.title);
		snippetView = (TextView)infoWindow.findViewById(R.id.snippet);
	}
	@Override
	public View getInfoContents(Marker marker) {
		titleView.setText(marker.getTitle());
		snippetView.setText(marker.getSnippet());
		return infoWindow;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

}
