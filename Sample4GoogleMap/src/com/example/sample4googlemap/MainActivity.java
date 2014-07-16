package com.example.sample4googlemap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {

	SupportMapFragment smf;
	GoogleMap mMap;
	LocationManager mLM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupMapIfNeeded();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}
	
	LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			if (mMap == null) return;
			LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude()); 
			CameraPosition position = new CameraPosition.Builder()
				.target(latlng)
				.zoom(12.5f)
				.bearing(30)
				.tilt(30)
				.build();
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
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
		setupMapIfNeeded();
	}
	
	private void setupMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment1)).getMap();
			if (mMap != null) {
				setupMap();
			}
		}
	}
	
	private void setupMap() {
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(false);	
	}
}
