package com.magnitude.gui;

import java.util.Enumeration;
import java.util.Vector;

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
import com.magnitude.ARKitapi.ARSphericalView;
import com.magnitude.app.R;
import com.magnitude.libs.MagnitudePOI;

public class Radar extends MagnitudeStaticView{
	
	private int icon;
	private Bitmap image;
	private Resources res = getResources();
	private ARLayout ar; 
	
	// List of all POI on the radar
	private Vector<PoiRadar> Pois = new Vector<PoiRadar>();
	
	public Radar(Context ctx, ARLayout ar) {
		super(ctx);
		setAr(ar);
		setIcon(R.drawable.radar);
	}
	
	/**
	 * Update of the vector status. If a POI is visible, then we add it.
	 */
	public void update() {
		Enumeration<ARSphericalView> e = getAr().getArViews().elements();
		Pois = new Vector<PoiRadar>();
		while(e.hasMoreElements())
		{
			ARSphericalView view = e.nextElement();
			if(((MagnitudePOI)view).isVisibleDist()) {
				float alpha = view.getAzimuth()-ar.getDirection();
				if(alpha > 0) alpha = 360 - alpha;
				else alpha = -alpha ;
				Pois.add(new PoiRadar(getCtx(),this.getX()+this.getImage().getWidth()/2,this.getY()+this.getImage().getWidth()/2,alpha,(view.getDistance()*(this.getImage().getWidth()/2))/ar.getMaxDistance()));
			}
		}
	}
	
	public void updateLayout() {
		layout((int)getX(),(int)getY(),(int)getX()+getImage().getWidth(),(int)getY()+getImage().getHeight());	
	}
	
	@Override
	public void draw(Canvas c)
	{
		if(isVisible() && getImage() != null) {
			c.drawBitmap(getImage(), getLeft(), getTop(), getP());
			Enumeration<PoiRadar> e = Pois.elements();
			while(e.hasMoreElements())
			{
				PoiRadar view = e.nextElement();
				view.draw(c);
			}		
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
	
	private String mOrKm(float value) {
		String result;
		float tmp = value;
		if(value > 1000) {
			tmp /= 1000;
			result = new String(""+Math.round(tmp*100)/100+" km");
		}
		else result = new String(""+Math.round(tmp)+" m");
		return result;
	}
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
        	if(new Region(getLeft(),getTop(),getRight(),getBottom()).contains(
					(int)event.getX(),
					(int)event.getY())) {
				Toast.makeText(getCtx(), "Maximum distance : "+mOrKm(ar.getMaxDistance())+"\n"+Pois.size()+" Pois", Toast.LENGTH_SHORT).show();
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
