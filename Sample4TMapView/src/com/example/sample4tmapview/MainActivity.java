package com.example.sample4tmapview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skp.Tmap.TMapCircle;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnCalloutRightButtonClickCallback;
import com.skp.Tmap.TMapView.OnClickListenerCallback;

public class MainActivity extends Activity {

	TMapView mapView;
	private static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	LocationManager mLM;
	boolean isInitialized = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (TMapView)findViewById(R.id.mapView);
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Button btn = (Button)findViewById(R.id.btn_add_marker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TMapPoint point = mapView.getCenterPoint();
//				TMapPoint mp = new TMapPoint(point.getLatitude(), point.getLongitude());
				TMapMarkerItem item = new TMapMarkerItem();
				Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
				item.setName("ysi");
				item.setIcon(bitmap);
				item.setTMapPoint(point);
				item.setPosition(0.5f, 0.5f);
				item.setCalloutTitle("Icon");
				item.setCalloutSubTitle("sub title");
				item.setCalloutRightButtonImage(bitmap);
				item.setCanShowCallout(true);
				mapView.addMarkerItem("id1", item);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_remove_marker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mapView.removeMarkerItem("id1");
			}
		});
		
		new RegisterTask().execute();
	}
	int id = 1;
	class RegisterTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			mapView.setSKPMapApiKey(APP_KEY);
			mapView.setLanguage(mapView.LANGUAGE_KOREAN);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result != null && result) {
				initailizeMap();
			}
		}
	}
	
	private void initailizeMap() {
		isInitialized = true;
		mapView.setMapType(mapView.MAPTYPE_STANDARD);
		mapView.setTrafficInfo(true);
//		mapView.setCompassMode(true);
		mapView.setSightVisible(true);
//		mapView.setTrackingMode(true);
		mapView.setIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());
		mapView.setIconVisibility(true);
		mapView.setOnCalloutRightButtonClickListener(new OnCalloutRightButtonClickCallback() {
			
			@Override
			public void onCalloutRightButton(TMapMarkerItem item) {
				Toast.makeText(MainActivity.this, "marker : " + item.getName(), Toast.LENGTH_SHORT).show();
			}
		});
		
		mapView.setOnClickListenerCallBack(new OnClickListenerCallback() {
			
			@Override
			public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arg0,
					ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
				mapView.removeTMapCircle("circle1");
				return false;
			}
			
			@Override
			public boolean onPressEvent(ArrayList<TMapMarkerItem> arg0,
					ArrayList<TMapPOIItem> arg1, TMapPoint point, PointF arg3) {
				TMapCircle circle = new TMapCircle();
				circle.setCenterPoint(point);
				circle.setRadius(100);
				circle.setLineColor(Color.RED);
				circle.setAreaColor(Color.DKGRAY);
				circle.setAreaAlpha(80);
				mapView.addTMapCircle("circle1", circle);
				return false;
			}
		});
		if (cacheLocation != null) {
			moveMap(cacheLocation);
			setMyLocation(cacheLocation);
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
			if (isInitialized) {
				moveMap(location);
				setMyLocation(location);
			} else {
				cacheLocation = location;
			}
		}
	};
	Location cacheLocation = null;
	private void moveMap(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	private void setMyLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
	}
	
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
	
}
