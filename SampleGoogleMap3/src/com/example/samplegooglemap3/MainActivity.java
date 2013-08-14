package com.example.samplegooglemap3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samplegooglemap3.network.Feature;
import com.example.samplegooglemap3.network.JSONRoadRequest;
import com.example.samplegooglemap3.network.RoadSearchResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;

public class MainActivity extends FragmentActivity implements 
	GoogleMap.OnMapClickListener,
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener,
	GoogleMap.OnCameraChangeListener {

    private static final String MOON_MAP_URL_FORMAT =
            "http://mw1.google.com/mw-planetary/lunar/lunarmaps_v1/clem_bw/%d/%d/%d.jpg";

    private static final LatLng NEWARK = new LatLng(40.714086, -74.228697);
	
	GoogleMap mMap;
	LocationManager mLM;
	HashMap<String, MyData> mValueResolve = new HashMap<String, MyData>();
	HashMap<MyData, Marker> mMarkerResolve = new HashMap<MyData, Marker>();
	ArrayList<Marker> mMarkerList = new ArrayList<Marker>();

	int mIndex = 0;
	
	LatLng selectedPoint;
	LatLng startPoint;
	LatLng endPoint;
	Handler mHandler = new Handler();
	
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
			
			mLocationSource.setMyLocation(location);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		
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
				LatLng latLng = pos.target;
				
				// addMarker...
				boolean bAdd = true;
				for (Marker marker : mMarkerList) {
					float[] results = new float[2];
					Location.distanceBetween(latLng.latitude, latLng.longitude, marker.getPosition().latitude, marker.getPosition().longitude, results);
					if (results[0] < 50) {
						bAdd = false;
						break;
					}
				}
				if (bAdd) {
					// add Marker
				}
			}
		});
		
		btn = (Button)findViewById(R.id.showInfo);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LatLng farLeft = mMap.getProjection().getVisibleRegion().farLeft;
				LatLng farRight = mMap.getProjection().getVisibleRegion().farRight;
				LatLng nearLeft = mMap.getProjection().getVisibleRegion().nearLeft;
				LatLng nearRight = mMap.getProjection().getVisibleRegion().nearRight;
				LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
			}
		});
		
		btn = (Button)findViewById(R.id.groundOVerlay);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GroundOverlayOptions options = new GroundOverlayOptions();
				options.image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922));
				options.position(NEWARK, 8600f, 6500f);
				options.anchor(0.0f, 1.0f);
				
				GroundOverlay overlay = mMap.addGroundOverlay(options);
				overlay.setTransparency(0.5f);
				CameraUpdate update = CameraUpdateFactory.newLatLng(NEWARK);
				mMap.animateCamera(update);
			}
		});
		
		btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (selectedPoint != null) {
					startPoint = selectedPoint;
					selectedPoint = null;
				} else {
					Toast.makeText(MainActivity.this, "not selected", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		btn = (Button)findViewById(R.id.end);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (selectedPoint != null) {
					endPoint = selectedPoint;
					selectedPoint = null;
				} else {
					Toast.makeText(MainActivity.this, "not selected", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if (startPoint != null && endPoint != null) {
					JSONRoadRequest request = new JSONRoadRequest(startPoint.latitude, startPoint.longitude, endPoint.latitude, endPoint.longitude);
					request.start(mHandler, new JSONRoadRequest.OnDownloadCompleteListener() {
						
						@Override
						public void onCompleted(JSONRoadRequest request) {
							RoadSearchResult result = request.getResult();
							// result.... 
							PolylineOptions options = new PolylineOptions();
							
							for (Feature f : result.features) {
								if (f.geometry.type.equals("LineString")) {
									double[] coord = f.geometry.coordinates;
									for (int i = 0; i < coord.length; i+=2) {
										LatLng point = new LatLng(coord[i+1],coord[i]);
										options.add(point);
									}
								}
								
								if (f.properties.index == 0) {
									int totalTime = f.properties.totalTime;
									int totalDistance = f.properties.totalDistance;
									Toast.makeText(MainActivity.this, "total Time : " + totalTime, Toast.LENGTH_SHORT).show();
								}
							}
							
							options.color(Color.RED);
							options.width(10);
							mMap.addPolyline(options);
							
							
						}
					});
//				} else {
//					Toast.makeText(MainActivity.this, "not selected", Toast.LENGTH_SHORT).show();
//				}
				
			}
		});
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
		mLocationSource.onResumed();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		mLocationSource.onPaused();
		super.onPause();
	}
	
	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			setUpMap();
		}
	}

	private void setUpMap() {
//		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//		mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
//		mMap.setTrafficEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.getUiSettings().setCompassEnabled(true);
		
		mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this));
		
		mMap.setOnMapClickListener(this);
		mMap.setOnMarkerClickListener(this);
		
		mMap.setLocationSource(mLocationSource);
		mMap.setOnMapLongClickListener(mLocationSource);
		
