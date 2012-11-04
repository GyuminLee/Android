package com.magnitude.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.magnitude.ARKitapi.MagnitudeAnimatedView;
import com.magnitude.ARKitapi.MagnitudeStaticView;
import com.magnitude.app.R;


public class SplashScreen extends MagnitudeStaticView{
	
	private Resources res;
	private float screenHeight;
	private float screenWidth;
	private MagnitudeAnimatedView anim;
	
	public SplashScreen(Context ctx, float scrWidth, float scrHeight) {
		super(ctx, true);
		res = getResources();
		this.screenHeight = scrHeight;
		this.screenWidth = scrWidth;
		anim = new MagnitudeAnimatedView(ctx, 1000);
		anim.setX((int)(screenWidth/2) - 20);
		anim.setY((int)(screenHeight/2 - 40));
		anim.addFrame(R.drawable.gps_locator1);
		anim.addFrame(R.drawable.gps_locator2);
		anim.addFrame(R.drawable.gps_locator3);
	}
	
	/**
	 * Update the layout dimension
	 */
	public void updateLayout() {
		layout((int) getX(), (int) getY(),
				(int) (getX() + getScreenWidth()), 
				(int) (getY() + getScreenHeight()));
	}

	/**
	 * Draw the icon or the black screen.
	 */
	public void draw(Canvas c) {
		int logo = R.drawable.magnitude_logo;
		Bitmap imageLogo = BitmapFactory.decodeResource(res, logo);

		getP().setColor(Color.BLACK);
		getP().setAlpha(190);
		c.drawRect(new Rect(0, 0, (int) getScreenWidth(),
				(int) getScreenHeight()), getP());
		getP().setAlpha(255);
		getP().setColor(Color.WHITE);
		getP().setAntiAlias(true);
		c.drawText("Waiting for GPS signal...", screenWidth / 2 - 50,
				screenHeight / 2 + 40, getP());
		c.drawBitmap(imageLogo, 5, screenHeight - imageLogo.getHeight()
				- 5, getP());
		anim.draw(c);
	}
	
	/**
	 * Event on touch. Notify if it's in the view (return true) or not (return
	 * false). Also toast with the gps status.
	 */
	public boolean onTouchEvent(MotionEvent event) {
				return true;
	}

	public float getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(float screenHeight) {
		this.screenHeight = screenHeight;
	}

	public float getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(float screenWidth) {
		this.screenWidth = screenWidth;
	}

}
