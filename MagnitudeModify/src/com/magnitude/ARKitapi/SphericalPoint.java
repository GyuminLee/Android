package com.magnitude.ARKitapi;

/**
 * Spherical Point
 * @author Chris Haseman
 *
 */
public class SphericalPoint {
	
	private float azimuth; // Angle from north
	private float distance; // Distance to object
	private float inclination; // angle off horizon.

	// used to compute inclination
	public static float currentAltitude = 0;

	public SphericalPoint(float angleFromNorth, float distance, float altitude) {
		float opposite;
		this.setDistance(distance);
		// arctan of opposite/adjacent
		
		boolean neg = false;
		if (altitude > currentAltitude) {
			opposite = altitude - currentAltitude;
		} else {
			opposite = currentAltitude - altitude;
			neg = true;
		}
		setInclination((float) Math.atan(((double) opposite / distance)));
		if (neg)
			opposite = opposite * -1;
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

	public void setInclination(float inclination) {
		this.inclination = inclination;
	}

	public float getInclination() {
		return inclination;
	}

}
