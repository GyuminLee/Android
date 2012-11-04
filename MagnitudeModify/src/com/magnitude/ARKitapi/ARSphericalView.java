package com.magnitude.ARKitapi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Location;
import android.view.View;

/**
 * Class representing a POI in  orginal ARKit
 * @author Chris Haseman -  modified by Magnitude Client
 */
public class ARSphericalView extends View  implements Comparable<ARSphericalView>{
	protected volatile float azimuth; // Angle from north
	private volatile float distance; // Distance to object
	private volatile float inclination = -1; // angle off horizon.
	protected volatile Location location;

	private volatile int x;
	private volatile int y;
	private volatile boolean visible = false;

	public static Location deviceLocation;
	// used to compute inclination
	public static float currentAltitude = 0;
	protected static Context ctx;
	private Paint p = new Paint();

	public ARSphericalView(Context ctx) {
		super(ctx);
		p.setAntiAlias(true);
	}

	public ARSphericalView(Context ctx, Location deviceLocation,
			Location objectLocation) {
		super(ctx);
		p.setAntiAlias(true);
		if (deviceLocation != null) {
			azimuth = deviceLocation.bearingTo(objectLocation);
			distance = deviceLocation.distanceTo(objectLocation);
			if (deviceLocation.hasAccuracy() && objectLocation.hasAltitude()) {
				double opposite;
				boolean neg = false;
				if (objectLocation.getAltitude() > deviceLocation.getAltitude()) {
					opposite = objectLocation.getAltitude()
							- deviceLocation.getAltitude();
				} else {
					opposite = deviceLocation.getAltitude()
							- objectLocation.getAltitude();
					neg = true;
				}
				setInclination((float) Math
						.atan(((double) opposite / getDistance())));
				if (neg)
					setInclination(getInclination() * -1);
			}
		}
	}

	public void draw(Canvas c) {
	}

	public void setAzimuth(float azimuth) {
		this.azimuth = azimuth;
	}

	public float getAzimuth() {
		return azimuth;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDistance() {
		return distance;
	}

	public void setInclination(float d) {
		this.inclination = d;
	}

	public float getInclination() {
		return inclination;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	protected void setP(Paint p) {
		this.p = p;
	}

	protected Paint getP() {
		return p;
	}

	public static void setCtx(Context ctx) {
		ARSphericalView.ctx = ctx;
	}

	protected Context getCtx() {
		return ctx;
	}
	
	public int compareTo(ARSphericalView other) { 
	      float nombre1 = other.getDistance(); 
	      float nombre2 = this.getDistance(); 
	      if (nombre1 > nombre2)  return -1; 
	      else if(nombre1 == nombre2) return 0; 
	      else return 1; 
	   }
}
