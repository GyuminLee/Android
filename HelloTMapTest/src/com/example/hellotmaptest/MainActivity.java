package com.example.hellotmaptest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.skp.Tmap.TMapView;

public class MainActivity extends Activity {
	boolean isInitialized = false;
	TMapView mapView;
	public static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	LocationManager mLM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (TMapView)findViewById(R.id.mapView);
		new ApiKeySetTask().execute("");
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
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
		mLM.removeUpdates(mListener);
		super.onStop();
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
				setMyLocation(location);
				moveLocation(location);
			} else {
				tempLocation = location;
			}
		}
	};
	
	Location tempLocation;

	private void setMyLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mapView.setIcon(icon);
	}
	private void moveLocation(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	class ApiKeySetTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			mapView.setSKPMapApiKey(APP_KEY);
			mapView.setLanguage(mapView.LANGUAGE_KOREAN);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			setUpMap();
			super.onPostExecute(result);
		}
	}

	public void setUpMap() {
		
		isInitialized = true;
		if (tempLocation != null) {
			setMyLocation(tempLocation);
			moveLocation(tempLocation);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
