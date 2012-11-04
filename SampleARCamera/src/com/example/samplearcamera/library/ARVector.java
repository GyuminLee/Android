package com.example.samplearcamera.library;

import android.location.Location;
import android.opengl.Matrix;

public class ARVector {
	float[] values = new float[4];
	float[] tempValue = new float[4];
	float[] tmp = new float[1];
	
	public static final int AXIS_X = 0;
	public static final int AXIS_Y = 1;
	public static final int AXIS_Z = 2;
	
	public ARVector() {
		values[0] = 0.0f;
		values[1] = 0.0f;
		values[2] = 0.0f;
		values[3] = 1.0f;
	}
	
	public ARVector(float x, float y, float z) {
		set(x,y,z);
	}
	
	public ARVector(Location location, Location viewLocation, int[] asix) {
		set(location,viewLocation,asix);
	}
	
	public float getX() {
		return values[0];
	}
	
	public float getY() {
		return values[1];
	}
	
	public float getZ() {
		return values[2];
	}
	
	public void set(float x, float y, float z) {
		values[0] = x;
		values[1] = y;
		values[2] = z;
		values[3] = 1.0f;
	}
	
	public void set(Location location, Location viewLocation, int[] asix) {
		// Longitude is x axis
		int x = asix[0];
		int y = asix[1];
		int z = asix[2];
		Location.distanceBetween(viewLocation.getLatitude(), viewLocation.getLongitude(), 
				viewLocation.getLatitude(), location.getLongitude(), tmp);
		if (viewLocation.getLongitude() < location.getLongitude()) {
			values[x] = tmp[0];
		} else {
			values[x] = -tmp[0];
		}
		
		// Latitude is y axis
		Location.distanceBetween(viewLocation.getLatitude(), viewLocation.getLongitude(), 
				location.getLatitude(), viewLocation.getLongitude(), tmp);
		if (viewLocation.getLatitude() < location.getLatitude()) {
			values[y] = -tmp[0];
		} else {
			values[y] = tmp[0];
		}
		
		// Altitude is z axis
		values[z] = (float)(viewLocation.getAltitude() - location.getAltitude());		
		
		values[3] = 1.0f;
	}
	
	public void set(float[] v) {
		if (v.length == 3) {
			set(v[0],v[1],v[2]);
		} else if (v.length == 4) {
			System.arraycopy(v, 0, values, 0, v.length);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public void add(ARVector v) {
		values[0] += v.values[0];
		values[1] += v.values[1];
		values[2] += v.values[2];
	}
	
	public void sub(ARVector v) {
		values[0] -= v.values[0];
		values[1] -= v.values[1];
		values[2] -= v.values[2];
	}
	
	public void multiple(ARMatrix m) {
		System.arraycopy(values, 0, tempValue, 0, values.length);
		Matrix.multiplyMV(values, 0, m.values, 0, tempValue, 0);
	}
}
