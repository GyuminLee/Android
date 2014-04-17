package com.example.sample3locationmanager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		LocationManager mLM;
		Criteria mCriteria;

		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mLM = (LocationManager) getActivity().getSystemService(
					Context.LOCATION_SERVICE);
			mCriteria = new Criteria();
			mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
			mCriteria.setPowerRequirement(Criteria.POWER_LOW);
			mCriteria.setCostAllowed(true);
		}

		ListView listView;
		EditText editText;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			listView = (ListView)rootView.findViewById(R.id.listView1);
			editText = (EditText)rootView.findViewById(R.id.editText1);
			Button btn = (Button)rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String keyword = editText.getText().toString();
					if (Geocoder.isPresent()) {
						Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
						try {
							List<Address> addresses = geocoder.getFromLocationName(keyword, 10);
							ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(getActivity(), android.R.layout.simple_list_item_1, addresses);
							listView.setAdapter(adapter);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
				}
			});
			
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Address address = (Address)listView.getItemAtPosition(position);
					
					long expired = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
					Intent intent = new Intent(getActivity(), ProximityAlertService.class);
					intent.setData(Uri.parse("myscheme://com.example.sample3locationmanager/"+id));
					intent.putExtra("address", address);
					PendingIntent pi = PendingIntent.getService(getActivity(), 0, intent, 0);
					mLM.addProximityAlert(address.getLatitude(), address.getLongitude(), 100, -1, pi);					
				}
			});
			return rootView;
		}

		LocationListener mListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
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
				Toast.makeText(
						getActivity(),
						"lat : " + location.getLatitude() + ", lng : "
								+ location.getLongitude(), Toast.LENGTH_SHORT)
						.show();
				if (Geocoder.isPresent()) {
					Geocoder geocoder = new Geocoder(getActivity(),
							Locale.getDefault());
					try {
						List<Address> addresses = geocoder.getFromLocation(
								location.getLatitude(),
								location.getLongitude(), 10);
						ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(
								getActivity(),
								android.R.layout.simple_list_item_1, addresses);
						listView.setAdapter(adapter);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					Toast.makeText(getActivity(), "not implement geocoder",
							Toast.LENGTH_SHORT).show();
				}
			}
		};

		LocationListener mSingleListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
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
				Toast.makeText(getActivity(), "single location",
						Toast.LENGTH_SHORT).show();
			}
		};
		boolean isFirstExecute = true;

		@Override
		public void onStart() {
			super.onStart();
			// String provider = mLM.getBestProvider(mCriteria, true);
			String provider = LocationManager.NETWORK_PROVIDER;
			if (mLM.isProviderEnabled(provider)) {
				if (isFirstExecute) {
					isFirstExecute = false;
					Intent i = new Intent(
							Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(i);
				} else {
					Toast.makeText(getActivity(),
							"set network provider enable!!!",
							Toast.LENGTH_SHORT).show();
					getActivity().finish();
				}
				return;
			}
			Location location = mLM.getLastKnownLocation(provider);
			if (location != null) {
				mListener.onLocationChanged(location);
			}
			mLM.requestLocationUpdates(provider, 2000, 5, mListener);

			Intent intent = new Intent(getActivity(), MyService.class);
			PendingIntent pi = PendingIntent.getService(getActivity(), 0,
					intent, 0);
			mLM.requestSingleUpdate(provider, pi);
		}

		@Override
		public void onStop() {
			mLM.removeUpdates(mListener);
			super.onStop();
		}
	}

}
