package com.example.sample4googlemap;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sample4googlemap.model.CarFeature;
import com.example.sample4googlemap.model.CarRouteInfo;
import com.example.sample4googlemap.model.NetworkModel;
import com.example.sample4googlemap.model.NetworkModel.OnResultListener;
import com.example.sample4googlemap.model.POI;
import com.example.sample4googlemap.model.SearchPOIInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends ActionBarActivity implements 
	GoogleMap.OnMapClickListener,
	GoogleMap.OnMapLongClickListener, 
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener {

	SupportMapFragment smf;
	GoogleMap mMap;
	LocationManager mLM;
	String mProvider = LocationManager.GPS_PROVIDER;
	EditText keywordView;
	ArrayList<MyData> items = new ArrayList<MyData>();
	HashMap<Marker,MyData> dataResolver = new HashMap<Marker,MyData>();
	HashMap<MyData,Marker> markerResolver = new HashMap<MyData,Marker>();
	ListView listView;
	ArrayAdapter<POI> mAdapter;
	HashMap<Marker,POI> poiResolver = new HashMap<Marker,POI>();
	HashMap<POI,Marker> markerPoiResolver = new HashMap<POI,Marker>();
	InfoBitmap infoBitmap;
	EditText searchView;
	RadioGroup group;
	double startLat, startLng, endLat, endLng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		group = (RadioGroup)findViewById(R.id.radioGroup1);
		infoBitmap = new InfoBitmap(this);
		keywordView = (EditText)findViewById(R.id.editText1);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<POI>());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Marker marker = markerPoiResolver.get((POI)listView.getItemAtPosition(position));
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
//		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Marker marker = markerResolver.get((MyData)listView.getItemAtPosition(position));
//				marker.remove();
//				return true;
//			}
//		});
		searchView = (EditText)findViewById(R.id.editText2);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = searchView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkModel.getInstnace().getPOI(MainActivity.this, keyword, new NetworkModel.OnResultListener<SearchPOIInfo>() {

						@Override
						public void onSuccess(SearchPOIInfo data) {
							for (POI poi : data.pois.poi) {
								MarkerOptions options = new MarkerOptions();
								options.position(new LatLng(poi.getCenterLatitude(), poi.getCenterLongitude()));
								options.anchor(0.5f, 1);
								options.icon(BitmapDescriptorFactory.defaultMarker());
								options.title(poi.name);
								options.snippet(poi.lowerAddrName);
								Marker marker = mMap.addMarker(options);
								poiResolver.put(marker, poi);
								markerPoiResolver.put(poi, marker);
								mAdapter.add(poi);
							}
						}

						@Override
						public void onFail(int code) {
							
							
						}
					});
				}
				
			}
		});
		
		btn = (Button)findViewById(R.id.btn_route);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (startLat != 0 && endLat != 0) {
					NetworkModel.getInstnace().getRouting(MainActivity.this, startLat, startLng, endLat, endLng, new OnResultListener<CarRouteInfo>() {
						
						@Override
						public void onSuccess(CarRouteInfo data) {
							if (data != null && data.features != null && data.features.size() > 0) {
								CarFeature firstFeature = data.features.get(0);
								int totalDistance = firstFeature.properties.totalDistance;
								int totalTime = firstFeature.properties.totalTime;
								int totalFare = firstFeature.properties.totalFare;
								PolylineOptions options = new PolylineOptions();
								for (CarFeature f : data.features) {
									if (f.geometry.type.equals("LineString")) {
										for (int i = 0; i < f.geometry.coordinates.length; i+=2) {
											double lng = f.geometry.coordinates[i];
											double lat = f.geometry.coordinates[i+1];
											LatLng latLng = new LatLng(lat,lng);
											options.add(latLng);
										}
									}
								}
								
								options.color(Color.RED);
								options.width(10);
								Polyline line = mMap.addPolyline(options);
								CameraUpdate update = CameraUpdateFactory.newLatLng(new LatLng(startLat, startLng));
								mMap.animateCamera(update);
								startLat = 0;
								startLng = 0;
								endLat = 0;
								endLng = 0;
							}
						}
						
						@Override
						public void onFail(int code) {
							
						}
					});
				} else {
					Toast.makeText(MainActivity.this, "set start or end", Toast.LENGTH_SHORT).show();
				}
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
		mMap.setOnMapLongClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
//		mMap.setInfoWindowAdapter(new MyInfoWindow(this, dataResolver));
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
//		options.icon(BitmapDescriptorFactory.defaultMarker());
		MyData data = new MyData();
		data.title = "icon"+mCount;
		data.description = "content" + mCount;
		data.resId = R.drawable.ic_launcher;
		Bitmap bm = infoBitmap.getInfoBitmap(data);
		options.icon(BitmapDescriptorFactory.fromBitmap(bm));
		mCount++;
		options.title(data.title);
		options.snippet(data.description);
		Marker marker = mMap.addMarker(options);
		markerResolver.put(data, marker);
		dataResolver.put(marker, data);
//		mAdapter.add(data);
	}

	Handler mHandler = new Handler();
	long startTime;
	@Override
	public boolean onMarkerClick(final Marker marker) {
//		MyData data = dataResolver.get(marker);
//		Toast.makeText(this, "title : " + data.title, Toast.LENGTH_SHORT).show();
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

	Circle circle = null;
	@Override
	public void onMapLongClick(LatLng latLng) {
		CircleOptions options = new CircleOptions();
		options.center(latLng);
		options.radius(100);
		options.strokeColor(Color.RED);
		options.strokeWidth(5);
		options.fillColor(0x80808080);
		circle = mMap.addCircle(options);
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				circle.remove();
			}
		}, 2000);
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
//		MyData data = dataResolver.get(marker);
//		Toast.makeText(this, "title : " + data.title, Toast.LENGTH_SHORT).show();
		switch(group.getCheckedRadioButtonId()) {
		case R.id.radio_start :
			startLat = marker.getPosition().latitude;
			startLng = marker.getPosition().longitude;
			break;
		case R.id.radio_end :
			endLat = marker.getPosition().latitude;
			endLng = marker.getPosition().longitude;
			break;
		}
		marker.hideInfoWindow();
	}
}
