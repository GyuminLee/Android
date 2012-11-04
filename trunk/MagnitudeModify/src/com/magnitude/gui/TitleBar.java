package com.magnitude.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Region;
import android.view.MotionEvent;
import android.widget.Toast;

import com.magnitude.ARKitapi.MagnitudeStaticView;
import com.magnitude.ARKitapi.ARLayout;
import com.magnitude.app.R;

public class TitleBar extends MagnitudeStaticView
{
	
	private int icon;
	private Bitmap image;
	private Resources res;
	private boolean showTitleBar = true;
	private String copyright = "Magnitude v1.0";
	
	public TitleBar(Context ctx, ARLayout ar) {
		super(ctx);
		res  = getResources();
		setIcon(R.drawable.titlebar);
	}
	
	@Override
	public void draw(Canvas c)
	{
		if(showTitleBar){
			c.drawBitmap(getImage(), getLeft(), getTop(), getP());
		}
	}
	
	public void updateLayout() {
		layout((int)getX(),(int)getY(),(int)getX()+getImage().getWidth(),(int)getY()+getImage().getHeight());	
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
			if(new Region(getLeft(),getTop(),getRight(),getBottom()).contains(
					(int)event.getX(),
					(int)event.getY())) {
				Toast.makeText(getCtx(),copyright,Toast.LENGTH_SHORT).show();
				return true;
			}
        }
       return false;
	}
	
}
