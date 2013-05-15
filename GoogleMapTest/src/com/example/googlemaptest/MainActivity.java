package com.example.googlemaptest;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements 
	GoogleMap.OnMapClickListener,
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener {

	GoogleMap mMap;
	LocationManager mLocationManager;
	ArrayList<Marker> markerList = new ArrayList<Marker>();
	HashMap<Marker,GooglePlaceItem> mDataMap = new HashMap<Marker,GooglePlaceItem>();
	
	
	int mCurrentMarkerIndex = -1;
	
	public static final int REQUEST_CODE_SEARCH_POI = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Button btn = (Button)findViewById(R.id.first);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCurrentMarkerIndex = -1;
				moveNextMarker();
			}
		});
		
		btn = (Button)findViewById(R.id.next);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				moveNextMarker();
			}
		});
		
		btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, SearchActivity.class);
				startActivityForResult(i, REQUEST_CODE_SEARCH_POI);
			}
		});
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SEARCH_POI) {
			if (resultCode == Activity.RESULT_OK) {
//				GooglePlaceItem item = data.getParcelableExtra(SearchActivity.RETURN_FIELD_POI);
				ArrayList<GooglePlaceItem> list = data.<GooglePlaceItem>getParcelableArrayListExtra(SearchActivity.RETURN_FIELD_POI);
				for (GooglePlaceItem item : list) {
					MarkerOptions options = new MarkerOptions();
					LatLng latLng = new LatLng(item.geometry.location.lat, item.geometry.location.lng);
					options.position(latLng)
						.title(item.name)
						.snippet(item.vicinity);
					Marker marker = mMap.addMarker(options);
					CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
					mMap.animateCamera(update);
					mDataMap.put(marker, item);
				}
			}
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
			
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
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				CameraPosition pos = CameraPosition.builder().target(latLng).zoom(15.5f).bearing(20.0f).tilt(45).build();
				CameraUpdate update = CameraUpdateFactory.newCameraPosition(pos);
				mMap.animateCamera(update);
			}
		}, null);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		setUpMapIfNeeded();
		myLocationSource.onResume();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		myLocationSource.onPause();
		super.onPause();
	}
	
	private void setUpMapIfNeeded() {
		// TODO Auto-generated method stub
		if (mMap == null) {
			mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			setUpMap();
		}
	}

	
	private void moveNextMarker() {
		if (mCurrentMarkerIndex + 1 < markerList.size()) {
			mCurrentMarkerIndex++;
			Marker marker = markerList.get(mCurrentMarkerIndex);
			CameraUpdate update = CameraUpdateFactory.newLatLng(marker.getPosition());
			mMap.animateCamera(update);
			marker.showInfoWindow();		
		} 
	}
	private void setUpMap() {
		// TODO Auto-generated method stub
		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.getUiSettings().setRotateGesturesEnabled(false);
		mMap.setOnMapClickListener(this);
		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
		mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this));
		mMap.setLocationSource(myLocationSource);
		mMap.setOnMapLongClickListener(myLocationSource);
		
	}

	MyLocationSource myLocationSource = new MyLocationSource();
	
	class MyLocationSource implements LocationSource, GoogleMap.OnMapLongClickListener {
		
		OnLocationChangedListener mListener;
		boolean isResumed = false;

		@Override
		public void onMapLongClick(LatLng latLng) {
			// TODO Auto-generated method stub
			if (mListener != null && isResumed) {
				Location location = new Location("MyLocation");
				location.setLatitude(latLng.latitude);
				location.setLongitude(latLng.longitude);
				location.setAccuracy(100);
				mListener.onLocationChanged(location);
			}
		}

		@Override
		public void activate(OnLocationChangedListener listener) {
			// TODO Auto-generated method stub
			mListener = listener;
		}

		@Override
		public void deactivate() {
			// TODO Auto-generated method stub
			mListener = null;
			
		}
		
		public void onPause() {
			isResumed = false;
		}
		
		public void onResume() {
			isResumed = true;
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	Marker mMyLastMarker;

	@Override
	public void onMapClick(LatLng latLng) {
		Projection projection = mMap.getProjection();
		Point point = projection.toScreenLocation(latLng);
		MarkerOptions options = new MarkerOptions();
		options.position(latLng);
		options.title("MyMarker");
		options.snippet("My Snippet");
		options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
		options.anchor(0.5f, 1.0f);
		options.draggable(true);
		options.visible(true);
		mMyLastMarker = mMap.addMarker(options);
		markerList.add(mMyLastMarker);
		
	}

	class MyInfoWindowAdapter implements InfoWindowAdapter {
		View infoWindow;
		TextView titleView;
		TextView snippetView;
		ImageView imageView;
		
		public MyInfoWindowAdapter(Context context) {
			LayoutInflater inflater = LayoutInflater.from(context);
			infoWindow = inflater.inflate(R.layout.info_window_layout, null);
			titleView = (TextView)infoWindow.findViewById(R.id.title);
			snippetView = (TextView)infoWindow.findViewById(R.id.snippet);
			imageView = (ImageView)infoWindow.findViewById(R.id.imageView1);
		}

		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			titleView.setText(marker.getTitle());
			snippetView.setText(marker.getSnippet());
			return infoWindow;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
//			titleView.setText(marker.getTitle());
//			snippetView.setText(marker.getSnippet());
//			return infoWindow;
			return null;
		}
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		if (marker.equals(mMyLastMarker)) {
			Toast.makeText(this, "My Last Marker Clicked", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}


	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "infoWindow clicked : " + marker.getTitle(), Toast.LENGTH_SHORT).show();
	}

}