//		UrlTileProvider provider = new UrlTileProvider(256,256) {
//			
//			@Override
//			public URL getTileUrl(int x, int y, int zoom) {
//				
//				int reservedY = (1 << zoom) - y - 1;
//				String url = String.format(MOON_MAP_URL_FORMAT, zoom,x, reservedY);
//				try {
//					return new URL(url);
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return null;
//			}
//		};
		
//		TileProvider p = new TileProvider() {
//			
//			@Override
//			public Tile getTile(int x, int y, int zoom) {
//				byte[] data = null;
//				// zoom, z, y file / resource
//				if (zoom > 3) {
//					return null;
//				}
//				Tile tile = new Tile(256, 256, data);
//				return tile;
//			}
//		};
		
//		TileOverlayOptions options = new TileOverlayOptions();
//		options.tileProvider(provider);
//		mMap.addTileOverlay(options);
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
		
		Point point = new Point();
		point = mMap.getProjection().toScreenLocation(latLng);
		LatLng ll = mMap.getProjection().fromScreenLocation(point);
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		MyData value = mValueResolve.get(marker.getId());
		Toast.makeText(this, "marker clicked : " + marker.getTitle() + ", value : " + value.mIndex, Toast.LENGTH_SHORT).show();
//		marker.showInfoWindow();
		selectedPoint = marker.getPosition();
		return true;
	}
	
	MyLocationSource mLocationSource = new MyLocationSource();
	
	public class MyLocationSource implements LocationSource, GoogleMap.OnMapLongClickListener {
		
		OnLocationChangedListener mChangedListener;
		boolean bResumed = true;

		@Override
		public void activate(OnLocationChangedListener listener) {
			mChangedListener = listener;
			
		}

		@Override
		public void deactivate() {
			mChangedListener = null;
		}
		
		public void onResumed() {
			bResumed = true;
		}
		
		public void onPaused() {
			bResumed = false;
		}

		@Override
		public void onMapLongClick(LatLng latLng) {
			
			if (mChangedListener != null && bResumed) {
			
				Location location = new Location("MyLocation");
				location.setLatitude(latLng.latitude);
				location.setLongitude(latLng.longitude);
				location.setAccuracy(50);
				mChangedListener.onLocationChanged(location);
			}
		}

		public void setMyLocation(Location location) {
			if (mChangedListener != null && bResumed) {
				mChangedListener.onLocationChanged(location);
			}
		}
	}
	
	public class MyInfoWindowAdapter implements InfoWindowAdapter {

		Context mContext;
		View mInfoContentView;
		ImageView imageView;
		TextView mTitleView;
		TextView mSnippentView;
		TextView mIndexView;
		
		public MyInfoWindowAdapter(Context context) {
			LayoutInflater inflater = LayoutInflater.from(context);
			mInfoContentView = inflater.inflate(R.layout.info_layout, null);
			imageView = (ImageView)mInfoContentView.findViewById(R.id.icon);
			mTitleView = (TextView)mInfoContentView.findViewById(R.id.title);
			mSnippentView = (TextView)mInfoContentView.findViewById(R.id.snippet);
			mIndexView = (TextView)mInfoContentView.findViewById(R.id.index);
			imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Marker m = (Marker)v.getTag();
					Toast.makeText(MainActivity.this, "Tost...", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		@Override
		public View getInfoContents(Marker marker) {
			return null;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			MyData value = mValueResolve.get(marker.getId());
			mTitleView.setText(marker.getTitle());
			mSnippentView.setText(marker.getSnippet());
			mIndexView.setText("index : " + value.mIndex);
			imageView.setTag(marker);
			return mInfoContentView;
		}
		
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// ...
	}

	@Override
	public void onCameraChange(CameraPosition position) {
		
	}
	
	

}
