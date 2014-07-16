package com.example.sample4tmapview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.skp.Tmap.TMapView;

public class MainActivity extends Activity {

	TMapView mapView;
	private static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	LocationManager mLM;
	boolean isInitialized = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (TMapView)findViewById(R.id.mapView);
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		new RegisterTask().execute();
	}
	
	class RegisterTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			mapView.setSKPMapApiKey(APP_KEY);
			mapView.setLanguage(mapView.LANGUAGE_KOREAN);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result != null && result) {
				initailizeMap();
			}
		}
	}
	
	private void initailizeMap() {
		isInitialized = true;
		mapView.setMapType(mapView.MAPTYPE_STANDARD);
		mapView.setTrafficInfo(true);
//		mapView.setCompassMode(true);
		mapView.setSightVisible(true);
//		mapView.setTrackingMode(true);
		mapView.setIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());
		mapView.setIconVisibility(true);
		if (cacheLocation != null) {
			moveMap(cacheLocation);
			setMyLocation(cacheLocation);
		}
	}

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
				moveMap(location);
				setMyLocation(location);
			} else {
				cacheLocation = location;
			}
		}
	};
	Location cacheLocation = null;
	private void moveMap(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	private void setMyLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
}
