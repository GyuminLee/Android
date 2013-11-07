package com.example.hellotmaptest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

public class MainActivity extends Activity {
	boolean isInitialized = false;
	TMapView mapView;
	public static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	LocationManager mLM;
	int mMarkerId = 0;
	
	private static final String TAG = "MainActivity";
	TMapPoint selectPoint, startPoint, endPoint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (TMapView)findViewById(R.id.mapView);
		new ApiKeySetTask().execute("");
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Button btn = (Button)findViewById(R.id.btnMarker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TMapPoint point = mapView.getCenterPoint();
				TMapMarkerItem item = new TMapMarkerItem();
				item.setTMapPoint(point);
				item.setPosition(0.5f, 1);
				Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
				item.setIcon(icon);
				item.setName("MyMarker");
				item.setCalloutTitle("My Marker");
				item.setCalloutSubTitle("subtitle");
				item.setCalloutRightButtonImage(icon);
				item.setCanShowCallout(true);
				mapView.addMarkerItem("marker"+mMarkerId, item);
				mMarkerId++;
			}
		});
		
		btn = (Button)findViewById(R.id.btnSearch);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = ((EditText)findViewById(R.id.keyword)).getText().toString();
				if (keyword != null && !keyword.equals("")) {
					new POISearchTask().execute(keyword);
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (selectPoint != null) {
					startPoint = selectPoint;
					selectPoint = null;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnEnd);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (selectPoint != null) {
					endPoint = selectPoint;
					selectPoint = null;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnRoute);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (startPoint != null && endPoint != null) {
					new RouteTask().execute(startPoint,endPoint);
					startPoint = null;
					endPoint = null;
				} else {
					Toast.makeText(MainActivity.this, "point not set", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	
	class RouteTask extends AsyncTask<TMapPoint, Integer, TMapPolyLine> {
		@Override
		protected TMapPolyLine doInBackground(TMapPoint... params) {
			TMapPoint start = params[0];
			TMapPoint end = params[1];
			TMapData mapData = new TMapData();
			try {
				TMapPolyLine line = mapData.findPathData(start, end);
				return line;
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return null;
		}
		
		@Override
		protected void onPostExecute(TMapPolyLine result) {
			if (result != null) {
				mapView.addTMapPath(result);
				Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
				mapView.setTMapPathIcon(icon, icon);
			}
			super.onPostExecute(result);
		}
	}
	
	class POISearchTask extends AsyncTask<String, Integer, ArrayList<TMapPOIItem>> {
		@Override
		protected ArrayList<TMapPOIItem> doInBackground(String... params) {
			TMapData mapData = new TMapData();
			String keyword = params[0];
			try {
				ArrayList<TMapPOIItem> items = mapData.findAllPOI(keyword);
				return items;
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return null;
		}
		
		@Override
		protected void onPostExecute(ArrayList<TMapPOIItem> result) {
			if (result != null) {
				mapView.addTMapPOIItem(result);
			}
			super.onPostExecute(result);
		}
	}
	
	TMapView.OnClickListenerCallback callback = new TMapView.OnClickListenerCallback() {
		
		@Override
		public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arg0,
				ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
			return false;
		}
		
		@Override
		public boolean onPressEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint location, PointF point) {
			if (markers != null) {
				for (TMapMarkerItem item : markers) {
					Log.i(TAG, "marker id : " + item.getID());
					selectPoint = item.getTMapPoint();
				}
			}
			if (pois != null) {
				for(TMapPOIItem poi : pois) {
					Log.i(TAG, "poi id : " + poi.getPOIID());
					selectPoint = poi.getPOIPoint();
				}
			}
			
			
			return false;
		}
	};
	
	TMapView.OnCalloutRightButtonClickCallback callout = new TMapView.OnCalloutRightButtonClickCallback() {
		
		@Override
		public void onCalloutRightButton(TMapMarkerItem item) {
			Toast.makeText(MainActivity.this, "marker id : " + item.getID(), Toast.LENGTH_SHORT).show();			
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
		mLM.removeUpdates(mListener);
		super.onStop();
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
				setMyLocation(location);
				moveLocation(location);
			} else {
				tempLocation = location;
			}
		}
	};
	
	Location tempLocation;

	private void setMyLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(), location.getLatitude());
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mapView.setIcon(icon);
	}
	private void moveLocation(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	class ApiKeySetTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			mapView.setSKPMapApiKey(APP_KEY);
			mapView.setLanguage(mapView.LANGUAGE_KOREAN);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			setUpMap();
			super.onPostExecute(result);
		}
	}

	public void setUpMap() {
		mapView.setOnClickListenerCallBack(callback);
		isInitialized = true;
		if (tempLocation != null) {
			setMyLocation(tempLocation);
			moveLocation(tempLocation);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
