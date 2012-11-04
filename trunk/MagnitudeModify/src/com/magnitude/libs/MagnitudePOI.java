package com.magnitude.libs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Region;
import android.location.Location;
import android.view.MotionEvent;

import com.magnitude.ARKitapi.ARSphericalView;
import com.magnitude.app.MagnitudeActivity;
import com.magnitude.app.R;
import com.magnitude.gui.JsonPopup;

/**
 * A class representing a Point of Interest.
 * @author Magnitude_client
 *
 */
public class MagnitudePOI extends ARSphericalView {

	private String name;
	private String plugin;
	private String url;
	private int icon;
	private int iconSave;
	private Bitmap imageScaleFree;
	private Bitmap image;
	private Resources res;
	private Rect textBox;
	private boolean visibleDist;
	public float scale = 1;
	private int viewWidth;
	private int viewHeight;
	private boolean isShifted;
	private boolean inSlide;
	private float initialX = 0;
	private float initialY = 0;
	private float shiftX = 0;
	private float shiftY = 0;
	
	/**
	 * bodureX : Used like a padding in onDraw
	 */
	private int bordureX = 7;
	
	/**
	 * bodureY : Used like a padding in onDraw
	 */
	private int bordureY = 7;
	
	/**
	 * X coordinate of the center of the bubble
	 */
	float BubbleX = 0;
	
	/**
	 * Y coordinate of the center of the bubble
	 */
	float BubbleY = 0;

	private String intent;
	private JsonPopup popup;

	private float timeOfStart;
	private MagnitudeActivity activity; 
	
	public MagnitudePOI(Context ctx, MagnitudeActivity activity,PoiAttributes poia) {
		super(ctx);
		res = getResources();
		this.setName(poia.getName());
		this.setLocation(new Location(name));
		this.getLocation().setLatitude(poia.getLatitude());
		this.getLocation().setLongitude(poia.getLongitude());
		this.getLocation().setAltitude(poia.getAltitude());
		if(poia.getIcon() != 0) this.setIcon(poia.getIcon());
		else this.setIcon(R.drawable.bubble_green);
		this.intent = poia.getIntent();
		this.setVisibleDist(true);
		this.setScale(1);
		this.optimizeViewSize();
		this.activity = activity;
		this.isShifted = false;
		this.inSlide = false;
		if(poia.getPopUp() != null && poia.getPopUp().length() > 7) {
			this.setUrl(poia.getPopUp());
			this.setPopup(R.layout.popup, 250, 260,poia.getPopUp());
		}
	}

	public MagnitudePOI(Context ctx, String name, double latitude,
			double longitude, double altitude, double azimuth, double incli,
			int icon) {
		super(ctx);
		setInclination((float) incli);
		this.setName(name);
		this.setLocation(new Location(name));
		this.getLocation().setLatitude(latitude);
		this.getLocation().setLongitude(longitude);
		this.getLocation().setAltitude(altitude);
		this.setAzimuth((float) azimuth);
		this.setIcon(icon);
		this.setVisibleDist(true);
		setScale(1);
		this.isShifted = false;
		this.inSlide = false;
		this.optimizeViewSize();
	}

