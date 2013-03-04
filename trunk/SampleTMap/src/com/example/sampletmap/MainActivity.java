package com.example.sampletmap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skp.Tmap.TMapCircle;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapGpsManager.onLocationChangedCallback;
import com.skp.Tmap.BizCategory;
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
	LocationManager mLocationManager;
	
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
			}
			
		}).start();
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mapView.setTrafficInfo(true);
				TMapPoint point = mapView.getCenterPoint();
				TMapMarkerItem item = new TMapMarkerItem();
				item.setTMapPoint(point);
				Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
				Bitmap bm2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_happy)).getBitmap();
				Bitmap bm3 = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_neutral)).getBitmap();
//				item.setIcon(bm);
				item.setName("marker1");
				item.setCalloutTitle("marker title");
//				item.setCalloutLeftImage(bm2);
//				item.setCalloutRightButtonImage(bm3);
				item.setCalloutSubTitle("sub title");
				item.setCanShowCallout(true);
				mapView.addMarkerItem("marker1", item);
				mapView.bringMarkerToFront(item);
				
				TMapPolyLine polyline = new TMapPolyLine();
				TMapPolygon polygon = new TMapPolygon();
				TMapCircle circle = new TMapCircle();
				TMapGpsManager manager = new TMapGpsManager(MainActivity.this);
				TMapPOIItem poi = new TMapPOIItem();
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						TMapData data = new TMapData();
						try {
							final ArrayList<TMapPOIItem> items = data.findAllPOI("SKT타워");
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									mapView.addTMapPOIItem(items);
								}
							});
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FactoryConfigurationError e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
//				int level = mapView.getZoomLevel();
//				Toast.makeText(MainActivity.this, "zoom level : " + level, Toast.LENGTH_SHORT).show();
				
			}
		});
		mapView.setOnClickListenerCallBack(click);
		mapView.setOnLongClickListenerCallback(longclick);
		mapView.setOnCalloutRightButtonClickListener(callout);
	}

	public class MyLocationListener extends ContextWrapper implements onLocationChangedCallback {

		public MyLocationListener(Context base) {
			super(base);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onLocationChange(Location arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	OnCalloutRightButtonClickCallback callout = new OnCalloutRightButtonClickCallback() {
		
		@Override
		public void onCalloutRightButton(TMapMarkerItem arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "onCalloutRightButton", Toast.LENGTH_SHORT).show();
		}
	};
	
	OnClickListenerCallback click = new OnClickListenerCallback() {
		
		@Override
		public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arg0,
				ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
			
			Toast.makeText(MainActivity.this, "onPressUpEvent", Toast.LENGTH_SHORT).show();
			return true;
		}
		
		@Override
		public boolean onPressEvent(ArrayList<TMapMarkerItem> arg0,
				ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "onPressEvent", Toast.LENGTH_SHORT).show();
			return true;
		}
	};
	
	OnLongClickListenerCallback longclick = new OnLongClickListenerCallback() {
		
		@Override
		public void onLongPressEvent(ArrayList<TMapMarkerItem> arg0,
				ArrayList<TMapPOIItem> arg1, TMapPoint arg2) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "onLongPressEvent", Toast.LENGTH_SHORT).show();			
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
			mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
		}
	};
	
	LocationListener mCurrentLocation = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			mapView.setLocationPoint(location.getLongitude(),location.getLatitude());
			mapView.setIconVisibility(true);
			Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
			mapView.setIcon(bm);
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
