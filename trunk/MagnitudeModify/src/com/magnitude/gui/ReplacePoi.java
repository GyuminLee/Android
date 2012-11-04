package com.magnitude.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Region;
import android.view.MotionEvent;

import com.magnitude.ARKitapi.MagnitudeStaticView;
import com.magnitude.ARKitapi.ARLayout;
import com.magnitude.app.R;

public class ReplacePoi extends MagnitudeStaticView{
	
	private int icon;
	private Bitmap image;
	private Resources res = getResources();
	private ARLayout ar; 
	
	public ReplacePoi(Context ctx, ARLayout ar) {
		super(ctx);
		setAr(ar);
		setIcon(R.drawable.refresh);
	}
		
	public void updateLayout() {
		layout((int)getX(),(int)getY(),(int)getX()+getImage().getWidth(),(int)getY()+getImage().getHeight());	
	}
	
	@Override
	public void draw(Canvas c)
	{
		if(isVisible() && getImage() != null) {
			c.drawBitmap(getImage(), getLeft(), getTop(), getP());	
		}
	}


	public int getIcon() {
		return icon;
	}
	
	/**
	 * Set the icon, the image is updated automatically 
	 * @param icone
	 */
	public void setIcon(int icon) {
		this.icon = icon;
		image = BitmapFactory.decodeResource(res, icon);
	}
	
	public Bitmap getImage() {
		return image;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
        	if((new Region(getLeft(), getTop(), getRight(), getBottom()))
					.contains((int) event.getX(),(int) event.getY())) {
	      		getAr().replaceAllPoi();
        		return true;
        	}
        }
       return false;
	}

	public void setAr(ARLayout ar) {
		this.ar = ar;
	}

	public ARLayout getAr() {
		return ar;
	}
}
