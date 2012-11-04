package com.magnitude.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.magnitude.ARKitapi.MagnitudeStaticView;

public class PoiRadar extends MagnitudeStaticView{
	
	private volatile float azimuth; 	// Angle from north
	private volatile float distance; 	// Distance to object
	private int radius = 1; 			// to draw the circle
	private float centerX;
	private float centerY;
	
	public PoiRadar(Context ctx) {
		super(ctx);
		this.setAzimuth(0);
		this.setDistance(0);
	}
	
	public PoiRadar(Context ctx, float centerX, float centerY, float azi, float distance) {
		super(ctx);
		this.setAzimuth(azi);
		this.setDistance(distance);
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.setVisible(true);
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
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

	public void updateLayout() {
		layout((int)getX(),(int)getY(),(int)getX()+radius,(int)getY()+radius);	
	}
	
	@Override
	public void draw(Canvas c)
	{
		if(isVisible()) {
			getP().setColor(Color.YELLOW);
			c.drawCircle(this.getCenterX() - (float) (this.getDistance()*(float)Math.sin(this.getAzimuth()*Math.PI/180)),this.getCenterY() - (float) (this.getDistance()*Math.cos(this.getAzimuth()*Math.PI/180)), radius, getP());
		}
	}
}

