package com.example.samplelocationmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Criteria mCriteria;

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
			if (Geocoder.isPresent()) {
				Geocoder geo = new Geocoder(MainActivity.this, Locale.KOREA);
				try {
					List<Address> list = geo
							.getFromLocation(location.getLatitude(),
									location.getLongitude(), 10);
					mAdapter.clear();
					mAdapter.addAll(list);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Toast.makeText(MainActivity.this, "geocoder not installed",
						Toast.LENGTH_SHORT).show();
			}
			// Toast.makeText(MainActivity.this, "Location Lat : " +
			// location.getLatitude() +
			// ", lng : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
		}
	};

	ListView listView;
	EditText keywordView;
	ArrayAdapter<Address> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mCriteria = new Criteria();
		mCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
		mCriteria.setCostAllowed(true);
		mCriteria.setPowerRequirement(Criteria.POWER_HIGH);
		mCriteria.setAltitudeRequired(false);
		mCriteria.setSpeedRequired(false);
		mCriteria.setBearingRequired(false);
		listView = (ListView) findViewById(R.id.listView1);
		keywordView = (EditText) findViewById(R.id.editText1);
		mAdapter = new ArrayAdapter<Address>(this,
				android.R.layout.simple_list_item_1, new ArrayList<Address>());
		listView.setAdapter(mAdapter);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Geocoder.isPresent()) {
					String keyword = keywordView.getText().toString();
					if (keyword != null && !keyword.equals("")) {
						Geocoder geo = new Geocoder(MainActivity.this, Locale.KOREA);
						try {
							List<Address> list = geo.getFromLocationName(keyword, 10);
							mAdapter.clear();
							mAdapter.addAll(list);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				} else {
					Toast.makeText(MainActivity.this, "geocoder not installed", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		String provider = mLM.getBestProvider(mCriteria, true);
		Location location = mLM
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5,
				mListener);
		// HandlerThread th = new HandlerThread("mythread");

		// mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mListener,
		// th.getLooper());

		// Intent i = new Intent(this, LocationReceiveService.class);
		// PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		// mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, pi);
	}

	@Override
	protected void onStop() {
		super.onStop();

		mLM.removeUpdates(mListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
