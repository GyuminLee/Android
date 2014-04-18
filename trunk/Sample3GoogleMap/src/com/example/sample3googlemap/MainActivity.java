package com.example.sample3googlemap;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			GoogleMap.OnCameraChangeListener, GoogleMap.OnMapClickListener,
			GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener,
			GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {

		public PlaceholderFragment() {
		}

		GoogleMap mMap;
		LocationManager mLM;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mLM = (LocationManager) getActivity().getSystemService(
					Context.LOCATION_SERVICE);
		}

		LocationListener mListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
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
				if (mMap != null) {
					// ....

					LatLng latLng = new LatLng(location.getLatitude(),
							location.getLongitude());
					CameraPosition.Builder builder = new CameraPosition.Builder();
					builder.target(latLng);
					builder.zoom(15.5f);
					builder.bearing(10.5f);
					builder.tilt(45);
					CameraPosition position = builder.build();
					// CameraUpdate update =
					// CameraUpdateFactory.newLatLngZoom(latLng, 15.5f);
					CameraUpdate update = CameraUpdateFactory
							.newCameraPosition(position);

					mMap.animateCamera(update);
				}
			}
		};

		@Override
		public void onStart() {
			super.onStart();
			String provider = LocationManager.NETWORK_PROVIDER;
			Location location = mLM.getLastKnownLocation(provider);
			if (location != null) {
				mListener.onLocationChanged(location);
			}
			mLM.requestLocationUpdates(provider, 0, 0, mListener);
		}

		@Override
		public void onStop() {
			super.onStop();
			mLM.removeUpdates(mListener);
		}

		@Override
		public void onResume() {
			super.onResume();
			setupMapIfNeeded();
		}

		private void setupMapIfNeeded() {
			if (mMap == null) {
				// mMap = ((SupportMapFragment)
				// ((ActionBarActivity)getActivity())
				// .getSupportFragmentManager().findFragmentById(
				// R.id.fragment1)).getMap();
				mMap = ((SupportMapFragment) getFragmentManager()
						.findFragmentById(R.id.fragment1)).getMap();
				if (mMap != null) {
					setupMap();
				}
			}
		}

		private void setupMap() {
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			mMap.setMyLocationEnabled(true);
			mMap.getUiSettings().setCompassEnabled(true);
			mMap.setOnCameraChangeListener(this);
			mMap.setOnMapClickListener(this);
			mMap.setOnMarkerClickListener(this);
			mMap.setOnInfoWindowClickListener(this);
			mMap.setInfoWindowAdapter(new MyInfoWindow(getActivity()));
			mMap.setOnMarkerDragListener(this);
		}

		class MyInfoWindow implements InfoWindowAdapter {

			View infoView;
			TextView titleView;
			TextView dataView;
			ImageView iconView;
			
			public MyInfoWindow(Context context) {
				View v = LayoutInflater.from(context).inflate(R.layout.info_window_layout, null);
				titleView = (TextView)v.findViewById(R.id.textView1);
				dataView = (TextView)v.findViewById(R.id.textView2);
				iconView = (ImageView)v.findViewById(R.id.imageView1);
				infoView = v;
			}
			
			@Override
			public View getInfoContents(Marker marker) {
				String text = mDataResolver.get(marker);
				titleView.setText(marker.getTitle());
				dataView.setText(text);
				return infoView;
			}

			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}
			
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			setupMapIfNeeded();
			Button btn = (Button) rootView.findViewById(R.id.btnZoomIn);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					CameraUpdate update = CameraUpdateFactory.zoomIn();
					mMap.animateCamera(update);
				}
			});

			btn = (Button) rootView.findViewById(R.id.btnZoomOut);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					CameraUpdate update = CameraUpdateFactory.zoomOut();
					mMap.animateCamera(update);
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnAddCircle);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					CameraPosition position = mMap.getCameraPosition();
					LatLng latLng = position.target;
					CircleOptions options = new CircleOptions();
					options.center(latLng);
					options.radius(100);
					options.fillColor(Color.TRANSPARENT);
					options.strokeColor(Color.RED);
					options.strokeWidth(5);
					Circle circle = mMap.addCircle(options);
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnSearch);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					NetworkModel.getInstance().getCarRouting(getActivity(),37.56468648536046 , 126.98217734415019, 35.17883196265564, 129.07579349764512, new NetworkModel.OnNetworkResult<CarRouteInfo>() {

						@Override
						public void onSuccess(CarRouteInfo result) {
							CarFeature firstFeature = result.features.get(0);
							int totaldistance = firstFeature.properties.totalDistance;
							int totaltime = firstFeature.properties.totalTime;
							PolylineOptions options = new PolylineOptions();
							
							for (CarFeature feature : result.features) {
								if (feature.geometry.type.equals("LineString")) {
									double[] coord = feature.geometry.coordinates;
									for (int i = 0 ; i < coord.length; i+=2) {
										options.add(new LatLng(coord[i+1],coord[i]));
									}
								}
							}
							
							options.color(Color.RED);
							options.width(10);
							options.geodesic(false);
							mMap.addPolyline(options);
						}

						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub
							
						}
						
					});
				}
			});
			return rootView;
		}

		@Override
		public void onCameraChange(CameraPosition position) {

		}

		@Override
		public void onMapLongClick(LatLng latLng) {

		}

		HashMap<String,Marker> mMarkerResolver = new HashMap<String,Marker>();
		HashMap<Marker,String> mDataResolver = new HashMap<Marker,String>();		
		
		int mId = 1;
		
		@Override
		public void onMapClick(LatLng latLng) {
			MarkerOptions options = new MarkerOptions();
			options.position(latLng);
			options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
			options.anchor(0.5f, 0.5f);
			options.title("my marker " + mId);
			options.snippet("description");
			options.draggable(false);
			Marker marker = mMap.addMarker(options);
			String text = "my marker " + mId;
			mMarkerResolver.put(text, marker);
			mDataResolver.put(marker, text);
			mId++;
		}

		@Override
		public boolean onMarkerClick(Marker marker) {
			String text = mDataResolver.get(marker);
			Toast.makeText(getActivity(), "data : " + text, Toast.LENGTH_SHORT).show();
			return false;
		}

		@Override
		public void onInfoWindowClick(Marker marker) {
			
			
		}

		@Override
		public void onMarkerDrag(Marker arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMarkerDragEnd(Marker arg0) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void onMarkerDragStart(Marker arg0) {
			// TODO Auto-generated method stub
			
		}
	}

}
