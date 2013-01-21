package com.example.testandroidmap2sample;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements 
	OnMapClickListener, 
	OnMapLongClickListener,
	OnCameraChangeListener, 
	OnMarkerClickListener,
	OnMarkerDragListener,
	OnInfoWindowClickListener {

    private GoogleMap mMap;
    MyLocationSource locationSource;
    private boolean isLocationUpdate = false;
    Marker mCurrentMarker;

    LocationManager mLocationManager;
    LocationListener mListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			if (isMapAvailable() && !isLocationUpdate) {
				isLocationUpdate = true;
				LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
				CameraPosition pos = CameraPosition.builder().target(position)
						.bearing(30)
						.zoom(15.5f)
						.tilt(50)
						.build();
				CameraUpdate update = CameraUpdateFactory.newCameraPosition(pos);
				mMap.moveCamera(update);
				mMap.animateCamera(update);
				mMap.animateCamera(update, new CancelableCallback() {
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
				
				MarkerOptions options = new MarkerOptions().position(position)
						.title("current")
						.snippet("description")
						.draggable(true)
						.icon(BitmapDescriptorFactory.defaultMarker());
				mCurrentMarker = mMap.addMarker(options);
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}

    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        setUpMapIfNeeded();
        
        Button btn = (Button)findViewById(R.id.my);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isMapAvailable()) {
					boolean isEnabled = !mMap.isMyLocationEnabled();
					mMap.setMyLocationEnabled(isEnabled);
				}
			}
		});
        
        btn = (Button)findViewById(R.id.traffic);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isMapAvailable()) {
					boolean isEnabled = !mMap.isTrafficEnabled();
					mMap.setTrafficEnabled(isEnabled);
				}
			}
		});
        
        btn = (Button)findViewById(R.id.indoor);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isMapAvailable()) {
					boolean isEnabled = !mMap.isIndoorEnabled();
					mMap.setIndoorEnabled(isEnabled);
				}
			}
		});
        
        btn = (Button)findViewById(R.id.setting);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uiSetting();
			}
		});
        
        btn = (Button)findViewById(R.id.normal);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isMapAvailable()) {
					mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				}
			}
		});
        
        btn = (Button)findViewById(R.id.satellite);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isMapAvailable()) {
					mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				}
			}
		});
        
        btn = (Button)findViewById(R.id.terrain);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isMapAvailable()) {
					mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				}
			}
		});
	}
	
	private boolean isMapAvailable() {
		return (mMap != null);
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
		super.onStart();
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, mListener);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationManager.removeUpdates(mListener);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        setUpMapIfNeeded();
        locationSource.onResume();
	}
	
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    public class MyInfoWindowAdapter implements InfoWindowAdapter {

    	View mWindow;
    	View mContents;
    	TextView mWindowTitle;
    	TextView mWIndowSnippet;
    	TextView mContentsTitle;
    	TextView mContentsSnippet;
    	
    	public MyInfoWindowAdapter(Context context) {
    		LayoutInflater inflater = LayoutInflater.from(context);
    		mWindow = inflater.inflate(R.layout.red_info, null);
    		mWindowTitle = (TextView)mWindow.findViewById(R.id.textView1);
    		mWIndowSnippet = (TextView)mWindow.findViewById(R.id.textView2);
    		mContents = inflater.inflate(R.layout.blue_info, null);
    		mContentsTitle = (TextView)mContents.findViewById(R.id.textView1);
    		mContentsSnippet = (TextView)mContents.findViewById(R.id.textView2);
    	}
    	
		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			mContentsTitle.setText(marker.getTitle());
			mContentsSnippet.setText(marker.getSnippet());
			return mContents;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			mWindowTitle.setText(marker.getTitle());
			mWIndowSnippet.setText(marker.getSnippet());
			return mWindow;
//			return null;
		}
    }
    MyInfoWindowAdapter mInfoWindowAdapter;
    
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mInfoWindowAdapter = new MyInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(mInfoWindowAdapter);
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraChangeListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        locationSource = new MyLocationSource();
        mMap.setLocationSource(locationSource);
        mMap.setOnMapLongClickListener(locationSource);
        mMap.setOnInfoWindowClickListener(this);
    }
    
    boolean isSetting = false;
    
    public void uiSetting() {
    	UiSettings settings = mMap.getUiSettings();
    	boolean isSetting = !settings.isCompassEnabled();
    	
    	settings.setCompassEnabled(isSetting);
    	settings.setMyLocationButtonEnabled(isSetting);
    	settings.setZoomControlsEnabled(isSetting);
    	
//    	settings.setAllGesturesEnabled(isSetting);
//    	settings.setRotateGesturesEnabled(isSetting);
//    	settings.setScrollGesturesEnabled(isSetting);
//    	settings.setZoomGesturesEnabled(isSetting);
    }

    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	locationSource.onPause();
    	super.onPause();
    }
    
	@Override
	public boolean onMarkerClick(final Marker marker) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Marker Clicked " + marker.getTitle(), Toast.LENGTH_SHORT).show();
//		marker.showInfoWindow();
		return false;
	}

	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Camera Change", Toast.LENGTH_SHORT).show();
	}

	MapAddFragment addDialog;
	
	public class MyLocationSource implements LocationSource , GoogleMap.OnMapLongClickListener {

		LocationSource.OnLocationChangedListener mListener;
		boolean mPaused;
		
		@Override
		public void onMapLongClick(LatLng point) {
			// TODO Auto-generated method stub
			if (mListener != null && !mPaused) {
				Location location = new Location("MyLongClick");
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
			mPaused = true;
		}
		
		public void onResume() {
			mPaused = false;
		}
		
	}
	
	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		if (addDialog == null) {
			addDialog = new MapAddFragment();
		}
		addDialog.setPoint(point);
		
		addDialog.show(getSupportFragmentManager(), "dialog");
	}

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Map Click", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Info Window Click", Toast.LENGTH_SHORT).show();
	}
    
	
	@Override
	public void onMarkerDrag(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragEnd(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		// TODO Auto-generated method stub
		
	}
	
	public void addMarker(LatLng point, String title, String snippet) {
		MarkerOptions options = new MarkerOptions().position(point)
				.title(title)
				.snippet(snippet)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
				.draggable(true);
		Marker marker = mMap.addMarker(options);
	}
	
	public class MapAddFragment extends DialogFragment {
		LatLng mPoint;
		EditText titleView;
		EditText snippetView;
		
		public void setPoint(LatLng point) {
			mPoint = point;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.poi_add_dialog, container, false);
			titleView = (EditText)v.findViewById(R.id.editText1);
			snippetView = (EditText)v.findViewById(R.id.editText2);
			Button btn = (Button)v.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String title = titleView.getText().toString();
					String snippet = snippetView.getText().toString();
					addMarker(mPoint, title, snippet);
					dismiss();
				}
			});
			btn = (Button)v.findViewById(R.id.button2);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
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
			dialog.setTitle("Add POI");
		}
		
	}

}
