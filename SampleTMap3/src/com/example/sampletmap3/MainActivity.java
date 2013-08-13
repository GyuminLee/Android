package com.example.sampletmap3;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.skp.Tmap.TMapView;

public class MainActivity extends Activity {

	TMapView mMapView;
	public static final String API_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	boolean isInitialized = false;
	LocationManager mLM;
	Location mCacheLocation;
	
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
				moveLocation(location);
			} else {
				mCacheLocation = location;
			}
		}
	};
	
	private void moveLocation(Location location) {
		mMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
		mMapView.setZoom(15.5f);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMapView = (TMapView)findViewById(R.id.map);
		new ApiSetup().execute("");
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Button btn = (Button)findViewById(R.id.zoomIn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					mMapView.MapZoomIn();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.zoomOut);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					int zoom = mMapView.getZoom();
					mMapView.MapZoomOut();
				}
			}
		});
	}
	
	
	@Override
	protected void onStart() {
		Location location = mLM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		mLM.removeUpdates(mListener);
		super.onStop();
	}
	
	public class ApiSetup extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			setUpApiKey();
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			setUpMap();
		}
		
	}

	private void setUpApiKey() {
		mMapView.setSKPMapApiKey(API_KEY);
		mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
	}
	
	private void setUpMap() {
		// move map...
		// type setting...
		mMapView.setMapType(mMapView.MAPTYPE_STANDARD);
		mMapView.setTrafficInfo(true);
		mMapView.setTrackingMode(false);
		mMapView.setCompassMode(false);
		mMapView.setSightVisible(true);
		
		if (mCacheLocation != null) {
			moveLocation(mCacheLocation);
		}
		
		isInitialized = true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
