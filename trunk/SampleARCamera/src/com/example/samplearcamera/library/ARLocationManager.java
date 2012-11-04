package com.example.samplearcamera.library;

import java.util.ArrayList;

import com.example.samplearcamera.MyApplication;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class ARLocationManager {
	
	private LocationManager mLocationManager;
	private String mProvider;
	private Location mCurrentLocation;
	
	public interface OnMarkerUpdateListener {
		public void onMarkerUpdate(Location location);
	}
	
	private ArrayList<OnMarkerUpdateListener> mListeners = new ArrayList<OnMarkerUpdateListener>();
	
	private static ARLocationManager instance;
	
	public static ARLocationManager getInstance() {
		if (instance == null) {
			instance = new ARLocationManager();
		}
		return instance;
	}
	
	private ARLocationManager() {
		mLocationManager = (LocationManager)MyApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
		mProvider = getBestProvider();
	}
	
	public String getBestProvider() {
		String provider = null;
		Criteria criteria = new Criteria();
		// 정확도
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// 전력사용 정도
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 고도 정보
		criteria.setAltitudeRequired(false);
		// 방위 정보
		criteria.setBearingRequired(false);
		// 속도 정보
		criteria.setSpeedRequired(false);
		// 비용
		criteria.setCostAllowed(true);

		provider = mLocationManager.getBestProvider(criteria, true);
		
		if (provider == null) {
			provider = LocationManager.NETWORK_PROVIDER;
		}
		return provider;
	}

	public void start(OnMarkerUpdateListener listener) {
		mListeners.add(listener);
		if (mListeners.size() == 1) {
			mLocationManager.requestLocationUpdates(mProvider, 0, 50, mPOIListener);
			mLocationManager.requestLocationUpdates(mProvider, 1000, 2, mCurrentLocationListener);
		}
		Location location = mLocationManager.getLastKnownLocation(mProvider);
		if (location != null) {
			ARCamera.getInstance().setViewLocation(location);
			listener.onMarkerUpdate(location);
		}
	}
	
	public void stop(OnMarkerUpdateListener listener) {
		mListeners.remove(listener);
		if (mListeners.size() == 0) {
			mLocationManager.removeUpdates(mPOIListener);
			mLocationManager.removeUpdates(mCurrentLocationListener);
		}
	}
	
	private LocationListener mPOIListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			ARCamera.getInstance().setViewLocation(location);
			for (OnMarkerUpdateListener listener : mListeners) {
				listener.onMarkerUpdate(location);
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			
		}
		
	};
	
	private LocationListener mCurrentLocationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			ARSensorManager.getInstance().setLocation(location);
			ARCamera.getInstance().setViewLocation(location);
			mCurrentLocation = location;
		}

		@Override
		public void onProviderDisabled(String arg0) {
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			
		}
		
	};
	
	public Location getCurrentLocation() {
		if (mCurrentLocation == null) {
			mCurrentLocation = mLocationManager.getLastKnownLocation(mProvider);
		}
		return mCurrentLocation;
	}
	
}
