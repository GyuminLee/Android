package org.tacademy.network.rss.yahooplace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.MyLocationOverlay;

public class CustomMyLocationOverlay extends MyLocationOverlay {

	Drawable mCompassDrawable;
	ImageView mMyLocationImage;
	Context mContext;
	MapView mMapView;
	
	public CustomMyLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
		mContext = context;
		mMapView = mapView;
		// TODO Auto-generated constructor stub
	}

	public void setImage(Drawable compassDrawable,Drawable myLocationDrawable) {
		mCompassDrawable = compassDrawable;
		mMyLocationImage = new ImageView(mContext);
		mMyLocationImage.setImageDrawable(myLocationDrawable);
		GeoPoint point = new GeoPoint(0,0);
		MapView.LayoutParams params = new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point, MapView.LayoutParams.CENTER);
		mMapView.addView(mMyLocationImage, params);
	}
	
	@Override
	protected void drawCompass(Canvas canvas, float bearing) {
		// TODO Auto-generated method stub
		// bearing에 따른 방향을 mCompassDrawable에 반영해야 함.
		mCompassDrawable.draw(canvas);
		//super.drawCompass(canvas, bearing);
	}


	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView,
			Location lastFix, GeoPoint myLocation, long when) {
		// TODO Auto-generated method stub
		MapView.LayoutParams params = new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,myLocation,MapView.LayoutParams.CENTER);
		mapView.updateViewLayout(mMyLocationImage, params);
		//super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
	}

}
