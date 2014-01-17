package com.example.sample2googlemap;

import java.util.HashMap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
	GoogleMap.OnMarkerClickListener, 
	GoogleMap.OnMapClickListener,
	GoogleMap.OnMapLongClickListener,
	GoogleMap.OnInfoWindowClickListener,
	GoogleMap.OnCameraChangeListener,
	GoogleMap.OnMapLoadedCallback {

	GoogleMap mMap;
	LocationManager mLM;
	HashMap<String,MyData> mValueResolver = new HashMap<String,MyData>();
	HashMap<MyData,Marker> mMarkerResolver = new HashMap<MyData,Marker>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeed();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Button btn = (Button)findViewById(R.id.btnMarker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraPosition position = mMap.getCameraPosition();
				MarkerOptions options = new MarkerOptions();
				options.position(position.target);
				options.anchor(0.5f, 0.5f);
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				options.draggable(false);
				options.title("title");
				options.snippet("description");
				Marker marker = mMap.addMarker(options);
				MyData data = new MyData();
				data.name = "ysi";
				data.age = 40;
				mValueResolver.put(marker.getId(), data);
				mMarkerResolver.put(data, marker);
			}
		});
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
			LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
			CameraPosition.Builder builder = new CameraPosition.Builder();
			builder.target(position);
			builder.zoom(15.5f);
			builder.bearing(0);
			builder.tilt(0);
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(builder.build());
//			CameraUpdate update2 = CameraUpdateFactory.newLatLngZoom(position, 15.5f);
//			mMap.moveCamera(update);
			mMap.animateCamera(update);
		}
	};
	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeed();
	}
	
	private void setUpMapIfNeed() {
		if (mMap == null) {
			SupportMapFragment f = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment1);
			mMap = f.getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}
	
	class MyInfoWindow implements InfoWindowAdapter {

		View infoView;
		TextView titleView;
		TextView snippetView;
		
		public MyInfoWindow(Context context) {
			infoView = LayoutInflater.from(context).inflate(R.layout.info_window_layout, null);
			titleView = (TextView)infoView.findViewById(R.id.title);
			snippetView = (TextView)infoView.findViewById(R.id.snippet);
		}
		
		@Override
		public View getInfoContents(Marker marker) {
			titleView.setText(marker.getTitle());
			snippetView.setText(marker.getSnippet());
			return infoView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	private void setUpMap() {
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.getUiSettings().setMyLocationButtonEnabled(false);
		mMap.getUiSettings().setTiltGesturesEnabled(false);
		mMap.getUiSettings().setRotateGesturesEnabled(false);
		mMap.setOnMarkerClickListener(this);
		mMap.setOnMapClickListener(this);
		mMap.setOnMapLoadedCallback(this);
		mMap.setOnMapLongClickListener(this);
		mMap.setOnCameraChangeListener(this);
		mMap.setOnInfoWindowClickListener(this);
		mMap.setInfoWindowAdapter(new MyInfoWindow(this));
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		MyData d = mValueResolver.get(marker.getId());
		marker.showInfoWindow();
		return true;
	}

	@Override
	public void onCameraChange(CameraPosition position) {
		
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		
	}

	@Override
	public void onMapLongClick(LatLng latlng) {
		
	}

	@Override
	public void onMapClick(LatLng latlng) {
		
	}

	@Override
	public void onMapLoaded() {
	
	}

}
