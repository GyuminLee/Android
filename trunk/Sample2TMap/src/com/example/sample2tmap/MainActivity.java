package com.example.sample2tmap;

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
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.TMapPathType;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

public class MainActivity extends Activity {
	TMapView mMap;
	LocationManager mLM;
	EditText keywordView;
	TMapPoint currentPoint = null;
	TMapPoint startPoint = null;
	TMapPoint endPoint = null;
	TextView positionView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMap = (TMapView) findViewById(R.id.map);
		keywordView = (EditText) findViewById(R.id.keywordView);
		positionView = (TextView) findViewById(R.id.positionView);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		new MapRegisterTask().execute("");
		Button btn = (Button) findViewById(R.id.btnZoomIn);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isInitialized) {
					mMap.MapZoomIn();
				}
			}
		});

		btn = (Button) findViewById(R.id.btnZoomOut);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isInitialized) {
					mMap.MapZoomOut();
				}
			}
		});

		btn = (Button) findViewById(R.id.btnMarker);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isInitialized) {
					TMapMarkerItem item = new TMapMarkerItem();
					TMapPoint point = mMap.getCenterPoint();
					item.setTMapPoint(point);
					Bitmap bm = ((BitmapDrawable) getResources().getDrawable(
							R.drawable.ic_launcher)).getBitmap();
					item.setIcon(bm);
					item.setPosition(0.5f, 0.5f);
					item.setCalloutTitle("my marker");
					item.setCalloutSubTitle("sub title");
					Bitmap lbm = ((BitmapDrawable) getResources().getDrawable(
							R.drawable.ic_launcher)).getBitmap();
					item.setCalloutLeftImage(lbm);
					Bitmap rbm = ((BitmapDrawable) getResources().getDrawable(
							R.drawable.ic_launcher)).getBitmap();
					item.setCalloutRightButtonImage(rbm);
					item.setCanShowCallout(true);
					mMap.addMarkerItem("markerid", item);
				}
			}
		});

		btn = (Button) findViewById(R.id.btnSearch);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					new SearckTask().execute(keyword);
				}
			}
		});

		btn = (Button) findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentPoint != null) {
					startPoint = currentPoint;
					currentPoint = null;
					positionView.setText("not set");
				}
			}
		});
		btn = (Button) findViewById(R.id.btnEnd);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentPoint != null) {
					endPoint = currentPoint;
					currentPoint = null;
					positionView.setText("not set");
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnRoute);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (startPoint != null && endPoint != null) {
					new RouteSearchTask().execute(startPoint,endPoint);
					startPoint = endPoint = null;
				}
			}
		});
	}
	
	class RouteSearchTask extends AsyncTask<TMapPoint, Integer, TMapPolyLine> {
		@Override
		protected TMapPolyLine doInBackground(TMapPoint... params) {
			TMapPoint start = params[0];
			TMapPoint end = params[1];
			TMapData data = new TMapData();
			try {
				TMapPolyLine path = data.findPathDataWithType(TMapPathType.CAR_PATH, start, end);
				return path;
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return null;
		}
		
		@Override
		protected void onPostExecute(TMapPolyLine result) {
			if (result != null) {
				mMap.addTMapPath(result);
				Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
				mMap.setTMapPathIcon(bm, bm);
			}
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
				moveMyLocation(location);
			} else {
				cacheLocation = location;
			}
		}
	};

	Location cacheLocation = null;
	boolean isInitialized = false;

	private void moveMap(Location location) {
		if (isInitialized) {
			mMap.setCenterPoint(location.getLongitude(), location.getLatitude());
		}
	}

	private void moveMyLocation(Location location) {
		if (isInitialized) {
			mMap.setLocationPoint(location.getLongitude(),
					location.getLatitude());
			Bitmap icon = ((BitmapDrawable) getResources().getDrawable(
					R.drawable.ic_launcher)).getBitmap();
			mMap.setIcon(icon);
			mMap.setIconVisibility(true);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
				mListener);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}

	class MapRegisterTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			mMap.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
			mMap.setLanguage(mMap.LANGUAGE_KOREAN);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			setUpMap();
		}
	}

	private void setUpMap() {
		isInitialized = true;
		if (cacheLocation != null) {
			moveMap(cacheLocation);
			moveMyLocation(cacheLocation);
			cacheLocation = null;
		}
		mMap.setTrafficInfo(true);

		mMap.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {

			@Override
			public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markers,
					ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {

				return false;
			}

			@Override
			public boolean onPressEvent(ArrayList<TMapMarkerItem> markers,
					ArrayList<TMapPOIItem> poiitems, TMapPoint point,
					PointF arg3) {
				Toast.makeText(
						MainActivity.this,
						"lat : " + point.getLatitude() + ",lng : "
								+ point.getLongitude(), Toast.LENGTH_SHORT)
						.show();
				for (TMapMarkerItem item : markers) {
					Toast.makeText(MainActivity.this,
							"click marker : " + item.getID(),
							Toast.LENGTH_SHORT).show();
				}

				for (TMapPOIItem item : poiitems) {
					Toast.makeText(MainActivity.this,
							"poi : " + item.getPOIName(), Toast.LENGTH_SHORT)
							.show();
				}
				currentPoint = point;
				positionView.setText("lat : " + currentPoint.getLatitude()
						+ ",lng : " + currentPoint.getLongitude());
				return false;
			}
		});

		mMap.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {

			@Override
			public void onCalloutRightButton(TMapMarkerItem item) {
				Toast.makeText(MainActivity.this, "item id : " + item.getID(),
						Toast.LENGTH_SHORT).show();
			}
		});
		// mMap.setSightVisible(true);
		// mMap.setCompassMode(true);
		// mMap.setTrackingMode(true);

	}

	class SearckTask extends AsyncTask<String, Integer, ArrayList<TMapPOIItem>> {
		@Override
		protected ArrayList<TMapPOIItem> doInBackground(String... params) {
			TMapData mapData = new TMapData();
			String keyword = params[0];
			try {
				TMapPoint point = mMap.getCenterPoint();
				ArrayList<TMapPOIItem> list = mapData.findAroundKeywordPOI(
						point, keyword, 500, 10);
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<TMapPOIItem> result) {
			if (result != null) {
				mMap.addTMapPOIItem(result);
				if (result.size() > 0) {
					mMap.setCenterPoint(result.get(0).getPOIPoint().getLongitude(), result.get(0).getPOIPoint().getLatitude());
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
