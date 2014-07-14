package com.example.sample4location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager mLM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
			Toast.makeText(
					MainActivity.this,
					"lat : " + location.getLatitude() + ",lng : "
							+ location.getLongitude(), Toast.LENGTH_SHORT)
					.show();
		}
	};

	boolean isFirst = true;

	@Override
	protected void onStart() {
		super.onStart();
		if (!mLM.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && isFirst) {
			Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(i);
			isFirst = false;
			return;
		}

		if (mLM.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			Location location = mLM
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				mListener.onLocationChanged(location);
			}
			mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
					mListener);
		} else {
			Toast.makeText(this, "finish app", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
}
