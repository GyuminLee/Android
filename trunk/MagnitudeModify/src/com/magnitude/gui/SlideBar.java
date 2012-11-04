package com.magnitude.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;

import com.magnitude.ARKitapi.MagnitudeStaticView;
import com.magnitude.ARKitapi.ARLayout;
import com.magnitude.app.R;

public class SlideBar extends MagnitudeStaticView{

	private int icon;
	private Bitmap image;
	
	private Resources res = getResources();
	private ARLayout ar; 
	private Cursor curseur;
	private boolean inSlide;
	
	// var used to increase/decrease the zoom
	private float currentAccuracy;
	
	// zoom max by slide is current*(1 + zoomFactore)
	private float zoomFactore = 1;
	
	// minimum dist for the filter (ar.MaxDistance > minDist)
	private final float minDist = 20;
	
	// max dist for the filter (ar.MaxDistance < maxDist)
	private final float maxDist = 500000;
	
	public SlideBar(Context ctx, ARLayout ar) {
		super(ctx);
		setAr(ar);
		setIcon(R.drawable.slide_shadow);
		this.curseur = new Cursor(ctx,R.drawable.slide_bg);
		inSlide = false;
	}
	
	public void updateLayout() {
		int yCorrection = 20;
		layout((int)getX(),(int)getY(),(int)getX()+getImage().getWidth(),(int)getY()+getImage().getHeight() - yCorrection);	
		this.curseur.setX(this.getX());
		this.curseur.setY(this.getY()-curseur.getImage().getHeight()/2 + this.image.getHeight()/2);
		this.curseur.updateLayout();
	}
	
	public void draw(Canvas c)
	{
		int Bordure = 6;
		curseur.draw(c,inSlide,ar.getMaxDistance(), new RectF(getX(), getY()+Bordure, getX()+image.getWidth(), getY()+image.getHeight()-Bordure));
		if(getImage() != null) {
			c.drawBitmap(getImage(), getLeft(), getTop(), getP());
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if(action == MotionEvent.ACTION_UP && inSlide) {
			inSlide = false;
        	this.curseur.setY(this.getY()-curseur.getImage().getHeight()/2 + this.image.getHeight()/2);
    		this.curseur.updateLayout();  	
        	return true;
		} else if(inSlide && action == MotionEvent.ACTION_MOVE) {
			float newY = event.getY();
			curseur.setY(newY-curseur.getImage().getHeight()/2);
			curseur.updateLayout();
			updateDistance(newY);      
			getAr().updatePoiDistance();
			return true;
		} else if(action == MotionEvent.ACTION_DOWN) {
			int ecart = 20;
        	if(new Region(curseur.getLeft()-ecart,(int)(this.getY()-ecart),curseur.getRight()+ecart,(int) (this.getY()+this.getImage().getHeight()+ecart)).contains(
					(int)event.getX(),
					(int)event.getY())) {
        		inSlide = true;
        		this.currentAccuracy = ar.getMaxDistance();
    			return true;      		
        	}
		}
        return false;
	}
	
	private void updateDistance(float y) {
		float position = Math.abs(y-(this.getTop() + image.getHeight()/2));
		float maxPosition;
		float result = currentAccuracy;
		if(y > (this.getTop() + image.getHeight()/2)) {
			maxPosition = getBottom()-(this.getTop() + image.getHeight()/2);
			result *= 1+(position*zoomFactore/maxPosition);
		} else {
			maxPosition = this.getTop();
			result /= 1+(position*zoomFactore/maxPosition);
		}
		if(result < minDist) result = minDist;
		else if(result > maxDist) result = maxDist;
		ar.setMaxDistance(result);
	}

	public int getIcon() {
		return icon;
	}
	
	public void setIcon(int icon) {
		this.icon = icon;
		this.image = BitmapFactory.decodeResource(res, icon);
	}
	
	public Bitmap getImage() {
		return image;
	}
	
	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	public Resources getRes() {
		return res;
	}
	
	public void setRes(Resources res) {
		this.res = res;
	}
	
	public ARLayout getAr() {
		return ar;
	}
	
	public void setAr(ARLayout ar) {
		this.ar = ar;
	}	
}
