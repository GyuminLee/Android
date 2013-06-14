package com.example.testlocationsample;

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
	EditText keyword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mCriteria = new Criteria();
		mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		mCriteria.setPowerRequirement(Criteria.POWER_LOW);
		mCriteria.setCostAllowed(true);
		keyword = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Geocoder.isPresent()) {
					Geocoder geo = new Geocoder(MainActivity.this, Locale.KOREA);
					String text = keyword.getText().toString();
					try {
						List<Address> mList = geo.getFromLocationName(text, 10);
						for (Address addr : mList) {
							Toast.makeText(MainActivity.this, "location : " + addr.getLatitude() + "," + addr.getLongitude(), Toast.LENGTH_SHORT).show();
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
		super.onStart();
		String provider = mLocationManager.getBestProvider(mCriteria, true);
		Location location = mLocationManager.getLastKnownLocation(provider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		
//		Intent i = new Intent(this, MyLocationService.class);
//		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
//		mLocationManager.requestSingleUpdate(mCriteria, pi);
//		mLocationManager.requestSingleUpdate(mCriteria, mListener, null);
		
		mLocationManager.requestLocationUpdates(provider, 
				2000, 
				5, 
				mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLocationManager.removeUpdates(mListener);
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
			Toast.makeText(MainActivity.this, 
					"Location : " + location.getLatitude() + 
					"," + location.getLatitude() + 
					"," + location.getAccuracy(), 
					Toast.LENGTH_SHORT).show();
			if (Geocoder.isPresent()) {
				Geocoder geo = new Geocoder(MainActivity.this, Locale.KOREA);
				try {
					List<Address> mList = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
					for (Address addr : mList) {
						Toast.makeText(MainActivity.this, addr.toString(), Toast.LENGTH_SHORT).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} else {
				Toast.makeText(MainActivity.this, "not exist geocoder", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
