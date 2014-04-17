package com.example.sample3googlemap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

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
	public static class PlaceholderFragment extends Fragment implements
	GoogleMap.OnCameraChangeListener {

		public PlaceholderFragment() {
		}

		GoogleMap mMap;
		LocationManager mLM;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mLM = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		}

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
				if (mMap != null) {
					// ....
					
					LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
					CameraPosition.Builder builder = new CameraPosition.Builder();
					builder.target(latLng);
					builder.zoom(15.5f);
					builder.bearing(10.5f);
					builder.tilt(45);
					CameraPosition position = builder.build();
//					CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15.5f);
					CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
					
					mMap.animateCamera(update);
				}
			}
		};
		
		@Override
		public void onStart() {
			super.onStart();
			String provider = LocationManager.NETWORK_PROVIDER;
			Location location = mLM.getLastKnownLocation(provider);
			if (location != null) {
				mListener.onLocationChanged(location);
			}
			mLM.requestLocationUpdates(provider, 0, 0, mListener);
		}
		
		@Override
		public void onStop() {
			super.onStop();
			mLM.removeUpdates(mListener);
		}
		
		@Override
		public void onResume() {
			super.onResume();
			setupMapIfNeeded();
		}

		private void setupMapIfNeeded() {
			if (mMap == null) {
//				mMap = ((SupportMapFragment) ((ActionBarActivity)getActivity())
//						.getSupportFragmentManager().findFragmentById(
//								R.id.fragment1)).getMap();
				mMap = ((SupportMapFragment)getFragmentManager().findFragmentById(
								R.id.fragment1)).getMap();
				if (mMap != null) {
					setupMap();
				}
			}
		}

		private void setupMap() {
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			mMap.getUiSettings().setCompassEnabled(true);
			
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			setupMapIfNeeded();
			Button btn = (Button)rootView.findViewById(R.id.btnZoomIn);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CameraUpdate update = CameraUpdateFactory.zoomIn();
					mMap.animateCamera(update);
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnZoomOut);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CameraUpdate update = CameraUpdateFactory.zoomOut();
					mMap.animateCamera(update);
				}
			});
			return rootView;
		}

		@Override
		public void onCameraChange(CameraPosition position) {
						
		}
	}

}
