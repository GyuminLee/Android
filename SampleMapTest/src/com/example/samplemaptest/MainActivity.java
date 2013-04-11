package com.example.samplemaptest;

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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

	GoogleMap mMap;
	LocationManager mLocationManager;
	HashMap<Marker,String> mPoiList = new HashMap<Marker,String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, mListener);
		super.onStart();
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
			// TODO Auto-generated method stub
			CameraPosition position = CameraPosition.builder()
					.target(new LatLng(location.getLatitude(), location.getLongitude()))
					.bearing(0)
					.zoom(15.5f)
					.tilt(20).build();
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
			mMap.animateCamera(update);
		}
	};
	
	
	protected void onStop() {
		mLocationManager.removeUpdates(mListener);
		super.onStop();
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		setUpMapIfNeeded();
		super.onResume();
	}
	
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    
    
    public class MyInfoWindow implements InfoWindowAdapter {

    	Context mContext;
    	View infoView;
    	TextView titleView;
    	TextView snippetView;
    	
    	public MyInfoWindow(Context context) {
    	
    		LayoutInflater inflater = LayoutInflater.from(context);
    		infoView = inflater.inflate(R.layout.info_layout, null);
    		titleView = (TextView)infoView.findViewById(R.id.textView1);
    		snippetView = (TextView)infoView.findViewById(R.id.textView2);
    		
    	}
    	
		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			titleView.setText(marker.getTitle());
			snippetView.setText(marker.getSnippet());
			return infoView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
//			titleView.setText(marker.getTitle());
//			snippetView.setText(marker.getSnippet());
//			return infoView;
			return null;
		}
    	
    }
    
    class MyLocationSource implements LocationSource, OnMapLongClickListener {

    	OnLocationChangedListener mListener;
    	
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

		@Override
		public void onMapLongClick(LatLng pos) {
			// TODO Auto-generated method stub
			if (mListener != null) {
				Location location = new Location("MyLocation");
				location.setLatitude(pos.latitude);
				location.setLongitude(pos.longitude);
				location.setAccuracy(100.0f);
				mListener.onLocationChanged(location);
			}
		}
    	
    }
    
    
    private void setUpMap() {
    	
    	mMap.setInfoWindowAdapter(new MyInfoWindow(this));
    	
    	mMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng pos) {
				// TODO Auto-generated method stub
				MarkerOptions options = new MarkerOptions();
				options.position(pos);
				options.title("myMarker");
				options.snippet("description");
				options.draggable(true);
				options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
				
				Marker marker = mMap.addMarker(options);
				String value = "time :" + System.currentTimeMillis();
				mPoiList.put(marker, value);
			}
		});
    	
    	mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				String value = mPoiList.get(marker);
				
				return false;
			}
		});
    	
    	mMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMarkerDragEnd(Marker arg0) {
				// TODO Auto-generated method stub
				String value = mPoiList.get(arg0);
			}
			
			@Override
			public void onMarkerDrag(Marker arg0) {
				// TODO Auto-generated method stub
				LatLng pos = arg0.getPosition();
			}
		});
    	
    	mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    	mMap.setMyLocationEnabled(true);
    	mMap.getUiSettings().setCompassEnabled(true);
    	mMap.getUiSettings().setZoomControlsEnabled(false);
    	MyLocationSource locationSource = new MyLocationSource();
    	mMap.setLocationSource(locationSource);
    	mMap.setOnMapLongClickListener(locationSource);
    	mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
			
			@Override
			public void onMyLocationChange(Location arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
	
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