	public void draw(Canvas c) {
		if (isVisible() && isVisibleDist()) {
			String distanceStr = new String(mOrKm(getDistance()));
			
			Rect tmpBox = new Rect();
			Rect tmpBoxDistance = new Rect();
			getP().getTextBounds(name, 0, name.length(), tmpBox);
			getP().getTextBounds(distanceStr, 0, distanceStr.length(), tmpBoxDistance);
			
			float difPixel = tmpBoxDistance.width() - tmpBox.width();
			if(difPixel < 0) difPixel = 0;
			
			float X = getLeft();
			float Y = getTop();
			
			getP().setColor(Color.WHITE);
			setInitialX(X - this.getShiftX());
			setInitialY(Y - this.getShiftY());			
			
			getP().setColor(Color.BLACK);
			if (name != null && getImage() != null) {
				// Pic is smaller than the text
				if (getImage().getWidth() <= textBox.width()) {
										
					getP().setAlpha(100);
					c.drawRect(X - bordureX, Y - bordureY,
							X + textBox.width() + bordureX + difPixel,
							Y + textBox.height() * 2 + bordureY,
							getP());
					getP().setColor(Color.WHITE);
					getP().setAlpha(255);
					c.drawText(name, X,
							Y + textBox.height(), getP());
					c.drawText(mOrKm(getDistance()), X, Y
							+ textBox.height() * 2 + 2, getP());
					
					BubbleX = X + textBox.width()/2 - getImage().getWidth()/2;
					BubbleY = Y + textBox.height()*2 + bordureY + 1;
					
				} else {
					// Pic is bigger than the text

					// draw the box
					getP().setAlpha(100);
					c.drawRect(X - bordureX - textBox.width() / 2
							+ getImage().getWidth() / 2, Y
							- bordureY, X + bordureX + difPixel +
							+ textBox.width() / 2 + getImage().getWidth()
							/ 2,
							Y + textBox.height() * 2 + bordureY,
							getP());
					getP().setColor(Color.WHITE);
					getP().setAlpha(255);
					c.drawText(name, X - textBox.width() / 2
							+ getImage().getWidth() / 2, Y
							+ textBox.height(), getP());
					c.drawText(mOrKm(getDistance()), X
							- textBox.width() / 2 + getImage().getWidth()
							/ 2, Y + textBox.height() * 2 + 2,
							getP());
					
					BubbleX = X;
					BubbleY = Y + textBox.height()*2 + bordureY;
					
				}
			} else {
				if (getImage() != null)
					BubbleX = getLeft();
					BubbleY = getTop();
			}
			c.drawBitmap(getImage(), BubbleX, BubbleY, getP());
		}
	}

