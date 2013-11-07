package com.example.hellogooglemaptest;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements 
	GoogleMap.OnMapClickListener,
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener {

	GoogleMap mMap;
	LocationManager mLM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
		
		btn = (Button)findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraPosition position = mMap.getCameraPosition();
				LatLng latLng = position.target;
				CircleOptions options = new CircleOptions();
				options.center(latLng);
				options.fillColor(0x80FF0000);
				options.radius(100);
				options.strokeWidth(5);
				options.strokeColor(Color.BLACK);
				Circle circle = mMap.addCircle(options);
				
				PolylineOptions ploptions = new PolylineOptions();
				ploptions.add(latLng);
				ploptions.geodesic(true);
				
				PolygonOptions pgoptions = new PolygonOptions();
				pgoptions.add(latLng);
//				pgoptions.addHole(points)
				
			}
		});
	}
	
	class MyInfoWindow implements InfoWindowAdapter {
		View infoWindow;
		ImageView iconView;
		TextView titleView;
		TextView snippetView;
		
		public MyInfoWindow(Context context) {
			infoWindow = LayoutInflater.from(context).inflate(R.layout.info_window, null);
			iconView = (ImageView)infoWindow.findViewById(R.id.imageView1);
			titleView = (TextView)infoWindow.findViewById(R.id.textView1);
			snippetView = (TextView)infoWindow.findViewById(R.id.textView2);
		}

		@Override
		public View getInfoContents(Marker marker) {
			String value = mResolver.get(marker.getId());
			titleView.setText(marker.getTitle());
			snippetView.setText(value);
			return infoWindow;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}
		
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
			moveLocation(location);
		}
	};

	private void moveLocation(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		CameraPosition position = new CameraPosition.Builder().target(latLng)
				.zoom(15.5f).tilt(30).bearing(45).build();

		CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
		mMap.animateCamera(update);
	}

	@Override
	protected void onStart() {
		Location location = mLM
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (location != null) {
			mListener.onLocationChanged(location);
		}

		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
				mListener);
		super.onStart();
	}

	@Override
	protected void onStop() {
		mLM.removeUpdates(mListener);
		super.onStop();
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.mapFragment)).getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	@Override
	protected void onResume() {
		setUpMapIfNeeded();
		super.onResume();
	}

	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.setOnMapClickListener(this);
		mMap.setOnMapClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
		MyInfoWindow info = new MyInfoWindow(this);
		mMap.setInfoWindowAdapter(info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	HashMap<String,String> mResolver = new HashMap<String,String>();
	
	@Override
	public void onMapClick(LatLng latLng) {
		MarkerOptions option = new MarkerOptions();
		option.position(latLng).anchor(0.5f, 1).title("MyMarker").snippet("sub title").draggable(true);
		option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		Marker m = mMap.addMarker(option);
		String value = "value " + m.getId();
		mResolver.put(m.getId(), value);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		String value = mResolver.get(marker.getId());
		Toast.makeText(this, "marker value : " + value, Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// ...
	}

}
