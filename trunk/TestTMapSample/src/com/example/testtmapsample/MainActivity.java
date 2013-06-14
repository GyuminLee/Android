package com.example.testtmapsample;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

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

public class MainActivity extends Activity {

	TMapView mMapView;
	EditText keywordView;
	LocationManager mLocationManager;
	boolean isInitialized = false;
	HashMap<String, TMapMarkerItem> markers = new HashMap<String, TMapMarkerItem>();

	TMapPoint mSelectedPoint;
	TMapPoint mStartPoint;
	TMapPoint mEndPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMapView = (TMapView) findViewById(R.id.map);
		keywordView = (EditText) findViewById(R.id.keyword);
		new Thread(new Runnable() {

			@Override
			public void run() {
				mMapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						setUpMap();
					}
				});
			}
		}).start();

		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Button btn = (Button) findViewById(R.id.zoomIn);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mMapView.MapZoomIn();
			}
		});

		btn = (Button) findViewById(R.id.zoomOut);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mMapView.MapZoomOut();
			}
		});

		btn = (Button) findViewById(R.id.addMarker);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TMapMarkerItem item = new TMapMarkerItem();
				TMapPoint center = mMapView.getCenterPoint();
				TMapPoint point = new TMapPoint(center.getLatitude(), center
						.getLongitude());
				item.setTMapPoint(point);
				Bitmap icon = ((BitmapDrawable) getResources().getDrawable(
						R.drawable.stat_happy)).getBitmap();
				item.setIcon(icon);
				item.setPosition(0.5f, 0.5f);
				item.setName("MyMarker");
				item.setCanShowCallout(true);
				item.setCalloutTitle("title");
				item.setCalloutSubTitle("sub title");
				Bitmap lefticon = ((BitmapDrawable) getResources().getDrawable(
						R.drawable.stat_neutral)).getBitmap();
				item.setCalloutLeftImage(lefticon);
				Bitmap righticon = ((BitmapDrawable) getResources()
						.getDrawable(R.drawable.stat_sad)).getBitmap();
				item.setCalloutRightButtonImage(righticon);
				mMapView.addMarkerItem("markerid", item);
			}
		});
		btn = (Button) findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					new TotalPOIWorker().execute(keyword);
				}
			}
		});

		btn = (Button) findViewById(R.id.setstart);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSelectedPoint != null) {
					mStartPoint = mSelectedPoint;
					mSelectedPoint = null;
				} else {
					Toast.makeText(MainActivity.this, "not selected",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn = (Button) findViewById(R.id.setend);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSelectedPoint != null) {
					mEndPoint = mSelectedPoint;
					mSelectedPoint = null;
				} else {
					Toast.makeText(MainActivity.this, "not selected",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn = (Button) findViewById(R.id.route);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mStartPoint != null && mEndPoint != null) {
					new RoutingSearchWorker().execute(mStartPoint, mEndPoint);
					mStartPoint = null;
					mEndPoint = null;
				} else {
					Toast.makeText(MainActivity.this,
							"not setting start point or end point ",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Location location = mLocationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 2000, 0, mListener);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLocationManager.removeUpdates(mListener);
	}

	Location mCacheLocation = null;

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
				setMoveMap(location);
			} else {
				mCacheLocation = location;
			}
		}
	};

	private void setMoveMap(Location location) {
		mMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}

	private void setMyLocation(Location location) {
		mMapView.setLocationPoint(location.getLongitude(),
				location.getLatitude());
		Bitmap icon = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.ic_launcher)).getBitmap();
		mMapView.setIcon(icon);
		mMapView.setIconVisibility(true);
	}

	private void setUpMap() {
		// mMapView.setCompassMode(true);
		// mMapView.setTrackingMode(true);
		mMapView.setTrafficInfo(true);
		mMapView.setSightVisible(true);
		mMapView.setOnClickListenerCallBack(clickCallback);
		mMapView.setOnCalloutRightButtonClickListener(callout);
		isInitialized = true;
		if (mCacheLocation != null) {
			setMyLocation(mCacheLocation);
			setMoveMap(mCacheLocation);
			mCacheLocation = null;
		}
	}

	TMapView.OnClickListenerCallback clickCallback = new TMapView.OnClickListenerCallback() {

		@Override
		public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint mappoint, PointF point) {

			return false;
		}

		@Override
		public boolean onPressEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint mappoint, PointF point) {

			for (TMapMarkerItem marker : markers) {
				mSelectedPoint = marker.getTMapPoint();
			}

			for (TMapPOIItem poi : pois) {
				mSelectedPoint = poi.getPOIPoint();
			}

			return false;
		}
	};

	TMapView.OnCalloutRightButtonClickCallback callout = new TMapView.OnCalloutRightButtonClickCallback() {

		@Override
		public void onCalloutRightButton(TMapMarkerItem item) {
			Toast.makeText(MainActivity.this, "callout", Toast.LENGTH_SHORT)
					.show();
		}
	};

	public class TotalPOIWorker extends
			AsyncTask<String, Integer, ArrayList<TMapPOIItem>> {

		@Override
		protected ArrayList<TMapPOIItem> doInBackground(String... params) {
			if (params != null && params.length > 0) {
				String keyword = params[0];
				TMapData data = new TMapData();
				try {
					ArrayList<TMapPOIItem> items = data.findAllPOI(keyword);
					return items;
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
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<TMapPOIItem> result) {
			if (result != null) {
				mMapView.addTMapPOIItem(result);
				if (result.size() > 0) {
					TMapPOIItem item = result.get(0);
					mMapView.setCenterPoint(item.getPOIPoint().getLongitude(),
							item.getPOIPoint().getLatitude());
				}
			}
		}

	}

	public class RoutingSearchWorker extends
			AsyncTask<TMapPoint, Integer, TMapPolyLine> {

		@Override
		protected TMapPolyLine doInBackground(TMapPoint... params) {
			if (params != null && params.length >= 2) {
				TMapPoint start = params[0];
				TMapPoint end = params[1];
				TMapData data = new TMapData();
				try {
					TMapPolyLine line = data.findPathData(start, end);
					return line;
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
			return null;
		}

		@Override
		protected void onPostExecute(TMapPolyLine result) {
			if (result != null) {
				mMapView.addTMapPath(result);
				Bitmap startIcon = ((BitmapDrawable) getResources()
						.getDrawable(R.drawable.stat_happy)).getBitmap();
				Bitmap endIcon = ((BitmapDrawable) getResources().getDrawable(
						R.drawable.stat_sad)).getBitmap();
				mMapView.setTMapPathIcon(startIcon, endIcon);
			}
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
