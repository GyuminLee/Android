package com.example.samplelocationmanager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager mLM;
	
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
			Toast.makeText(MainActivity.this, "Location Lat : " + location.getLatitude() + 
					", lng : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
//		Location location = mLM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//		if (location != null) {
//			mListener.onLocationChanged(location);
//		}
//		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, mListener);
//		mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mListener, null);
		
		Intent i = new Intent(this, LocationReceiveService.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, pi);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
//		mLM.removeUpdates(mListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
