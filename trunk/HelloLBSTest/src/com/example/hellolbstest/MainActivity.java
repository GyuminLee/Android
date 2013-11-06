package com.example.hellolbstest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager mLM;
	ListView listView;
	EditText keywordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view,
					int position, long id) {
				Address address = (Address) list.getItemAtPosition(position);
				Toast.makeText(MainActivity.this,
						"addr : " + address.toString(), Toast.LENGTH_SHORT)
						.show();
				Intent i = new Intent(MainActivity.this, AlertService.class);
				i.putExtra("address", address);
				PendingIntent pi = PendingIntent.getService(MainActivity.this,
						0, i, 0);
				long expiration = System.currentTimeMillis() + 24 * 60 * 60
						* 1000;
				mLM.addProximityAlert(address.getLatitude(),
						address.getLongitude(), 100, expiration, pi);
			}
		});
		keywordView = (EditText) findViewById(R.id.editText1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					if (Geocoder.isPresent()) {
						Geocoder coder = new Geocoder(MainActivity.this,
								Locale.KOREA);
						try {
							List<Address> addresses = coder
									.getFromLocationName(keyword, 10);
							ArrayAdapter<Address> aa = new ArrayAdapter<Address>(
									MainActivity.this,
									android.R.layout.simple_list_item_1,
									addresses);
							listView.setAdapter(aa);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

			}
		});
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	LocationListener mLocationListener = new LocationListener() {

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
			showCurrentLocation(location);
		}
	};

	private void showCurrentLocation(Location location) {
		Toast.makeText(
				this,
				"location lat : " + location.getLatitude() + ", lng : "
						+ location.getLongitude(), Toast.LENGTH_SHORT).show();
		if (Geocoder.isPresent()) {
			Geocoder coder = new Geocoder(this, Locale.KOREA);
			try {
				List<Address> addresses = coder.getFromLocation(
						location.getLatitude(), location.getLongitude(), 10);
				ArrayAdapter<Address> aa = new ArrayAdapter<Address>(this,
						android.R.layout.simple_list_item_1, addresses);
				listView.setAdapter(aa);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Toast.makeText(this, "geocoder not implements", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onStart() {
		// Intent i = new Intent(this,MyService.class);
		// i.putExtra("poi", "number10");
		// PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		//
		// mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, pi);
		//
		// mLM.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
		// mLocationListener, null);
		Location location = mLM
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			showCurrentLocation(location);
		}
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5,
				mLocationListener);
		super.onStart();
	}

	@Override
	protected void onStop() {
		mLM.removeUpdates(mLocationListener);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
