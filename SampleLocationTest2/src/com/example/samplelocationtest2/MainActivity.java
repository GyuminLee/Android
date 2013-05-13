package com.example.samplelocationtest2;

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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager mLocationManager;
	String mProvider;
	EditText keywordView;
	ListView list;
	ArrayAdapter<Address> mAdapter;
	
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
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, 
					"Location lat : " + location.getLatitude() + ", lng : " + location.getLongitude(), 
					Toast.LENGTH_SHORT).show();
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setCostAllowed(true);
		mProvider = mLocationManager.getBestProvider(criteria, true);
		
		Button btn = (Button)findViewById(R.id.singleUpdate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, MyLocationService.class);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, i, 0);
				mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, pi);
			}
		});
		keywordView = (EditText)findViewById(R.id.editText1);
		list = (ListView)findViewById(R.id.listView1);
		
		btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Geocoder.isPresent()) {
					Geocoder geocoder = new Geocoder(MainActivity.this, Locale.KOREA);
					try {
						List<Address> addresses = geocoder.getFromLocationName(keywordView.getText().toString(), 10);
						mAdapter = new ArrayAdapter<Address>(MainActivity.this, android.R.layout.simple_list_item_1, addresses);
						list.setAdapter(mAdapter);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Address address = mAdapter.getItem(position);
				Location location = new Location("MyAddressLocation");
				location.setLatitude(address.getLatitude());
				location.setLongitude(address.getLongitude());
				
				Intent i = new Intent(MainActivity.this, MyAlertService.class);
				i.putExtra(MyAlertService.PARAM_ALERT_LOCATION, location);
				i.putExtra(MyAlertService.PARAM_ALERT_ADDRESS, address);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, i, 0);
				mLocationManager.addProximityAlert(location.getLatitude(), 
						location.getLongitude(), 50, -1, pi);
				Toast.makeText(MainActivity.this, "ProximityAlert lat : " + location.getLatitude() + ", lng : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, mListener);
		
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationManager.removeUpdates(mListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
