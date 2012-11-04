package com.example.samplearcamera.library;

import java.util.ArrayList;

import com.example.samplearcamera.R;
import com.example.samplearcamera.R.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;

public class OverlayView extends View {

	ArrayList<Marker> mMarkers = new ArrayList<Marker>();
	
	public static final int UPDATE_INTERVAL = 200;
	
	Bitmap mIcon;
	
	public OverlayView(Context context) {
		super(context);
		init(context);
	}

	public OverlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public OverlayView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public void init(Context context) {
		mIcon = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
	}

	public void addMarker(String id, double latitude,double longitude) {
		mMarkers.add(new Marker(id, mIcon,latitude,longitude));
	}
	
	public Marker findMarker(String id) {
		for (int i = 0; i < mMarkers.size(); i++) {
			Marker marker = mMarkers.get(i);
			if (marker.getId().equals(id)) {
				return marker;
			}
		}
		return null;
	}
	
	public boolean removeMarker(String id) {
		Marker marker = findMarker(id);
		if (marker != null) {
			mMarkers.remove(marker);
		}
		return false;
	}
	
	public void clear() {
		mMarkers.clear();
	}
	
	private Runnable updateRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			invalidate();
			postDelayed(updateRunnable, UPDATE_INTERVAL);
		}
	};
	
	public void startUpdateScreen() {
		post(updateRunnable);
	}
	
	public void stopUpdateScreen() {
		removeCallbacks(updateRunnable);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		ARCamera.getInstance().setRotationMatrix(ARSensorManager.getInstance().getRotationMatrix());
		if (Utility.getDisplayRotation() == Surface.ROTATION_90) {
			ARCamera.getInstance().setAsix(ARCamera.ASIX_X, ARCamera.ASIX_Z, ARCamera.ASIX_Y);
		} else {
			ARCamera.getInstance().setAsix(ARCamera.ASIX_Y, ARCamera.ASIX_Z, ARCamera.ASIX_X);
		}
		ARCamera.getInstance().setCameraSize(getMeasuredWidth(), getMeasuredHeight());
		for (int i = 0; i < mMarkers.size(); i++) {
			Marker marker = mMarkers.get(i);
			marker.draw(canvas);
		}
	}
}
