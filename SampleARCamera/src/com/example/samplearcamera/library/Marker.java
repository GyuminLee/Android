package com.example.samplearcamera.library;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;

public class Marker extends Drawable {

	private String mId;
	private Bitmap mIcon;
	private Location mPosition;
	private float mDistance;
	private float mBearing;
	private ARVector mVector = new ARVector();

	private static Paint sPaint = new Paint();
	private Paint mPaint = sPaint;
		
	private static final String mProvider = "poiprovider";

	public Marker(String id,Bitmap icon) {
		mId = id;
		mIcon = icon;
	}
	
	public Marker(String id,Bitmap icon,Location position) {
		init(id,icon,position);
	}
	
	public Marker(String id, Bitmap icon,double latitude, double longitude) {
		this(id, icon,latitude,longitude,0.0);
	}
	
	public Marker(String id, Bitmap icon,double latitude, double longitude, double altitude) {
		Location location = new Location(mProvider);
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		location.setAltitude(altitude);
		init(id, icon,location);
	}
	
	private void init(String id, Bitmap icon,Location position) {
		mId = id;
		mIcon = icon;
		mPosition = position;
	}
	
	public String getId() {
		return mId;
	}
	
	public void setPaint(Paint paint) {
		mPaint = paint;
	}
	
	public void setBitmap(Bitmap icon) {
		mIcon = icon;
	}
	
	public Bitmap getBitmap() {
		return mIcon;
	}
	
	public void setPosition(Location position) {
		mPosition = position;
	}
	
	public Location getPosition() {
		return mPosition;
	}
	
	public void calViewPosition() {
		ARCamera.getInstance().computeViewPosition(mPosition, mVector);
	}
	
	public float getDistance() {
		return ARCamera.getInstance().getDistance(mPosition);
	}
	
	public float getBearing() {
		return ARCamera.getInstance().getBearing(mPosition);
	}

	@Override
	public void draw(Canvas canvas) {
		calViewPosition();
		if (mVector.getZ() < -1f) {
			canvas.drawBitmap(mIcon, mVector.getX(), mVector.getY(), mPaint);
		}
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub

	}

}
