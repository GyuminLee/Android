package com.example.samplelocationtest;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView message;
	LocationManager lm;
	String mProvider;
	EditText inputView;
	TextView positionMessage;
	
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
					"location lat : " + location.getLatitude() + ", lng : " + location.getLongitude(), 
					Toast.LENGTH_SHORT).show();
			
			message.setText("location lat : " + location.getLatitude() + ", lng : " + location.getLongitude());
			
			if (Geocoder.isPresent()) {
				Geocoder geocoder = new Geocoder(MainActivity.this, Locale.KOREA);
				try {
					List<Address> mList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
					StringBuilder sb = new StringBuilder();
					
					for (Address addr : mList) {
						sb.append(addr.toString());
						sb.append("\n\r");
					}
					message.setText("address" + sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		message = (TextView)findViewById(R.id.location);
		
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setBearingRequired(false);
		
		mProvider = lm.getBestProvider(criteria, true);
		if (mProvider == null) {
			mProvider = LocationManager.NETWORK_PROVIDER;
		}
		
		inputView = (EditText)findViewById(R.id.editText1);
		positionMessage = (TextView)findViewById(R.id.position);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Geocoder.isPresent()) {
					Geocoder geocoder = new Geocoder(MainActivity.this, Locale.KOREA);
					try {
						List<Address> mList = geocoder.getFromLocationName(inputView.getText().toString(), 10);
						StringBuilder sb = new StringBuilder();
						if (mList.size() > 0) {
							Address addr = mList.get(0);
							long expiration = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
							Intent i = new Intent(MainActivity.this, MyService.class);
							i.putExtra("address", addr.toString());
							PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, i , 0);
							lm.addProximityAlert(addr.getLatitude(), addr.getLongitude(), 50, expiration, pi);
						}
						for (Address addr : mList) {
							sb.append(addr.getLocality() + " lat : " + addr.getLatitude() + ", lng :" + addr.getLongitude());
							sb.append("\n\r");
						}
						positionMessage.setText(sb.toString());
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
		lm.requestLocationUpdates(
				mProvider, 
				5000, 
				5, 
				mListener);
		
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		lm.removeUpdates(mListener);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
