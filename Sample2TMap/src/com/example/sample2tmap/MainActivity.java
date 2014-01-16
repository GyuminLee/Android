package com.example.sample2tmap;

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
	TMapView mMap;
	LocationManager mLM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMap = (TMapView)findViewById(R.id.map);
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		new MapRegisterTask().execute("");
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
				moveMyLocation(location);
			} else {
				cacheLocation = location;
			}
		}
	};

	Location cacheLocation = null;
	boolean isInitialized = false;
	
	private void moveMap(Location location) {
		if (isInitialized) {
			mMap.setCenterPoint(location.getLongitude(), location.getLatitude());
		}
	}
	
	private void moveMyLocation(Location location) {
		if (isInitialized) {
			mMap.setLocationPoint(location.getLongitude(), location.getLatitude());
			Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
			mMap.setIcon(icon);
		}
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
	
	class MapRegisterTask extends AsyncTask<String, Integer, Boolean>{
		@Override
		protected Boolean doInBackground(String... params) {
			mMap.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
			mMap.setLanguage(mMap.LANGUAGE_KOREAN);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			setUpMap();
		}
	}
	
	private void setUpMap() {
		isInitialized = true;
		if (cacheLocation != null) {
			moveMap(cacheLocation);
			moveMyLocation(cacheLocation);
			cacheLocation = null;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
