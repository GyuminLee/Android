package com.example.sample3tmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.skp.Tmap.TMapView;

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

		TMapView mMapView;
		LocationManager mLM;
		
		public PlaceholderFragment() {
		}

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
				if (isInitialzed) {
					moveMap(location);
					setMyLocation(location);
				} else {
					cacheLocation = location;
				}
			}
		};
		
		Location cacheLocation = null;
		private void moveMap(Location location) {
			mMapView.setCenterPoint(location.getLongitude(), location.getLatitude(), true);
		}
		
		private void setMyLocation(Location location) {
			mMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
		}
		
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
			mLM.removeUpdates(mListener);
			super.onStop();
		}
		
		boolean isInitialzed = false;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			mMapView = (TMapView)rootView.findViewById(R.id.tmap);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					mMapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
					mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
					return null;
				}
				
				@Override
				protected void onPostExecute(Void result) {
					setupMap();
					super.onPostExecute(result);
				}
			}.execute();
			return rootView;
		}
		
		private void setupMap() {
			mMapView.setMapType(mMapView.MAPTYPE_STANDARD);
			mMapView.setTrafficInfo(true);
//			mMapView.setSightVisible(true);
			mMapView.setCompassMode(true);
			isInitialzed = true;
			Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
			mMapView.setIcon(icon);
			mMapView.setIconVisibility(true);
			if (cacheLocation != null) {
				moveMap(cacheLocation);
				setMyLocation(cacheLocation);
			}
		}
		
		
	}

}
