package com.example.sample3tmap;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.TMapPathType;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

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
	public static class PlaceholderFragment extends Fragment {

		TMapView mMapView;
		LocationManager mLM;
		
		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mLM = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
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
				if (isInitialzed) {
					moveMap(location);
					setMyLocation(location);
				} else {
					cacheLocation = location;
				}
			}
		};
		
		Location cacheLocation = null;
		private void moveMap(Location location) {
			mMapView.setCenterPoint(location.getLongitude(), location.getLatitude(), true);
		}
		
		private void setMyLocation(Location location) {
			mMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
		}
		
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
			mLM.removeUpdates(mListener);
			super.onStop();
		}
		
		boolean isInitialzed = false;
		EditText keywordView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			mMapView = (TMapView)rootView.findViewById(R.id.tmap);
			mMapView.setPadding(0, 0, 0, 0);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					mMapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
					mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
					return null;
				}
				
				@Override
				protected void onPostExecute(Void result) {
					setupMap();
					super.onPostExecute(result);
				}
			}.execute();
			
			Button btn = (Button)rootView.findViewById(R.id.btnAddMarker);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TMapPoint point = mMapView.getCenterPoint();
					TMapMarkerItem item = new TMapMarkerItem();
					item.setTMapPoint(point);
					Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_contact_picture)).getBitmap();
					item.setIcon(bitmap);
					item.setPosition(0.5f, 0.5f);
					item.setName("name");
					item.setCalloutTitle("my marker");
					item.setCalloutSubTitle("subtitle");
					Bitmap left = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher_settings)).getBitmap();
					item.setCalloutLeftImage(left);
					Bitmap right = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_popup_reminder)).getBitmap();
					item.setCalloutRightButtonImage(right);
					item.setCanShowCallout(true);
//					mMapView.addMarkerItem("markerid", item);
					
					MyTMapMarker marker = new MyTMapMarker();
					marker.setIcon(bitmap);
					marker.setPosition(0.5f, 0.5f);
					marker.setTMapPoint(point);
					mMapView.addMarkerItem2("markerid", marker);
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnGeocoder);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TMapData data = new TMapData();
					TMapPoint point = mMapView.getCenterPoint();
					data.convertGpsToAddress(point.getLatitude(), point.getLongitude(), new TMapData.ConvertGPSToAddressListenerCallback() {
						
						@Override
						public void onConvertToGPSToAddress(String address) {
							Toast.makeText(getActivity(), "address : " + address, Toast.LENGTH_SHORT).show();
						}
					});
				}
			});
		
			keywordView = (EditText)rootView.findViewById(R.id.editText1);
			btn = (Button)rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TMapData data = new TMapData();
					data.findAddressPOI(keywordView.getText().toString(), new TMapData.FindAddressPOIListenerCallback() {
						
						@Override
						public void onFindAddressPOI(ArrayList<TMapPOIItem> pois) {
							for (TMapPOIItem poi : pois) {
								TMapPoint pont = poi.getPOIPoint();
							}
						}
					});
					
					data.findAllPOI(keywordView.getText().toString(), new TMapData.FindAllPOIListenerCallback() {
						
						@Override
						public void onFindAllPOI(ArrayList<TMapPOIItem> pois) {
							
						}
					});
					
					TMapPoint point = mMapView.getCenterPoint();
					
					data.findAroundKeywordPOI(point, keywordView.getText().toString(), 4, 10, new TMapData.FindAroundKeywordPOIListenerCallback() {
						
						@Override
						public void onFindAroundKeywordPOI(ArrayList<TMapPOIItem> pois) {
							
							
						}
					});
					
//					data.findAroundNamePOI(point, "편의점", findAroundNamePoiListener)
					data.findPathData(point, point, new TMapData.FindPathDataListenerCallback() {
						
						@Override
						public void onFindPathData(TMapPolyLine path) {
							mMapView.addTMapPath(path);
							mMapView.setTMapPathIcon(null, null);
						}
					});
					
					data.findPathDataWithType(TMapPathType.PEDESTRIAN_PATH, point, point, new TMapData.FindPathDataListenerCallback() {
						
						@Override
						public void onFindPathData(TMapPolyLine path) {
							
						}
					});
				}
			});
			mMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
				
				@Override
				public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markers,
						ArrayList<TMapPOIItem> pois, TMapPoint mapPoint, PointF screenPoint) {
					return false;
				}
				
				@Override
				public boolean onPressEvent(ArrayList<TMapMarkerItem> markers,
						ArrayList<TMapPOIItem> pois, TMapPoint mapPoint, PointF screenPoint) {
					if (markers != null) {
						for (TMapMarkerItem marker : markers) {
							String id = marker.getID();
							// ...
							TMapPoint point = marker.getTMapPoint();
							int x = mMapView.getMapXForPoint(point.getLongitude(), point.getLatitude());
							int y = mMapView.getMapYForPoint(point.getLongitude(), point.getLatitude());
							// ...
						}
					}
					return false;
				}
			});
			
			mMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
				
				@Override
				public void onCalloutRightButton(TMapMarkerItem item) {
					// item....
				}
			});
			return rootView;
		}
		
		private void setupMap() {
			mMapView.setMapType(mMapView.MAPTYPE_STANDARD);
			mMapView.setTrafficInfo(true);
//			mMapView.setSightVisible(true);
//			mMapView.setCompassMode(true);
			isInitialzed = true;
			Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
			mMapView.setIcon(icon);
			mMapView.setIconVisibility(true);
			if (cacheLocation != null) {
				moveMap(cacheLocation);
				setMyLocation(cacheLocation);
			}
			
		}
		
		
	}

}
