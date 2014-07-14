package com.example.sample4location;

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
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager mLM;
	
	Criteria mCriteria;
	String mProvider;
	ListView listView;
	EditText keywordView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.editText1);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		mCriteria = new Criteria();
		mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		mCriteria.setAltitudeRequired(false);
		mCriteria.setSpeedRequired(false);
		mCriteria.setPowerRequirement(Criteria.POWER_LOW);
		mCriteria.setCostAllowed(true);
		mProvider = mLM.getBestProvider(mCriteria, true);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address addr = (Address)listView.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this, AlertService.class);
				intent.putExtra("address", addr);
				intent.setData(Uri.parse("myscheme://myalert/10"));
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, 0);
				long expired = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
				int radius = 100;
				mLM.addProximityAlert(addr.getLatitude(), addr.getLongitude(), radius, expired, pi);
			}
		});
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					if (Geocoder.isPresent()) {
						Geocoder geo = new Geocoder(MainActivity.this, Locale.KOREAN);
						try {
							List<Address> addresses = geo.getFromLocationName(keyword, 10);
							ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(MainActivity.this, android.R.layout.simple_list_item_1, addresses);
							listView.setAdapter(adapter);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						Toast.makeText(MainActivity.this, "not support geocoder", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	LocationListener mListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch(status) {
				case LocationProvider.AVAILABLE :
				case LocationProvider.OUT_OF_SERVICE :
				case LocationProvider.TEMPORARILY_UNAVAILABLE :
			}
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
			
			if (Geocoder.isPresent()) {
				Geocoder geo = new Geocoder(MainActivity.this);
				try {
					List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
					ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(MainActivity.this, android.R.layout.simple_list_item_1, addresses);
					listView.setAdapter(adapter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(MainActivity.this, "not support geocoder", Toast.LENGTH_SHORT).show();
			}
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
			
			Intent intent = new Intent(this, MyService.class);
			intent.putExtra("name", "value");
			PendingIntent pi = PendingIntent.getService(this, 0, intent, 0);
			
			mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, pi);
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
