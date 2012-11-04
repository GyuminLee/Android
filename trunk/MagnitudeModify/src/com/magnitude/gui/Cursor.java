package com.magnitude.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

import com.magnitude.ARKitapi.MagnitudeStaticView;

/**
 * Cursor is used to draw the cursor (what you can see moving) for the slidebar.
 * @author Magnitude Client
 */
public class Cursor extends MagnitudeStaticView{

	/**
	 * Resource's reference to the image
	 */
	private int icon;

	/**
	 * The image
	 */
	private Bitmap image;
	
	/**
	 * To be able to access the resources
	 */
	private Resources res;
	
	/**
	 * Constructor. 
	 * @param ctx The current application Context.
	 * @param icon The reference to the icon in the resources
	 */
	public Cursor(Context ctx, int icon) {
		super(ctx);
		res = getResources();
		setIcon(icon);
		getP().setAntiAlias(true);
	}
	
	/**
	 * Update the layout dimension
	 */
	public void updateLayout() {
		layout((int)getX(),(int)getY(),(int)getX()+getImage().getWidth(),(int)getY()+getImage().getHeight());	
	}
	
	/**
	 * Return the actual icon integer reference
	 * @return icon
	 */
	public int getIcon() {
		return icon;
	}
	
	/**
	 * Set the icon, the image is updated automatically 
	 * @param icon new icon
	 */
	public void setIcon(int icon) {
		this.icon = icon;
		image = BitmapFactory.decodeResource(res, icon);
	}
	
	/**
	 * Return the actual bitmap associated to the cursor
	 * @return image
	 */
	public Bitmap getImage() {
		return image;
	}
	
	


	/**
	 * Draw the cursor on screen. The tricky math modification is because we use a 3*screen height image. According to the position the part of the cursor to draw under the slide bar shadow changed.
	 * @param c the canvas
	 * @param inSlide used to draw a small box with actual value of the distance filter
	 * @param distance if inSlide, it's the value to draw
	 * @param rect The rectangle where to draw
	 */
	public void draw(Canvas c,boolean inSlide,float distance, RectF rect)
	{
		if(isVisible()) {
			Rect rectSRC;
			RectF rectDEST = rect;
			rectSRC = new Rect(0,(int) Math.abs(this.getTop() - rect.top) ,this.image.getWidth(),(int) ((int) Math.abs(this.getTop() - rect.top)+rect.height()));
			c.drawBitmap(getImage(), rectSRC, rectDEST, getP());		
			if(inSlide) {
				float bordureX = 5;
				float bordureY = 5;
				float fixY = -6;
				Rect textBox = new Rect();
				String dist = mOrKm(distance);
				getP().getTextBounds(dist, 0, dist.length(), textBox);
				
				getP().setColor(Color.BLACK);
				getP().setAlpha(100);
				c.drawRect(getLeft()-bordureX*2-textBox.width(),
						rect.top + (rect.bottom - rect.top)/2 - bordureY + fixY,
						getLeft()-2,
						rect.top + (rect.bottom - rect.top)/2 + textBox.height()+ bordureY + fixY, getP());
							
				getP().setColor(Color.WHITE);
				getP().setAlpha(255);
				c.drawText(dist, getLeft()-textBox.width()-5,
						rect.top + (rect.bottom - rect.top)/2 + textBox.height() + fixY, getP());
			}
		}
	}
	/**
	 * Convert a meter value to an equivalent string. Also make the conversion in km to reduce the size of the string
	 * @param value the value (in meter) to convert
	 * @return a string of distance (in km or m)
	 */
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
	
}


