package com.example.sample4googlemap;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements 
	GoogleMap.OnMapClickListener,
	GoogleMap.OnMarkerClickListener {

	SupportMapFragment smf;
	GoogleMap mMap;
	LocationManager mLM;
	String mProvider = LocationManager.GPS_PROVIDER;
	EditText keywordView;
	ArrayList<MyData> items = new ArrayList<MyData>();
	HashMap<Marker,MyData> dataResolver = new HashMap<Marker,MyData>();
	HashMap<MyData,Marker> markerResolver = new HashMap<MyData,Marker>();
	ListView listView;
	ArrayAdapter<MyData> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.editText1);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<MyData>());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Marker marker = markerResolver.get((MyData)listView.getItemAtPosition(position));
				CameraUpdate update = CameraUpdateFactory.newLatLng(marker.getPosition());
				mMap.animateCamera(update,new CancelableCallback() {
					
					@Override
					public void onFinish() {
						marker.showInfoWindow();
					}
					
					@Override
					public void onCancel() {
						
					}
				});
				
			}
		});
		setupMapIfNeeded();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		initData();
	}
	
	private void initData() {
		
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
			if (mMap == null) return;
			LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude()); 
			CameraPosition position = new CameraPosition.Builder()
				.target(latlng)
				.zoom(15.5f)
				.bearing(0)
				.tilt(0)
				.build();
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
//			mMap.moveCamera(update);
			mMap.moveCamera(update);
			mLM.removeUpdates(this);
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 0, 0, mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setupMapIfNeeded();
	}
	
	private void setupMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment1)).getMap();
			if (mMap != null) {
				setupMap();
			}
		}
	}
	
	private void setupMap() {
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
//		mMap.getUiSettings().setZoomControlsEnabled(false);	
		mMap.setOnMapClickListener(this);
		mMap.setOnMarkerClickListener(this);
	}

	int mCount = 0;
	
	@Override
	public void onMapClick(LatLng latLng) {
//		Point pt = mMap.getProjection().toScreenLocation(latLng);
//		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)keywordView.getLayoutParams();
//		params.leftMargin = pt.x;
//		params.topMargin = pt.y;
//		keywordView.setVisibility(View.VISIBLE);
		MarkerOptions options = new MarkerOptions();
		options.position(latLng);
		options.anchor(0.5f, 1.0f);
		options.icon(BitmapDescriptorFactory.defaultMarker());
		MyData data = new MyData();
		data.title = "icon"+mCount;
		data.description = "content" + mCount;
		mCount++;
		options.title(data.title);
		options.snippet(data.description);
		Marker marker = mMap.addMarker(options);
		markerResolver.put(data, marker);
		dataResolver.put(marker, data);
		mAdapter.add(data);
	}

	Handler mHandler = new Handler();
	long startTime;
	@Override
	public boolean onMarkerClick(final Marker marker) {
		MyData data = dataResolver.get(marker);
		Toast.makeText(this, "title : " + data.title, Toast.LENGTH_SHORT).show();
		startTime = System.currentTimeMillis();
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				float t = ((float)(System.currentTimeMillis() - startTime)) / 1000.0f;
				if (t < 1) {
					float f = new BounceInterpolator().getInterpolation(t);
					
					marker.setAnchor(0.5f, 1.0f + f);
					mHandler.postDelayed(this, 50);
				} else {
					marker.setAnchor(0.5f, 1.0f);
					marker.showInfoWindow();
				}
			}
		});
		return true;
	}
}
