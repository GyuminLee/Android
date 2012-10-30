package org.tacademy.network.rss.yahooplace;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.R.drawable;
import org.tacademy.network.rss.R.id;
import org.tacademy.network.rss.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class MyMapActivity extends MapActivity implements MyItemizedOverlay.OnMapItemClickListener, MapInputView.OnMapInfoAddListener {

	MapView mapView;
	MapController mapCtrl;
	TextView name;
	YahooPlacesItem item;
	YahooPlaces places;
	MyLocationOverlay myOverlay;
	MyItemizedOverlay itemOverlay;
	MapExplainView explainView;
	MapInputView inputView;
	boolean isFocused = false;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.map_view);

	    name = (TextView)findViewById(R.id.name);
	    mapView = (MapView)findViewById(R.id.mapView);
	    mapCtrl = mapView.getController();
	    mapCtrl.setZoom(16);
	    
	    Intent i = getIntent();
	    item = i.getParcelableExtra("item");

	    if (item != null) {
		    name.setText(item.name);
		    GeoPoint point = new GeoPoint((int)(item.latitude * 1E6), (int)(item.longitude *1E6));
		    addItem(point);
		    mapCtrl.setCenter(point);
	    }
	    places = i.getParcelableExtra("items");
	    if (places != null) {
	    	itemOverlay = new MyItemizedOverlay(this.getResources().getDrawable(R.drawable.poi_icon_selector),this);
	    	itemOverlay.setYahooPlacesItems(places.items);
	    	itemOverlay.setOnMapItemClickListener(this);
	    	mapView.getOverlays().add(itemOverlay);
	    	GeoPoint point = new GeoPoint((int)(places.items.get(0).latitude * 1E6), (int)(places.items.get(0).longitude * 1E6));
	    	mapCtrl.setCenter(point);
	    }

	    Button btn = (Button)findViewById(R.id.current);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				myOverlay = new MyLocationOverlay(getApplicationContext(),mapView);
				mapView.getOverlays().add(myOverlay);
				myOverlay.runOnFirstFix(new Runnable() {

					public void run() {
						mapCtrl.animateTo(myOverlay.getMyLocation());
					}
					
				});
				myOverlay.enableCompass();
				myOverlay.enableMyLocation();
			}
		});
	    
	    btn = (Button)findViewById(R.id.prev);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OverlayItem item;
				if (isFocused == false) {
					item = itemOverlay.getItem(0);
					isFocused = true;
				} else {
					item = itemOverlay.nextFocus(false);
				}
				itemOverlay.setFocus(item);
				if (item == null) {
					Toast.makeText(MyMapActivity.this, "item is null", Toast.LENGTH_SHORT).show();
				} else {
					mapCtrl.setCenter(item.getPoint());
				}
			}
		});
	    btn = (Button)findViewById(R.id.next);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OverlayItem item;
				if (isFocused == false) {
					item = itemOverlay.getItem(0);
					isFocused = true;
				} else {
					item = itemOverlay.nextFocus(true);
				}
				itemOverlay.setFocus(item);
				if (item == null) {
					Toast.makeText(MyMapActivity.this, "item is null", Toast.LENGTH_SHORT).show();
				} else {
					mapCtrl.setCenter(item.getPoint());
				}
			}
		});
	}

	public void addItem(GeoPoint point) {
		ImageView iv = new ImageView(getApplicationContext());
		iv.setImageResource(R.drawable.icon);
		MapView.LayoutParams param = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 
				point, 
				MapView.LayoutParams.TOP_LEFT);
		mapView.addView(iv, param);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (myOverlay != null) {
			myOverlay.disableCompass();
			myOverlay.disableMyLocation();
		}
	}

	public void onMapItemClick(YahooPlacesItem item) {
		if (explainView == null) {
			explainView = new MapExplainView(this);
		} else {
			mapView.removeView(explainView);			
		}
		
		GeoPoint point = new GeoPoint((int)(item.latitude * 1E6),(int)(item.longitude * 1E6));
		MapView.LayoutParams param = new MapView.LayoutParams(
			LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT,
			point,
			MapView.LayoutParams.CENTER);
		explainView.setData(item.name, item.country + " " + item.city + " " + item.county);
		mapView.addView(explainView,param);
	}

	public void onMapClick(GeoPoint point) {
		if (inputView == null) {
			inputView = new MapInputView(this);
			inputView.setOnMapInfoAddListener(this);
		} else {
			mapView.removeView(inputView);			
		}

		MapView.LayoutParams param = new MapView.LayoutParams(
			LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT,
			point,
			MapView.LayoutParams.CENTER);
		inputView.setLocation((double)(point.getLatitudeE6()) / 1E6, (double)(point.getLongitudeE6()) / 1E6);
		mapView.addView(inputView,param);
	}

	public void onAdd(YahooPlacesItem item) {
		itemOverlay.addYahooPlacesItems(item);
	}

}
