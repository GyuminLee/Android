package com.example.testgooglemapsample;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements 
	GoogleMap.OnCameraChangeListener , 
	GoogleMap.OnMapClickListener, 
	GoogleMap.OnMapLongClickListener, 
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    LocationManager mLocationManager;
	
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
			LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
			CameraPosition position = CameraPosition.builder().target(pos).bearing(30).zoom(15.5f).tilt(50).build();
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
			mMap.animateCamera(update);
			MarkerOptions options = new MarkerOptions().position(pos)
					.title("My Position")
					.snippet("Description")
					//.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
					.draggable(true);
			Marker marker = mMap.addMarker(options);
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, mListener);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationManager.removeUpdates(mListener);
		
		super.onStop();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpMapIfNeeded();
		mLocationSource.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mLocationSource.onPause();
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
    
    public class MyInfoWindowAdapter implements InfoWindowAdapter {

    	View infoContent;
    	TextView infoContentTitle;
    	TextView infoContentSnippet;
    	
    	public MyInfoWindowAdapter(Context context) {
    		LayoutInflater inflater = LayoutInflater.from(context);
    		infoContent = inflater.inflate(R.layout.info_content, null);
    		infoContentTitle = (TextView)infoContent.findViewById(R.id.textView1);
    		infoContentSnippet = (TextView)infoContent.findViewById(R.id.textView2);
    	}
    	
		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			infoContentTitle.setText(marker.getTitle());
			infoContentSnippet.setText(marker.getSnippet());
			return infoContent;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }

    MyInfoWindowAdapter mInfoWindowAdapter;
    
    public class MyLocationSource implements LocationSource ,  GoogleMap.OnMapLongClickListener {

    	LocationSource.OnLocationChangedListener mListener;
    	boolean isPaused = false;
    	
		@Override
		public void onMapLongClick(LatLng point) {
			// TODO Auto-generated method stub
			if (mListener != null & !isPaused) {
				Location location = new Location("MyLocation");
				location.setLatitude(point.latitude);
				location.setLongitude(point.longitude);
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
			isPaused = true;
		}
		
		public void onResume() {
			isPaused = false;
		}
    	
    }
    
    MyLocationSource mLocationSource = new MyLocationSource();
    
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setOnCameraChangeListener(this);
        mMap.setOnMapClickListener(this);
//        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        mMap.setMyLocationEnabled(true);
//        mMap.setTrafficEnabled(true);
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setLocationSource(mLocationSource);
        mMap.setOnMapLongClickListener(mLocationSource);
        
        mInfoWindowAdapter = new MyInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(mInfoWindowAdapter);
    }

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		
		MarkerAddFragment f = new MarkerAddFragment();
		f.setPoint(point);
		f.show(getSupportFragmentManager(), "dialog");
		
	}

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		
	}
    
	public void addMarker(LatLng point, String title, String snippet) {
		MarkerOptions options = new MarkerOptions();
		options.position(point).title(title).snippet(snippet);
		mMap.addMarker(options);
	}
	
	public class MarkerAddFragment extends DialogFragment {
		LatLng mPoint;
		
		EditText titleView;
		EditText descView;
		
		public void setPoint(LatLng point) {
			mPoint = point;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View v = inflater.inflate(R.layout.marker_add, container, false);
			titleView = (EditText)v.findViewById(R.id.editText1);
			descView = (EditText)v.findViewById(R.id.editText2);
			Button btn = (Button)v.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String title = titleView.getText().toString();
					String snippet = descView.getText().toString();
					addMarker(mPoint, title, snippet);
					dismiss();
				}
			});
			return v;
		}
		
		@Override
		public void onActivityCreated(Bundle arg0) {
			// TODO Auto-generated method stub
			super.onActivityCreated(arg0);
			
			Dialog dialog = getDialog();
			dialog.setTitle("Add Marker");
		}
	}

}