	public void drawLine(Canvas c) {
		if(this.isShifted()) {
			c.drawLine(this.getInitialX() + getImage().getWidth()/2,
					this.getInitialY() + getImage().getHeight()/2 - 1, 
					BubbleX+getImage().getWidth()/2,
					BubbleY+getImage().getHeight()/2, getP());
			c.drawLine(this.getInitialX() + getImage().getWidth()/2,
						this.getInitialY() + getImage().getHeight()/2 + 1, 
						BubbleX+getImage().getWidth()/2,
						BubbleY+getImage().getHeight()/2, getP());
			c.drawCircle(this.getInitialX() + getImage().getWidth()/2,
					this.getInitialY() + getImage().getHeight()/2, 4, getP());
			getP().setColor(Color.BLACK);
			c.drawCircle(this.getInitialX() + getImage().getWidth()/2,
					this.getInitialY() + getImage().getHeight()/2, 2, getP());
			c.drawLine(this.getInitialX() + getImage().getWidth()/2,
					this.getInitialY() + getImage().getHeight()/2, 
					BubbleX+getImage().getWidth()/2,
					BubbleY+getImage().getHeight()/2, getP());
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		long time = event.getEventTime();
		if(action == MotionEvent.ACTION_DOWN && !inSlide) {
			if((new Region(getLeft(), getTop(), getRight(), getBottom()))
					.contains((int) event.getX(),(int) event.getY())) {
				inSlide = true;
				iconSave = getIcon();
				timeOfStart = time;
				return true;
			}
		} else if(inSlide && action == MotionEvent.ACTION_UP && (time - timeOfStart) < 300) {
				showPopup();
				inSlide = false;
				this.setIcon(iconSave);
				return true;
		} else if(inSlide && action == MotionEvent.ACTION_UP) {
				inSlide = false;
				this.setIcon(iconSave);
				return true;
		}else if(inSlide && action == MotionEvent.ACTION_MOVE && (time - timeOfStart) > 300) {
			this.movePoi((float)event.getX(), (float)event.getY());
			this.setIcon(R.drawable.bubble_white);
			return true;
		}	
	return false;
	}

	public void showPopup() {
		if(popup != null) {
			popup.setDistance(mOrKm(getDistance()));
			popup.show(10,30);
		}
	}
	
	private String mOrKm(float value) {
		String result;
		float tmp = value;
		if (value > 1000) {
			tmp /= 1000;
			result = new String("" + Math.round(tmp * 100) / 100 + " km");
		} else
			result = new String("" + Math.round(tmp) + " m");
		return result;
	}

	public void optimizeViewSize() {
		int tmp_x2 = 0;
		int tmp_y2 = 0;
		if (image != null) {
			tmp_x2 = image.getWidth();
			tmp_y2 += image.getHeight();
		}
		if (name != null) {
			if (image == null || textBox.width() > image.getWidth())
				tmp_x2 = textBox.width();
			tmp_y2 += textBox.height();
		}
		viewWidth = tmp_x2;
		viewHeight = tmp_y2;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public int getViewHeight() {
		return viewHeight;
	}
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	public void setIcon(Integer icon) {
		this.icon = icon;
		if (icon == null) {
			this.imageScaleFree = null;
		} else {
			this.imageScaleFree = BitmapFactory.decodeResource(res, icon);
			setImage(Bitmap.createScaledBitmap(this.getImageScaleFree(), (int)(this.getImageScaleFree().getWidth()*getScale()), (int)(this.getImageScaleFree().getHeight()*getScale()), false));
		}
		optimizeViewSize();
	}

	public int getIcon() {
		return this.icon;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		textBox = new Rect();
		getP().getTextBounds(name, 0, name.length(), textBox);
		optimizeViewSize();
	}

	public int getRectW() {
		return textBox.width();
	}

	public void setVisibleDist(boolean visibleDist) {
		this.visibleDist = visibleDist;
	}

	public boolean isVisibleDist() {
		return visibleDist;
	}

	public void setScale(float scale) {
		if(this.scale != scale) {
			this.scale = scale;
			setImage(Bitmap.createScaledBitmap(this.getImageScaleFree(), (int)(this.getImageScaleFree().getWidth()*getScale()), (int)(this.getImageScaleFree().getHeight()*getScale()), false));	
		}
	}

	public float getScale() {
		return scale;
	}
	
	public void setPopup(int layout, int height, int width , String urlSource) {
		popup = new JsonPopup(activity,this,layout,height,width);
     	popup.setUrl(urlSource);
	}

	public void setImageScaleFree(Bitmap imageScale) {
		this.imageScaleFree = imageScale;
	}

	public Bitmap getImageScaleFree() {
		return imageScaleFree;
	}
	
	public PoiAttributes extractParcelable() {
		PoiAttributes poia = new PoiAttributes(name, intent, getLocation().getAltitude(),
				getLocation().getLongitude(), getLocation().getLatitude(),
				getIcon());
		poia.setPopUp(getUrl());
		return poia;
	}
	@Override
	public String toString() {
		return (this.getLocation().getLatitude() + "," + this.getLocation().getLongitude());
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getPlugin() {
		return plugin;
	}
	
	public void movePoi(float goX, float goY) {
		this.setShifted(true);
		this.setShiftX(goX - this.getInitialX() - this.getWidth());
		this.setShiftY(goY - this.getInitialY() - this.getHeight());
	}
	
	
	public boolean isShifted() {
		return isShifted;
	}

	public void setShifted(boolean isShifted) {
		this.isShifted = isShifted;
	}

	public float getInitialX() {
		return initialX;
	}

	public void setInitialX(float initialX) {
		this.initialX = initialX;
	}

	public float getInitialY() {
		return initialY;
	}

	public void setInitialY(float initialY) {
		this.initialY = initialY;
	}

	public float getShiftX() {
		return shiftX;
	}

	public void setShiftX(float shiftX) {
		this.shiftX = shiftX;
	}

	public float getShiftY() {
		return shiftY;
	}

	public void setShiftY(float shiftY) {
		this.shiftY = shiftY;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}


