package com.example.testgooglemapsample2;

import java.util.HashMap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

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
	GoogleMap.OnMarkerClickListener, 
	GoogleMap.OnInfoWindowClickListener,
	GoogleMap.OnCameraChangeListener {

	GoogleMap mMap;
	LocationManager mLocationManager;
	HashMap<String,Marker> mMarkerList = new HashMap<String,Marker>();
	HashMap<Marker,String> mResolveKeyList = new HashMap<Marker,String>();
	int nNumber = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeed();
		
		Button btn = (Button)findViewById(R.id.zoomIn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraUpdate update = CameraUpdateFactory.zoomIn();
				mMap.animateCamera(update);
			}
		});
		
		btn = (Button)findViewById(R.id.zoomOut);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraUpdate update = CameraUpdateFactory.zoomOut();
				mMap.animateCamera(update);
			}
		});
		
		btn = (Button)findViewById(R.id.addMarker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraPosition pos = mMap.getCameraPosition();
				MarkerOptions options = new MarkerOptions();
				options.position(pos.target);
				options.anchor(0.5f, 0.5f);
				options.title("mymarker");
				options.snippet("sub title");
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
				options.draggable(true);
				Marker marker = mMap.addMarker(options);
				
				String data = "key"+ nNumber++;
				mMarkerList.put(data, marker);
				mResolveKeyList.put(marker,data);
			}
		});
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeed();
	}
	
	private void setUpMapIfNeed() {
		if (mMap == null) {
			mMap = ((SupportMapFragment)getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			setUpMap();
		}
	}
	
	private void setUpMap() {
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.setOnCameraChangeListener(this);
		mMap.setOnInfoWindowClickListener(this);
		mMap.setOnMapClickListener(this);
		mMap.setOnMarkerClickListener(this);
	}
	

	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);
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
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			CameraPosition pos = CameraPosition.builder().target(latLng)
					.zoom(14.5f)
					.bearing(10)
					.tilt(22.5f).build();
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(pos);
			mMap.animateCamera(update);
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onCameraChange(CameraPosition arg0) {
		
	}


	@Override
	public void onInfoWindowClick(Marker arg0) {
		
	}


	@Override
	public boolean onMarkerClick(Marker marker) {
		String data = mResolveKeyList.get(marker);
		return false;
	}


	@Override
	public void onMapClick(LatLng latlng) {
		
	}

}
