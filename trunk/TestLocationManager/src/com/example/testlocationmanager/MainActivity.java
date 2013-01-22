package com.example.testlocationmanager;

import java.io.IOException;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager mLocationManager;

	Criteria mCriteria;
		
	LocationListener mListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, 
					"lat : " + location.getLatitude() 
					+ ",lng : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
			if (Geocoder.isPresent()) {
				Geocoder geocoder = new Geocoder(MainActivity.this, Locale.KOREAN);
				try {
					List<Address> mAddresses = geocoder.getFromLocation(
							location.getLatitude(), 
							location.getLongitude(), 10);
					
					for (Address address : mAddresses) {
						Toast.makeText(MainActivity.this, "address : " + address.toString(), Toast.LENGTH_SHORT).show(); 
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
			// TODO Auto-generated method stub
			
		}

	};
	
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		mCriteria = new Criteria();
		mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		mCriteria.setCostAllowed(true);
		mCriteria.setPowerRequirement(Criteria.POWER_LOW);
		
		keywordView = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Geocoder.isPresent()) {
					Geocoder geocoder = new Geocoder(MainActivity.this, Locale.KOREAN);
					String keyword = keywordView.getText().toString();
					
					try {
						List<Address> addresses = geocoder.getFromLocationName(keyword, 10);
						for (Address addr : addresses) {
							Toast.makeText(MainActivity.this, "Location : " + addr.toString(), Toast.LENGTH_SHORT).show();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		String provider = mLocationManager.getBestProvider(mCriteria, true);
		if (provider == null) {
			provider = mLocationManager.NETWORK_PROVIDER;
		}
		mLocationManager.requestLocationUpdates(provider, 0, 0, mListener);
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
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
