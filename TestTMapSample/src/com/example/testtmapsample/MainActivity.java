package com.example.testtmapsample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

public class MainActivity extends Activity {

	TMapView mMapView;
	LocationManager mLocationManager;
	boolean isInitialized = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMapView = (TMapView)findViewById(R.id.map);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mMapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						setUpMap();
					}
				});
			}
		}).start();
		
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLocationManager.removeUpdates(mListener);
	}
	
	Location mCacheLocation = null;
	
	LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			if (isInitialized) {
				setMyLocation(location);
				setMoveMap(location);
			} else {
				mCacheLocation = location;
			}
		}
	};
	
	private void setMoveMap(Location location) {
		mMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}

	private void setMyLocation(Location location) {
		mMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mMapView.setIcon(icon);
		mMapView.setIconVisibility(true);
	}
	
	private void setUpMap() {
//		mMapView.setCompassMode(true);
//		mMapView.setTrackingMode(true);
		mMapView.setTrafficInfo(true);
		mMapView.setSightVisible(true);
		mMapView.setOnClickListenerCallBack(clickCallback);
		isInitialized = true;
		if (mCacheLocation != null) {
			setMyLocation(mCacheLocation);
			setMoveMap(mCacheLocation);
			mCacheLocation = null;
		}
	}
	
	TMapView.OnClickListenerCallback clickCallback = new TMapView.OnClickListenerCallback() {
		
		@Override
		public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint mappoint, PointF point) {
			
			Toast.makeText(MainActivity.this, "onPressUpEvent : " + mappoint.getLatitude() + "," + mappoint.getLongitude(), Toast.LENGTH_SHORT).show();
			return false;
		}
		
		@Override
		public boolean onPressEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint mappoint, PointF point) {
			Toast.makeText(MainActivity.this, "onPressEvent : " + mappoint.getLatitude() + "," + mappoint.getLongitude(), Toast.LENGTH_SHORT).show();

			return false;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
