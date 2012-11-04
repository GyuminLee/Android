package com.magnitude.ARKitapi;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * @author Magnitude Client
 * Abstract class of static items on screen, but that can show
 * several images (like animated loading gps signal).
 */
public class MagnitudeAnimatedView extends MagnitudeStaticView{
	
	private ArrayList<Bitmap> anim;
	private Resources res;
	private int currentFrame;
	private int maxFrame;
	Timer t;
	AnimationManager manager;
	
	public MagnitudeAnimatedView(Context context, long period) {
		super(context);
		anim = new ArrayList<Bitmap>();
		this.currentFrame = 0;
		this.maxFrame = 0;
		res = context.getResources();
		manager = new  AnimationManager();
		t = new Timer();
		t.scheduleAtFixedRate(manager, 50, period);
	}

	public void draw(Canvas c) {	
		if(maxFrame != 0) c.drawBitmap(anim.get(currentFrame), getX(), getY(), getP());
	}
		
	public void addFrame(int val) {
		Bitmap image = BitmapFactory.decodeResource(res, val);
		anim.add(image);		
		maxFrame += 1;
	}

	public Bitmap getAnim() {
		return anim.get(currentFrame);
	}	
	
	class AnimationManager extends TimerTask {
		 AnimationManager() {
		}

		public void run() {
			currentFrame += 1;
			if(currentFrame == maxFrame) currentFrame = 0;
		}
	}
}