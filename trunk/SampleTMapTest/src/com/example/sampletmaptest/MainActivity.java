package com.example.sampletmaptest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnCalloutRightButtonClickCallback;
import com.skp.Tmap.TMapView.OnClickListenerCallback;
import com.skp.Tmap.TMapView.OnLongClickListenerCallback;

public class MainActivity extends Activity {

	TMapView mapView;
	LocationManager mLocationManager;
	Location currentLocation;
	boolean isInitialized = false;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (TMapView)findViewById(R.id.map);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				mapView.setLanguage(mapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						setUpMap();
					}
				});
			}
		}).start();
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		keywordView = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isInitialized) {
					new TotalPOISearch().execute(keywordView.getText().toString());
				}
			}
		});
	}

	class TotalPOISearch extends AsyncTask<String, Integer, ArrayList<TMapPOIItem>> {

		@Override
		protected ArrayList<TMapPOIItem> doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (params != null && params.length > 0) {
				String keyword = params[0];
				TMapData mapData = new TMapData();
				try {
					ArrayList<TMapPOIItem> items = mapData.findAllPOI(keyword);
					return items;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(ArrayList<TMapPOIItem> result) {
			mapView.addTMapPOIItem(result);
			
		}
		
	}
	public void setUpMap() {
		isInitialized = true;
		mapView.setOnClickListenerCallBack(new OnClickListenerCallback() {
			
			@Override
			public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arg0,
					ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onPressEvent(ArrayList<TMapMarkerItem> markerList,
					ArrayList<TMapPOIItem> poiList, TMapPoint mapPoint, PointF point) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		mapView.setOnLongClickListenerCallback(new OnLongClickListenerCallback() {
			
			@Override
			public void onLongPressEvent(ArrayList<TMapMarkerItem> arg0,
					ArrayList<TMapPOIItem> arg1, TMapPoint mapPoint) {
				// TODO Auto-generated method stub
				TMapMarkerItem item = new TMapMarkerItem();
				item.setTMapPoint(mapPoint);
				Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_happy)).getBitmap();
				item.setIcon(bm);
				item.setPosition(0.5f, 0.5f);
				item.setCanShowCallout(true);
				item.setCalloutTitle("myItem");
				item.setCalloutSubTitle("subtitle");
				Bitmap lbm = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_neutral)).getBitmap();
				item.setCalloutLeftImage(lbm);
				Bitmap rbm = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_sad)).getBitmap();
				item.setCalloutRightButtonImage(rbm);
				item.setVisible(item.VISIBLE);
				mapView.addMarkerItem("mymarker", item);
				
				
			}
		});
		
		mapView.setOnCalloutRightButtonClickListener(new OnCalloutRightButtonClickCallback() {
			
			@Override
			public void onCalloutRightButton(TMapMarkerItem item) {
				// TODO Auto-generated method stub
				if (item.getID().equals("mymarker")) {
					
				}
			}
		});
		
		if (currentLocation != null) {
			setCurrentLocation(currentLocation);
		}
	}
	
	public void setCurrentLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
		mapView.setIconVisibility(true);
		Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mapView.setIcon(bm);
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);
		super.onStart();
	}
	
	protected void onStop() {
		mLocationManager.removeUpdates(mListener);
	};
	
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
			if (isInitialized) {
				setCurrentLocation(location);
			} else {
				currentLocation = location;
			}
			
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
