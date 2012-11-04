package com.example.samplearcamera.map;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samplearcamera.R;
import com.example.samplearcamera.googleplaces.GooglePlaceItem;
import com.example.samplearcamera.library.ARLocationManager;
import com.example.samplearcamera.library.POIManager;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ARMap extends MapActivity {

	MapView mapView;
	MapController mapCtrl;
	TextView name;
	MyItemizedOverlay itemOverlay;
	boolean isFocused = false;
	CompassView mCompassView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map_layout);

	    name = (TextView)findViewById(R.id.name);
	    mapView = (MapView)findViewById(R.id.mapView);
	    mapCtrl = mapView.getController();
	    mapCtrl.setZoom(16);

	    
	    ArrayList<GooglePlaceItem> items = POIManager.getInstance().getCurrentPOI();
	    
	    if (items.size() > 0) {
	    	Drawable marker = getResources().getDrawable(R.drawable.stat_happy);
	    	Drawable focusMarker = getResources().getDrawable(R.drawable.stat_sample);
	    	itemOverlay = new MyItemizedOverlay(marker);
	    	itemOverlay.setFocusMarker(focusMarker);
	    	mapView.getOverlays().add(itemOverlay);
	    	itemOverlay.add(items);
	    }

	    Button btn = (Button)findViewById(R.id.current);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Location loc = ARLocationManager.getInstance().getCurrentLocation();
				if (loc != null) {
					GeoPoint point = new GeoPoint((int)(loc.getLatitude()*1E6),(int)(loc.getLongitude()*1E6));
					mapCtrl.animateTo(point);
				}
			}
		});
	    
	    btn = (Button)findViewById(R.id.prev);
	    btn.setOnClickListener(mFocusMoveClickListener);
	    btn = (Button)findViewById(R.id.next);
	    btn.setOnClickListener(mFocusMoveClickListener);

	    addCompassView();
	}
	
	View.OnClickListener mFocusMoveClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			MyOverlayItem item;
			boolean isNext = true;
			if (v.getId() == R.id.next) {
				isNext = true;
			} else if (v.getId() == R.id.prev) {
				isNext = false;
			}
			if (isFocused == false) {
				item = itemOverlay.getItem(0);
				isFocused = true;
			} else {
				item = itemOverlay.nextFocus(isNext);
			}
			itemOverlay.setFocus(item);
			if (item == null) {
				Toast.makeText(ARMap.this, "item is null", Toast.LENGTH_SHORT).show();
			} else {
				Location location = new Location("poi");
				location.setLatitude((double)item.getPoint().getLatitudeE6() / 1E6);
				location.setLongitude((double)item.getPoint().getLongitudeE6() / 1E6);
				mCompassView.setTargetLocation(location);
				mapCtrl.animateTo(item.getPoint());
			}
		}
	};
	
	private void addCompassView() {
		mCompassView = new CompassView(this);
		MapView.LayoutParams lp = new MapView.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT, 
				0, 0, MapView.LayoutParams.TOP_LEFT);
		mapView.addView(mCompassView, lp);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
