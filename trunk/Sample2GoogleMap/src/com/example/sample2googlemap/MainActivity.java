package com.example.sample2googlemap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {

	GoogleMap mMap;
	LocationManager mLM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeed();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
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
			LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
			CameraPosition.Builder builder = new CameraPosition.Builder();
			builder.target(position);
			builder.zoom(15.5f);
			builder.bearing(0);
			builder.tilt(0);
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(builder.build());
//			CameraUpdate update2 = CameraUpdateFactory.newLatLngZoom(position, 15.5f);
//			mMap.moveCamera(update);
			mMap.animateCamera(update);
		}
	};
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
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeed();
	}
	
	private void setUpMapIfNeed() {
		if (mMap == null) {
			SupportMapFragment f = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment1);
			mMap = f.getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}
	
	private void setUpMap() {
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
