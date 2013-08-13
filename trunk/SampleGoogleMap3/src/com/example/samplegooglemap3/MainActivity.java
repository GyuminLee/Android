package com.example.samplegooglemap3;

import java.util.HashMap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements 
	GoogleMap.OnMapClickListener,
	GoogleMap.OnMarkerClickListener {

	GoogleMap mMap;
	LocationManager mLM;
	HashMap<String, MyData> mValueResolve = new HashMap<String, MyData>();
	HashMap<MyData, Marker> mMarkerResolve = new HashMap<MyData, Marker>();

	int mIndex = 0;
	
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
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			CameraPosition.Builder builder = new CameraPosition.Builder();
			builder.target(latLng).zoom(15.5f).bearing(30).tilt(22);
			CameraPosition pos = builder.build();
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(pos);
			mMap.animateCamera(update);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	protected void onStart() {
		Location location = mLM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		mLM.removeUpdates(mListener);
		super.onStop();
	}
	
	
	@Override
	protected void onResume() {
		setUpMapIfNeeded();
		super.onResume();
	}
	
	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			setUpMap();
		}
	}

	private void setUpMap() {
//		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		mMap.setMyLocationEnabled(true);
//		mMap.setTrafficEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.getUiSettings().setCompassEnabled(true);
		
		mMap.setOnMapClickListener(this);
		mMap.setOnMarkerClickListener(this);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onMapClick(LatLng latLng) {
		MarkerOptions options = new MarkerOptions();
		options.position(latLng);
		options.title("MyMarker" + mIndex);
		options.snippet("description");
		options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		options.anchor(0.5f, 1.0f);
		
		Marker marker = mMap.addMarker(options);
		MyData data = new MyData();
		data.mIndex = mIndex;
		data.name = "MyMarker" + mIndex;
		mValueResolve.put(marker.getId(), data);
		mMarkerResolve.put(data, marker);
		
		mIndex++;
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		MyData value = mValueResolve.get(marker.getId());
		Toast.makeText(this, "marker clicked : " + marker.getTitle() + ", value : " + value.mIndex, Toast.LENGTH_SHORT).show();
		marker.showInfoWindow();
		return true;
	}

}
