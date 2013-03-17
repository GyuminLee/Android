package com.example.sampletmap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.skp.Tmap.TMapCircle;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager.onLocationChangedCallback;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapPolygon;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnCalloutRightButtonClickCallback;
import com.skp.Tmap.TMapView.OnClickListenerCallback;
import com.skp.Tmap.TMapView.OnLongClickListenerCallback;

public class MainActivity extends Activity {

	TMapView mapView;
	boolean isInitialized= false;
	LocationManager mLocationManager;
	Location currentLocation;
	int markerId = 0;
	public final static String MARKER_ID_LABEL = "myMarkerID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapView = (TMapView)findViewById(R.id.map);
		new Thread(new Runnable() {

			@Override
			public void run() {
				mapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				mapView.setLanguage(mapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						setUpMap();
					}
				});
			}
			
		}).start();
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Button zoomIn = (Button)findViewById(R.id.zoomIn);
		zoomIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					mapView.MapZoomIn();
				}
			}
		});
		
		Button zoomOut = (Button)findViewById(R.id.zoomOut);
		zoomOut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					mapView.MapZoomOut();
				}
			}
		});

		Button addMarker = (Button)findViewById(R.id.addMarker);
		addMarker.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					TMapPoint point = mapView.getCenterPoint();
					TMapMarkerItem item = new TMapMarkerItem();
					item.setTMapPoint(point);
					Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_happy)).getBitmap();
					item.setIcon(icon);
					item.setName("myMarker");
					
					Bitmap calloutLeftIcon = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_neutral)).getBitmap();
					Bitmap calloutRightButton = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_sad)).getBitmap();
					item.setCalloutTitle("marker title");
					item.setCalloutSubTitle("marker subtitle");
					item.setCalloutLeftImage(calloutLeftIcon);
					item.setCalloutRightButtonImage(calloutRightButton);
					item.setCanShowCallout(true);
					mapView.addMarkerItem(MARKER_ID_LABEL+markerId, item);
					markerId++;
				}
			}
		});
		
		Button addCircle = (Button)findViewById(R.id.addCircle);
		addCircle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					TMapCircle circle = new TMapCircle();
					circle.setCenterPoint(mapView.getCenterPoint());
					circle.setRadius(20.0);
					circle.setLineColor(Color.RED);
					circle.setAreaColor(Color.TRANSPARENT);
					circle.setCircleWidth(10.0f);
					mapView.addTMapCircle("myCircle01", circle);
				}
				
			}
		});
		
		Button addPolygon = (Button)findViewById(R.id.addPolygon);
		addPolygon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					TMapPolygon polygon = new TMapPolygon();
					TMapPoint center = mapView.getCenterPoint();
					double interval = 200 /  1E6;
					TMapPoint pt1 = new TMapPoint(center.getLatitude() - interval, center.getLongitude() - interval);
					TMapPoint pt2 = new TMapPoint(center.getLatitude() - interval, center.getLongitude() + interval);
					TMapPoint pt3 = new TMapPoint(center.getLatitude() + interval, center.getLongitude() + interval);
					TMapPoint pt4 = new TMapPoint(center.getLatitude() + interval, center.getLongitude() - interval);
					polygon.addPolygonPoint(pt1);
					polygon.addPolygonPoint(pt2);
					polygon.addPolygonPoint(pt3);
					polygon.addPolygonPoint(pt4);
					polygon.setLineColor(Color.RED);
					polygon.setPolygonWidth(10.0f);
					polygon.setAreaColor(Color.TRANSPARENT);
					mapView.addTMapPolygon("myPolygon01", polygon);
				}
			}
		});
		
		Button addPolyline = (Button)findViewById(R.id.addPolyline);
		addPolyline.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInitialized) {
					TMapPolyLine polyline = new TMapPolyLine();
					TMapPoint center = mapView.getCenterPoint();
					double interval = 200 /  1E6;
					TMapPoint pt1 = new TMapPoint(center.getLatitude() - interval, center.getLongitude() - interval);
					TMapPoint pt2 = new TMapPoint(center.getLatitude() - interval, center.getLongitude() + interval);
					TMapPoint pt3 = new TMapPoint(center.getLatitude() + interval, center.getLongitude() + interval);
					TMapPoint pt4 = new TMapPoint(center.getLatitude() + interval, center.getLongitude() - interval);
					polyline.addLinePoint(pt1);
					polyline.addLinePoint(pt2);
					polyline.addLinePoint(pt3);
					polyline.addLinePoint(pt4);
					polyline.setLineColor(Color.RED);
					polyline.setLineWidth(10.0f);
					mapView.addTMapPolyLine("myPolyline01", polyline);
				}
			}
		});
		
		Button totalSearch = (Button)findViewById(R.id.total);
		totalSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = ((EditText)findViewById(R.id.keyword)).getText().toString();
				new TotalPOIWorker().execute(keyword);
			}
		});
		
		Button areaSearch = (Button)findViewById(R.id.area);
		areaSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = ((EditText)findViewById(R.id.keyword)).getText().toString();
				new AreaPOIWorker().execute(keyword);
			}
		});
		
		Button route = (Button)findViewById(R.id.route);
		route.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TMapPoint start = new TMapPoint(37.56468648536046,126.98217734415019);
				TMapPoint end = new TMapPoint(35.17883196265564,129.07579349764512);
				new RoutePathWorker().execute(start, end);
			}
		});
		
	}

	public void setUpMap() {
		mapView.setCompassMode(true);
		mapView.setTrackingMode(true);
		mapView.setSightVisible(true);
		mapView.setOnClickListenerCallBack(click);
		mapView.setOnLongClickListenerCallback(longclick);
		mapView.setOnCalloutRightButtonClickListener(callout);
		isInitialized = true;
		if (currentLocation != null) {
			setCurrentLocation(currentLocation);
		}
	}
	

	public class TotalPOIWorker extends AsyncTask<String, Integer, ArrayList<TMapPOIItem>> {

		@Override
		protected ArrayList<TMapPOIItem> doInBackground(String... params) {
			if (isInitialized && params != null && params.length > 0) {
				String keyword = params[0];
				TMapData poiData = new TMapData();
				try {
					ArrayList<TMapPOIItem> items = poiData.findAllPOI(keyword);
					return items;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (FactoryConfigurationError e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(ArrayList<TMapPOIItem> result) {
			if (isInitialized && result != null) {
				Toast.makeText(MainActivity.this, "Total POI Added", Toast.LENGTH_SHORT).show();
				mapView.addTMapPOIItem(result);
			}
		}
	}
	
	public class AreaPOIWorker extends AsyncTask<String, Integer, ArrayList<TMapPOIItem>> {

		@Override
		protected ArrayList<TMapPOIItem> doInBackground(String... params) {
			if (isInitialized && params != null && params.length > 0) {
				TMapPoint point = mapView.getLocationPoint();
				if (point == null) {
					point = mapView.getCenterPoint();
				}
				TMapData poiData = new TMapData();
				String keyword = params[0];
				try {
					ArrayList<TMapPOIItem> items = poiData.findAroundNamePOI(point, keyword);
					return items;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (FactoryConfigurationError e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(ArrayList<TMapPOIItem> result) {
			if (isInitialized && result != null) {
				Toast.makeText(MainActivity.this, "Area POI Added", Toast.LENGTH_SHORT).show();
				mapView.addTMapPOIItem(result);
			}
		}
	}
	
	public class RoutePathWorker extends AsyncTask<TMapPoint, Integer, TMapPolyLine> {

		@Override
		protected TMapPolyLine doInBackground(TMapPoint... params) {
			if (isInitialized && params != null && params.length >= 2) {
				TMapPoint start = params[0];
				TMapPoint end = params[1];
				TMapData poiData = new TMapData();
				try {
					TMapPolyLine route = poiData.findPathData(start, end);
					return route;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (FactoryConfigurationError e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(TMapPolyLine result) {
			if (isInitialized && result != null) {
				Toast.makeText(MainActivity.this, "Route Added", Toast.LENGTH_SHORT).show();
				mapView.addTMapPath(result);
			}
		}
	}
	
	public class MyLocationListener extends ContextWrapper implements onLocationChangedCallback {

		public MyLocationListener(Context base) {
			super(base);
		}

		@Override
		public void onLocationChange(Location arg0) {
			
		}
		
	}
	OnCalloutRightButtonClickCallback callout = new OnCalloutRightButtonClickCallback() {
		
		@Override
		public void onCalloutRightButton(TMapMarkerItem marker) {
			Toast.makeText(MainActivity.this, "onCalloutRightButton", Toast.LENGTH_SHORT).show();
			if (marker.getID().equals(MARKER_ID_LABEL+"0")) {
				Toast.makeText(MainActivity.this, "first marker callout right button click", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	OnClickListenerCallback click = new OnClickListenerCallback() {
		
		@Override
		public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint mapPoint, PointF screenPoint) {
			
			Toast.makeText(MainActivity.this, "onPressUpEvent", Toast.LENGTH_SHORT).show();
			for (TMapMarkerItem marker : markers) {
				if (marker.getID().equals(MARKER_ID_LABEL+"0")) {
					Toast.makeText(MainActivity.this, "first marker press up", Toast.LENGTH_SHORT).show();
				}
			}
			return true;
		}
		
		@Override
		public boolean onPressEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint mapPoint, PointF scrrenPoint) {
			Toast.makeText(MainActivity.this, "onPressEvent", Toast.LENGTH_SHORT).show();
			for (TMapMarkerItem marker : markers) {
				if (marker.getID().equals(MARKER_ID_LABEL+"0")) {
					Toast.makeText(MainActivity.this, "first marker press", Toast.LENGTH_SHORT).show();
				}
			}
			return true;
		}
	};
	
	OnLongClickListenerCallback longclick = new OnLongClickListenerCallback() {
		
		@Override
		public void onLongPressEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint mapPoint) {
			Toast.makeText(MainActivity.this, "onLongPressEvent", Toast.LENGTH_SHORT).show();			
			for (TMapMarkerItem marker : markers) {
				if (marker.getID().equals(MARKER_ID_LABEL+"0")) {
					Toast.makeText(MainActivity.this, "first marker long press", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	
	LocationListener mMapListener = new LocationListener() {
		
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
			//mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
		}
	};
	
	public void setCurrentLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(),location.getLatitude());
		mapView.setIconVisibility(true);
		Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mapView.setIcon(bm);
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	LocationListener mCurrentLocation = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			if (isInitialized) {
				setCurrentLocation(location);
			} else {
				currentLocation = location;
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
	protected void onStart() {
		super.onStart();
		mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mMapListener, null);
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mCurrentLocation);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLocationManager.removeUpdates(mCurrentLocation);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
