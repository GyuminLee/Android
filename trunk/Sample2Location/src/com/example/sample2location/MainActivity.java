package com.example.sample2location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager mLM;
	TextView messageView;
	Criteria mCriteria;
	ListView listView;
	EditText keywordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.messageView);
		listView = (ListView) findViewById(R.id.listView1);
		keywordView = (EditText) findViewById(R.id.editText1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Geocoder.isPresent()) {
					String keyword = keywordView.getText().toString();
					if (keyword != null && !keyword.equals("")) {
						Geocoder geo = new Geocoder(MainActivity.this,
								Locale.KOREA);
						try {
							List<Address> addresses = geo.getFromLocationName(
									keyword, 10);
							ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(
									MainActivity.this,
									android.R.layout.simple_list_item_1,
									addresses);
							listView.setAdapter(adapter);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Address address = (Address)listView.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this, AlertService.class);
				intent.putExtra(AlertService.PARAM_ADDRESS, address);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, 0);
				long time = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
				mLM.addProximityAlert(address.getLatitude(), address.getLongitude(), 500, -1, pi);
			}
		});
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mCriteria = new Criteria();
		mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		mCriteria.setPowerRequirement(Criteria.POWER_LOW);
		mCriteria.setCostAllowed(true);
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
			messageView.setText("lat : " + location.getLatitude() + ", lng : "
					+ location.getLongitude());
			if (Geocoder.isPresent()) {
				Geocoder geo = new Geocoder(MainActivity.this, Locale.KOREA);
				try {
					List<Address> addresses = geo
							.getFromLocation(location.getLatitude(),
									location.getLongitude(), 10);
					ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(
							MainActivity.this,
							android.R.layout.simple_list_item_1, addresses);
					listView.setAdapter(adapter);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		String provider = mLM.getBestProvider(mCriteria, true);

		if (provider == null
				|| !mLM.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(i);
			Toast.makeText(this, "....", Toast.LENGTH_SHORT).show();
			return;
		}
		Location location = mLM
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
//		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5,
//				mListener);
		mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mListener, null);
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
