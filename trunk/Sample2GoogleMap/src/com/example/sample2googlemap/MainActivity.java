package com.example.sample2googlemap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skplanetx.openapi.tmap.CarFeature;
import org.skplanetx.openapi.tmap.CarRouteInfo;
import org.skplanetx.openapi.tmap.Geometry;
import org.skplanetx.openapi.tmap.GeometryDeserializer;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skp.openplatform.android.sdk.api.APIRequest;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.CONTENT_TYPE;
import com.skp.openplatform.android.sdk.common.PlanetXSDKConstants.HttpMethod;
import com.skp.openplatform.android.sdk.common.PlanetXSDKException;
import com.skp.openplatform.android.sdk.common.RequestBundle;
import com.skp.openplatform.android.sdk.common.ResponseMessage;

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
		APIRequest.setAppKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
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
		
		btn = (Button)findViewById(R.id.btnRoute);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MyRouteTask().execute("");
			}
		});
	}
	CarRouteInfo mResult;
	
	class MyRouteTask extends AsyncTask<String, Integer, PolylineOptions> {
		@Override
		protected PolylineOptions doInBackground(String... params) {
			double startLat = 37.56468648536046;
			double startLng = 126.98217734415019;
			double endLat = 35.17883196265564;
			double endLng = 129.07579349764512;
			APIRequest request = new APIRequest();
			RequestBundle bundle = new RequestBundle();
			bundle.setUrl("https://apis.skplanetx.com/tmap/routes");
			bundle.setHttpMethod(HttpMethod.POST);
			bundle.setRequestType(CONTENT_TYPE.FORM);
			bundle.setResponseType(CONTENT_TYPE.JSON);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("version", 1);
			param.put("endX", endLng);
			param.put("endY", endLat);
			param.put("startX", startLng);
			param.put("startY", startLat);
			param.put("resCoordType", "WGS84GEO");
			param.put("reqCoordType", "WGS84GEO");
			bundle.setParameters(param);
			try {
				ResponseMessage message = request.request(bundle);
				Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeserializer()).create();
				CarRouteInfo info = gson.fromJson(message.getResultMessage(), CarRouteInfo.class);
				mResult = info;
				PolylineOptions options = new PolylineOptions();
				for (CarFeature feature : info.features) {
					if (feature.geometry.type.equals("LineString")) {
						double[] coords = feature.geometry.coordinates;
						for (int i = 0 ; i < coords.length; i+=2) {
							LatLng point = new LatLng(coords[i+1],coords[i]);
							options.add(point);
						}
					}
				}
				options.color(Color.RED);
				options.width(10);
				return options;
			} catch (PlanetXSDKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(PolylineOptions result) {
			if (result != null) {
				Polyline line = mMap.addPolyline(result);
				List<LatLng> points = line.getPoints();
				if (points.size() > 0) {
					LatLng firstpoint = points.get(0);
					CameraUpdate update = CameraUpdateFactory.newLatLng(firstpoint);
					mMap.animateCamera(update);
				}
				
				int totalD = mResult.features.get(0).properties.totalDistance;
				int totalT = mResult.features.get(0).properties.totalTime;
				int totalF = mResult.features.get(0).properties.totalFare;
				Toast.makeText(MainActivity.this, " distance : " + totalD + ", Time : " + totalT + ", Fare : " + totalF, Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
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
			MyData data = mValueResolver.get(marker.getId());
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
