package com.example.samplearcamera.library;

import android.location.Location;

public class ARCamera {
	private Location mViewLocation;
	private ARMatrix mRoationMatrix = new ARMatrix();
	private int mWidth;
	private int mHeight;
	private float mAngleX;
	private float mAngleY;
	private float mDistanceX;
	private float mDistanceY;
	private ARVector tempVector = new ARVector();
	private int[] mAsix;
	
	public static final int ASIX_X = ARVector.AXIS_X;
	public static final int ASIX_Y = ARVector.AXIS_Y;
	public static final int ASIX_Z = ARVector.AXIS_Z;
	
	private static ARCamera instance;
	public static ARCamera getInstance() {
		if (instance == null) {
			instance = new ARCamera();
		}
		return instance;
	}
	private ARCamera() {
		mWidth = 0;
		mHeight = 0;
		mDistanceX = 0;
		mDistanceY = 0;
		mAngleX = 45f;
		mAngleY = 45f;
		mAsix = new int[3];
	}
	
	public void setCameraSize(int width,int height) {
		setAngle(width,height,mAngleX,mAngleY);
	}
	
	public void setAngle(float angleX,float angleY) {
		setAngle(mWidth,mHeight,angleX,angleY);
	}
	
	public void setAngle(int width,int height,float angleX, float angleY) {
		mWidth = width;
		mHeight = height;
		mAngleX = angleX;
		mAngleY = angleY;
		float radianAngle = (float)Math.toRadians(mAngleX);
		mDistanceX = (float)((mWidth/2) * Math.tan(radianAngle / 2));
		radianAngle = (float)Math.toRadians(mAngleY);
		mDistanceY = (float)((mHeight/2) * Math.tan(radianAngle / 2));
	}
	
	public void setViewLocation(Location location) {
		mViewLocation = location;
	}
	
	public void setRotationMatrix(ARMatrix matrix) {
		mRoationMatrix.set(matrix);
	}
	
	public void setAsix(int asix_x, int asix_y, int asix_z) {
		mAsix[0] = asix_x;
		mAsix[1] = asix_y;
		mAsix[2] = asix_z;
	}
	
	public void computeViewPosition(Location location, ARVector outPosition) {
		if (location.getAltitude() == 0) {
			location.setAltitude(mViewLocation.getAltitude());
		}
		
		tempVector.set(location, mViewLocation, mAsix);
		//tempVector.set(100,0,100);
		
		tempVector.multiple(mRoationMatrix);
		
		float x,y,z;
		x = mDistanceX * tempVector.getX() / - tempVector.getZ();
		y = mDistanceY * tempVector.getY() / - tempVector.getZ();
		z = tempVector.getZ();
		x = x + mWidth / 2;
		y = -y + mHeight / 2;
		outPosition.set(x,y,z);
	}
	
	public float getDistance(Location location) {
		return mViewLocation.distanceTo(location);
	}
	
	public float getBearing(Location location) {
		return mViewLocation.bearingTo(location);
	}
	
	
}
