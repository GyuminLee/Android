package com.example.sampletmaptest2;

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
import com.skp.Tmap.TMapView.OnClickListenerCallback;

public class MainActivity extends Activity {

	TMapView mapView;
	
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
		Button btn = (Button)findViewById(R.id.addMarker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isInitialized) {
					TMapMarkerItem marker = new TMapMarkerItem();
					marker.setTMapPoint(mapView.getCenterPoint());
					Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
					marker.setIcon(bm);
					marker.setName("T Academy");
					marker.setPosition(0.5f, 0.5f);
					marker.setCalloutTitle("MyMarker");
					marker.setCalloutSubTitle("MyMarker Subtitle");
					marker.setCanShowCallout(true);
					mapView.addMarkerItem("myMarker", marker);
				} else {
					Toast.makeText(MainActivity.this, "Not initialized", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isInitialized) {
					EditText keywordView = (EditText)findViewById(R.id.keyword);
					String keyword = keywordView.getText().toString();
					new MyPOIWorker().execute(keyword);
				}
			}
		});
		
		btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mSelectedPoint != null) {
					mStartPoint = mSelectedPoint;
					mSelectedPoint = null;
					Toast.makeText(MainActivity.this, "Set StartPoint", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "Not Selected", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.end);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mSelectedPoint != null) {
					mEndPoint = mSelectedPoint;
					mSelectedPoint = null;
					Toast.makeText(MainActivity.this, "Set EndPoint", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "Not Selected", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.findPath);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mStartPoint != null && mEndPoint != null) {
					new MyPathWorker().execute(mStartPoint, mEndPoint);
					mStartPoint = null;
					mEndPoint = null;
				} else {
					Toast.makeText(MainActivity.this, "Plz... Set StartPoint or EndPoint", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	boolean isInitialized = false;
	Location cacheLocation = null;
	TMapPoint mSelectedPoint = null;
	TMapPoint mStartPoint = null;
	TMapPoint mEndPoint = null;
	
	private void setUpMap() {
		mapView.setTrafficInfo(true);
		isInitialized = true;
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); 
		if ( location != null) {
			moveLocation(location);
		}
		mapView.setOnClickListenerCallBack(new OnClickListenerCallback() {
			
			@Override
			public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markers,
					ArrayList<TMapPOIItem> items, TMapPoint arg2, PointF arg3) {
				// TODO Auto-generated method stub
				for (TMapMarkerItem marker : markers) {
					if (marker.getID().equals("myMarker")) {
						Toast.makeText(MainActivity.this, "MyMarker Press Up", Toast.LENGTH_SHORT).show();
					}
					mSelectedPoint = marker.getTMapPoint();
				}
				
				for (TMapPOIItem item : items) {
					mSelectedPoint = item.getPOIPoint();
				}
				
				return false;
			}
			
			@Override
			public boolean onPressEvent(ArrayList<TMapMarkerItem> arg0,
					ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	class MyPOIWorker extends AsyncTask<String, Integer, ArrayList<TMapPOIItem>> {

		@Override
		protected ArrayList<TMapPOIItem> doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (params.length > 0) {
				String keyword = params[0];
				TMapData data = new TMapData();
				try {
					ArrayList<TMapPOIItem> items = data.findAllPOI(keyword);
					return items;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(ArrayList<TMapPOIItem> result) {
			if (result != null) {
				mapView.addTMapPOIItem(result);
				if (result.size() > 0) {
					TMapPOIItem item = result.get(0);
					mapView.setCenterPoint(item.getPOIPoint().getLongitude(), item.getPOIPoint().getLatitude());
				}
			}
		}
	}
	
	class MyPathWorker extends AsyncTask<TMapPoint, Integer, TMapPolyLine> {

		@Override
		protected TMapPolyLine doInBackground(TMapPoint... params) {
			// TODO Auto-generated method stub
			if (params.length == 2) {
				TMapPoint start = params[0];
				TMapPoint end = params[1];
				TMapData data = new TMapData();
				try {
					TMapPolyLine path = data.findPathData(start, end);
					return path;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 	
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(TMapPolyLine result) {
			// TODO Auto-generated method stub
			if (result != null) {
				mapView.addTMapPath(result);
				Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
				mapView.setTMapPathIcon(bm, bm);
			}
		}
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
			
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
					moveLocation(location);
				} else {
					cacheLocation = location;
				}
			}
		}, null);
	}
	
	private void moveLocation(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
