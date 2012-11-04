package com.magnitude.ARKitapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.magnitude.gui.GpsIndicator;
import com.magnitude.gui.Radar;
import com.magnitude.gui.ReplacePoi;
import com.magnitude.gui.SlideBar;
import com.magnitude.gui.SplashScreen;
import com.magnitude.gui.TitleBar;
import com.magnitude.libs.MagnitudePOI;

/**
 * ARLayout class implements all the calculation to show
 * POIs correctly on the screen. This should not be modified
 * if you only plan to develop plugins
 * @author Chris Haseman - modified by Magnitude Client.
 *
 */
public class ARLayout extends View implements LocationListener, SensorEventListener
{
	private final float xAngleWidth = 50f;
	private final float yAngleWidth = 19f;

	private float screenWidth;
	private float screenHeight;
	
	private volatile Vector<ARSphericalView> arViews = new Vector<ARSphericalView>();
	public synchronized Vector<ARSphericalView> getArViews() {
		synchronized (arViews) {
		return arViews;
		}
	}

	private SharedPreferences sp;
	private SensorManager sensorMan;
	private LocationManager locMan;
	private Location curLocation = null;
	private Context ctx;
	private float direction = (float) 0;
	private double inclination;
	private double rollingX = (float)0;
	private double rollingZ = (float)0;
	private float kFilteringFactor = (float)0.045;
	private float one = (float)0;
	private float two = (float)0;
	private float three = (float)0;
	private boolean locationChanged = false;
	private boolean DEBUG = false;
	private boolean GPS = true;
	private boolean RADAR = true;
	private boolean SCROLL = true;
	private boolean TITLEBAR = true;
	private boolean SPLASH = true;
	
	private Resources res = getResources();
	private Bitmap image;
	
	private GpsIndicator gpsIcon;
	private SlideBar rightSlide;
	private Radar radar;
	private TitleBar titleBar;
	private SplashScreen splash;
	private ReplacePoi poiReinit;
	private boolean poiMove = false;
	
	private float curLeftArm = -1;
	private float curRightArm = -1;
	private float curUpperArm = -1;
	private float curLowerArm = -1;

	private ArrayList<String> toucheableAction;
	private long timeOfStart;
	private Activity main;
	private boolean inClick;
	
	private float maxDistance = 1000;
	
	private float tmpPA,tmpPB,pseudoDirectionA,pseudoDirectionB;
	
	public ARLayout(Context context, Activity main)
	{
		super(context);
		ctx = context;
		updateSettings();
		initSensors();
		initArLayout();
		initGui();
		this.main = main;
	}
	
	public void onLocationChanged(Location location)
	{
		if(getCurLocation() == null) {
			ARSphericalView.deviceLocation = location;
		}
		locationChanged = true;
		setCurLocation(location);
		gpsIcon.update(getCurLocation().getAccuracy(),location.getAccuracy());
		//updateLayouts(0, 0, new Location("test"));
		updatePoiDistance();		
		postInvalidate();
	}
		
	
	public GpsIndicator getGpsIcon() {
		return gpsIcon;
	}

	public void setGpsIcon(GpsIndicator gpsIcon) {
		this.gpsIcon = gpsIcon;
	}

	public boolean onTouchEvent(MotionEvent event) {
		boolean eventCatch = false;
		long time = event.getEventTime();
		eventCatch = evaluateTouch(event);
		if(!eventCatch) {
			Vector<ARSphericalView> arViewReverse = new Vector<ARSphericalView>(getArViews());
			Collections.sort(arViewReverse);
			Iterator<ARSphericalView> it = arViewReverse.iterator();
			View v;
			while(it.hasNext() && !eventCatch)
			{
				v = it.next();
				eventCatch = v.dispatchTouchEvent(event);		
				if(!poiMove) poiMove = ((MagnitudePOI)v).isShifted();
			}
		}
		if(!eventCatch) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				timeOfStart = time;
				inClick = true;
			} else if(inClick && event.getAction() == MotionEvent.ACTION_UP && (time - timeOfStart) > 1000) {
				LaunchAction();
				inClick = false;
			}
		}
		return true;
	}
	public boolean evaluateTouch(MotionEvent event){
		if(SPLASH && gpsIcon.getGpsStatus() == GpsIndicator.GPS_STATUS_DISCONNECTED && splash.dispatchTouchEvent(event)) return true;
		else if(gpsIcon.dispatchTouchEvent(event)) return true;
		else if(radar.dispatchTouchEvent(event)) return true;
		else if(rightSlide.dispatchTouchEvent(event)) return true; 
		else if(titleBar.dispatchTouchEvent(event)) return true;
		else if(poiReinit.dispatchTouchEvent(event)) return true;
		
		return false;
	}
	
	public void onProviderDisabled(String provider){
	}

	public void onProviderEnabled(String provider){		
	}
	
	
	public void onStatusChanged(String provider, int status, Bundle extras) {
		if(status == LocationProvider.OUT_OF_SERVICE || status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
			this.gpsIcon.disconnect();
		} else {
			gpsIcon.update(getCurLocation().getAccuracy(),0);
		}
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1){
	}

	public void onSensorChanged(SensorEvent evt)
	{
		float vals[] = evt.values;
		float localDirection;
		
		
		
		if(evt.sensor.getType() == Sensor.TYPE_ORIENTATION)
		{
			float tmp = vals[0];

			if(tmp < 0)
				tmp = tmp+360;

			tmpPA = vals[0];
			if (vals[0]>180)
				tmpPB = vals[0];
			else
				tmpPB = vals[0]+360;
			pseudoDirectionA = (float) ((tmpPA * getkFilteringFactor()) + (pseudoDirectionA * (1.0 - getkFilteringFactor())));
			pseudoDirectionB = (float) ((tmpPB * getkFilteringFactor()) + (pseudoDirectionB * (1.0 - getkFilteringFactor())));
			
			if(vals[0]<90)
				setDirection(pseudoDirectionB - 360);
			else if(vals[0]>270)
				setDirection(pseudoDirectionB);
			else
				setDirection(pseudoDirectionA);
	
			
			if(getDirection() < 0)
				localDirection = 360+getDirection();
			else
				localDirection = getDirection();
		    
		    if(locationChanged){
		    	updateLayouts(localDirection, (float)getInclination(), getCurLocation());
		    	locationChanged = false;
		    }
		    else
		    	updateLayouts(localDirection, (float)getInclination(), null);
		    	
		    
			this.radar.update();
		}
		if(evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			 setRollingZ((vals[2] * getkFilteringFactor()) + (getRollingZ() * (1.0 - getkFilteringFactor())));
			 setRollingX((vals[0] * getkFilteringFactor()) + (getRollingX() * (1.0 - getkFilteringFactor())));
			 
			if (getRollingZ() != 0.0) 
			{
				setInclination(Math.atan(getRollingX() / getRollingZ()));// + Math.PI / 2.0;
			} else if (getRollingX() < 0) 
			{
				setInclination(Math.PI/2.0);
			} else if (getRollingX() >= 0) 
			{
				setInclination(3 * Math.PI/2.0);
			}
			
			//convert to degress
			setInclination(getInclination() * (360/(2*Math.PI)));
			
			//flip!
			if(getInclination() < 0)
				setInclination(getInclination() + 90);
			else
				setInclination(getInclination() -90);
			
		}
		if(getDirection() < 0)
			localDirection = 360+getDirection();
		else
			localDirection = getDirection();
		
		if(locationChanged){
	    	updateLayouts(localDirection, (float)getInclination(), getCurLocation());
	    	locationChanged = false;
		}
	    else
	    	updateLayouts(localDirection, (float)getInclination(), null);
		
		postInvalidate();
	}

	public void addARView(ARSphericalView view)
	{
		getArViews().add(view);
		
	}
	public void removeARView(ARSphericalView view)
	{
		getArViews().remove(view);
	}
	
	public void clearARViews()
	{
		getArViews().removeAllElements();
	}
	//Given a point, is it visible on the screen?
	
	private  float calcXvalue(float leftArm, float rightArm, float az)
	{
		float offset;
		if(leftArm > rightArm)
		{
			if(az >= leftArm)
			{
				offset = az - leftArm;
			}
			if(az <= rightArm)
			{
				offset =  360 - leftArm + az;
			}
			else
				offset = az - leftArm;
		}
		else
		{
			offset = az - leftArm;
		}
		
		return ((offset)/xAngleWidth) * getScreenWidth();
	}
	private float calcYvalue(float lowerArm, float upperArm, float inc)
	{
		//distance in degress to the lower arm
		float offset = ((upperArm - yAngleWidth) - inc) * -1;
		return getScreenHeight() - ((offset/yAngleWidth) * getScreenHeight());
	}
	
	@Override
	public void onDraw(Canvas c)
	{
		//Log.e("Spec","Updating "+arViews.size()+" views");
		//long time = System.currentTimeMillis();
		Enumeration<ARSphericalView> e = getArViews().elements();
		Enumeration<ARSphericalView> e2 = getArViews().elements();
		Paint p = new Paint();
		float precision = 10000;
		
		if(DEBUG)
		{
			p.setColor(Color.WHITE);

			c.drawText("Compass:"+String.valueOf(Math.round(getDirection()*precision)/precision), 20, 20, p);
			c.drawText("Inclination"+String.valueOf(Math.round(getInclination()*precision)/precision), 150, 20, p);
			//---------------------------------
			ARSphericalView view;
			if(e2.hasMoreElements()) {
				view = e2.nextElement();
				c.drawText("POI1: "+view.isVisible(),20,80,p);
			}
			if(e2.hasMoreElements()) {
				view = e2.nextElement();
				c.drawText("POI2: "+view.isVisible(),20,100,p);
			}
			if(e2.hasMoreElements()) {
				view = e2.nextElement();	
				c.drawText("POI3: "+view.isVisible(),20,120,p);
			}
			//------------------
			if(getCurLocation() != null) {
				c.drawText("Lat : "+String.valueOf(getCurLocation().getLatitude()), 20, 60, p);
				c.drawText("Long : "+String.valueOf(getCurLocation().getLongitude()), 250, 60, p);
			}
		}
		while(e.hasMoreElements())
		{
MagnitudePOI view = (MagnitudePOI)e.nextElement();
			
			if(view.getAzimuth()<330 && view.getAzimuth()>30){
				if(Math.abs(view.getAzimuth() - direction) < 30) {
					view.drawLine(c);				
				}
			}
			else{
				view.drawLine(c);
			}	
		}
		e = getArViews().elements();
		while(e.hasMoreElements())
		{
			MagnitudePOI view = (MagnitudePOI)e.nextElement();
			
			if(view.getAzimuth()<330 && view.getAzimuth()>30){
				if(Math.abs(view.getAzimuth() - direction) < 30) {
					view.setVisible(true);
					view.draw(c);				
				}
				else {
					view.setVisible(false);
				}
			}
			else{
				view.setVisible(true);
				view.draw(c);
			}	
		}
		
		if (TITLEBAR)
		titleBar.draw(c);
		if (RADAR)
		radar.draw(c);
		if (SCROLL)
		rightSlide.draw(c);
		if (GPS)
		gpsIcon.draw(c);
		if(poiMove)
		poiReinit.draw(c);
		if (SPLASH && gpsIcon.getGpsStatus() == GpsIndicator.GPS_STATUS_DISCONNECTED)
		splash.draw(c);
	}
	
	public void replaceAllPoi(){
		Iterator<ARSphericalView> it = getArViews().iterator();
		View v;
		while(it.hasNext()) {
			v = it.next();
			((MagnitudePOI)v).setShifted(false);
			((MagnitudePOI)v).setShiftX(0);
			((MagnitudePOI)v).setShiftY(0);
		}
		poiMove = false;
	}
	
	public void setPoiMove(boolean val) {
		this.poiMove = val;
	}
	
	public void updateLayouts(float Azi, float zAngle, Location l)
	{
		/*
		if(l.hasAccuracy()) this.gpsIcon.disconnect();
		else gpsIcon.update(l.getAccuracy());
		*/
		if(Azi != -1)
		{
			//Process the accelerometor stuff
			// Manages mobile orientation
			float leftArm = Azi -(xAngleWidth/2);
			float rightArm = Azi +(xAngleWidth/2);
			if(leftArm < 0)
				leftArm = leftArm + 360;
			if(rightArm > 360)
				rightArm = rightArm - 360;
			
			if(this.curLeftArm == -1) this.curLeftArm = leftArm;
			if(this.curRightArm == -1) this.curRightArm = rightArm;
			
			if(!(aChanger(curLeftArm,leftArm,(float)0.05))) leftArm = this.curLeftArm;
			else this.curLeftArm = leftArm;
			if(!(aChanger(this.curRightArm,rightArm,(float)0.05))) rightArm = this.curRightArm;
			else this.curRightArm = rightArm;
			
			if(leftArm < 0)
				leftArm = leftArm + 360;
			if(rightArm > 360)
				rightArm = rightArm - 360;
			
			//gere l'orientation du mobile en hauteur
			float upperArm = zAngle + (yAngleWidth/2);
			float lowerArm = zAngle - (yAngleWidth/2);

			if(this.curUpperArm == -1) this.curUpperArm = upperArm;
			if(this.curLowerArm == -1) this.curLowerArm = lowerArm;
			
			if(!(aChanger(this.curUpperArm,upperArm,(float)0.05))) upperArm = this.curUpperArm;
			else this.curUpperArm = upperArm;
			if(!(aChanger(this.curLowerArm,lowerArm,(float)0.05))) lowerArm = this.curLowerArm;
			else this.curLowerArm = lowerArm;
			
			Enumeration<ARSphericalView> e = getArViews().elements();

			if(getArViews().size() == 0)
				return;
			
			while(e.hasMoreElements())
			{
				//If we have a location, and the view has one, update it's data
				try{
					ARSphericalView view = e.nextElement();
					//------------
					if(view.isVisible()) {
						
						if(l != null && view.getLocation() != null)
						{
							float old_azi = view.getAzimuth();
							float old_incli = view.getInclination();
							float new_incli;
							if(aChanger(old_azi,l.bearingTo(view.getLocation()),(float)0.05)) {
								view.setAzimuth((l.bearingTo(view.getLocation()))-90);
							}
							if(view.getAzimuth() < 0)
								view.setAzimuth(360+view.getAzimuth());
							if(l.hasAltitude() && view.getLocation().hasAltitude())
							{
								new_incli = (float) Math.atan(((view.getLocation().getAltitude() - l.getAltitude()) / l.distanceTo(view.getLocation()))); 
								if(aChanger(old_incli, new_incli, (float)0.05))
									view.setInclination(new_incli);
							}
						}
					}
					
	//				if(!isVisibleX(leftArm, rightArm, view.azimuth))
	//				{
	//					view.visible = false;
	//					continue;
	//				}
	//				if(!isVisibleY(lowerArm, upperArm, view.inclination))
	//				{
	//					view.visible = false;
//						continue;
	//				}
//					view.setVisible(true);
					int tmp_x = (int)calcXvalue(leftArm, rightArm, view.getAzimuth());
					int tmp_y = (int)calcYvalue(lowerArm, upperArm, view.getInclination());
					
					if (view instanceof MagnitudePOI) {
						view.layout((int)(((MagnitudePOI)view).getShiftX()+tmp_x),
								    (int)(((MagnitudePOI)view).getShiftY()+tmp_y),
								    (int)((((MagnitudePOI)view).getShiftX())+tmp_x+((MagnitudePOI) view).getViewWidth()),
								    (int)(((MagnitudePOI)view).getShiftY()+tmp_y+((MagnitudePOI) view).getViewHeight()));
					} else view.layout(tmp_x, tmp_y, tmp_x, tmp_y);
				}
				catch(Exception x)
				{
					Log.e("ArLayout", x.getMessage());
				}
			}
		}
		//37.763557,-122.410719
	}

	private boolean aChanger(float old_val, float new_val, float seuil) {
		return (Math.abs(old_val-new_val) >=  seuil);
	}

	public void close()
	{
		getSensorMan().unregisterListener(this);
		getLocMan().removeUpdates(this);
	}
	
	public void initArLayout () {
		WindowManager w = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
		Display d = w.getDefaultDisplay();
		int width = d.getWidth();
		int height = d.getHeight();
		setScreenHeight(height);
		setScreenWidth(width);
		toucheableAction = new ArrayList<String>();
		inClick = false;
	}
	
	public void initSensors() {
		setSensorMan((SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE));
		getSensorMan().registerListener(this, getSensorMan().getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
		getSensorMan().registerListener(this, getSensorMan().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		startGPS();
	}
	
	/**
	 * Initiation of the GUI.
	 */
	public void initGui() {
		int margin = 5; 
		int yMargin=0;
		int yCorrection=0;
		
		gpsIcon = new GpsIndicator(ctx);
		gpsIcon.setX(screenWidth-(gpsIcon.getImage().getWidth()+margin));
		gpsIcon.setY(margin);
		gpsIcon.updateLayout();
		yMargin = gpsIcon.getImage().getHeight() + margin;
	
		radar = new Radar(ctx,this);
		radar.setX(getScreenWidth()-(radar.getImage().getWidth()+margin));
		radar.setY(getScreenHeight()-(radar.getImage().getHeight()+margin));
		radar.updateLayout();
		yCorrection = radar.getImage().getHeight() + 2*margin + yMargin;

		
		rightSlide = new SlideBar(ctx,this);
		rightSlide.setX(getScreenWidth()-(rightSlide.getImage().getWidth()));
		rightSlide.setY(((getScreenHeight()-yCorrection)/2)-(rightSlide.getImage().getHeight()/2)+yMargin);
		rightSlide.updateLayout();
		
		titleBar = new TitleBar(ctx,this);
		titleBar.setX(getLeft());
		titleBar.setY(getTop());
		titleBar.updateLayout();
	
		poiReinit = new ReplacePoi(ctx,this);
		poiReinit.setX(margin);
		poiReinit.setY(getScreenHeight()-(poiReinit.getImage().getHeight()+margin));
		poiReinit.updateLayout();
		yCorrection = poiReinit.getImage().getHeight() + 2*margin + yMargin;	
		
		splash = new SplashScreen(ctx, getScreenWidth(), getScreenHeight());
		splash.setX(0);
		splash.setY(0);
		splash.updateLayout();		
	}
	
	public void updatePoiDistance() {
		// Mise a jour de la distance avec les POI.
		Enumeration<ARSphericalView> e = getArViews().elements();
		if(getArViews().size() == 0)
			return;
		while(e.hasMoreElements())
		{

				ARSphericalView view = e.nextElement();
				view.setDistance(this.getCurLocation().distanceTo(view.getLocation()));
				if(view.getDistance() > this.maxDistance) 
					((MagnitudePOI)view).setVisibleDist(false);
				else 
					((MagnitudePOI)view).setVisibleDist(true);	
				// update scale
				updateScalePOIImage();
				if(view.getDistance() > this.maxDistance) 
					((MagnitudePOI)view).setVisibleDist(false);
				else
					((MagnitudePOI)view).setVisibleDist(true);
		}
		Collections.sort(getArViews(), Collections.reverseOrder());
	}
	
	
	public void updateScalePOIImage() {
		float tempScale;
		// Mise a jour de la taille des images des POI.
		// en fonction de la nouvelle distance
		Enumeration<ARSphericalView> e = getArViews().elements();
		if(getArViews().size() == 0)
			return;
		while(e.hasMoreElements())
		{
			try{
				MagnitudePOI view = (MagnitudePOI)e.nextElement();
				float rapportDist = (view.getDistance() / this.getMaxDistance());
				if (rapportDist <= 0.1) {
					view.setScale((float)1.0);
				} 
				
				else if (rapportDist >0.1 && rapportDist <= 0.8) {
					tempScale = (float)((-5.0/7.0) * rapportDist + 15.0/14.0); 
					view.setScale(tempScale);
				} 
				else {
					view.setScale((float)0.5);
				}
			}
			catch(Exception x)
			{
				Log.e("ArLayout", x.getMessage());
			}
		}	
	}
	
	
	public void setScreenWidth(float screenWidth) {
		this.screenWidth = screenWidth;
	}
	public float getScreenWidth() {
		return screenWidth;
	}
	
	public void setScreenHeight(float screenHeight) {
		this.screenHeight = screenHeight;
	}
	public float getScreenHeight() {
		return screenHeight;
	}
	
	public void setSensorMan(SensorManager sensorMan) {
		this.sensorMan = sensorMan;
	}
	public SensorManager getSensorMan() {
		return sensorMan;
	}
	
	public void setLocMan(LocationManager locMan) {
		this.locMan = locMan;
	}
	public LocationManager getLocMan() {
		return locMan;
	}
	
	public void setCurLocation(Location curLocation) {
		this.curLocation = curLocation;
	}
	public Location getCurLocation() {
		return curLocation;
	}
	
	public void setDirection(float direction) {
		this.direction = direction;
	}
	public float getDirection() {
		return direction;
	}
	
	public void setInclination(double inclination) {
		this.inclination = inclination;
	}
	public double getInclination() {
		return inclination;
	}
	
	public void setRollingX(double rollingX) {
		this.rollingX = rollingX;
	}
	public double getRollingX() {
		return rollingX;
	}
	
	public void setRollingZ(double rollingZ) {
		this.rollingZ = rollingZ;
	}
	public double getRollingZ() {
		return rollingZ;
	}
	
	public void setkFilteringFactor(float kFilteringFactor) {
		this.kFilteringFactor = kFilteringFactor;
	}
	public float getkFilteringFactor() {
		return kFilteringFactor;
	}
	
	public void setOne(float one) {
		this.one = one;
	}
	public float getOne() {
		return one;
	}
	
	public void setTwo(float two) {
		this.two = two;
	}
	public float getTwo() {
		return two;
	}
	
	public void setThree(float three) {
		this.three = three;
	}
	public float getThree() {
		return three;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setRes(Resources res) {
		this.res = res;
	}

	public Resources getRes() {
		return res;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setRightSlide(SlideBar rightSlide) {
		this.rightSlide = rightSlide;
	}

	public SlideBar getRightSlide() {
		return rightSlide;
	}

	public void updateSettings() {
		sp = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
		GPS = sp.getBoolean("gps", true);
		RADAR = sp.getBoolean("radar", true);
		TITLEBAR = sp.getBoolean("bar", true);
		SCROLL = sp.getBoolean("scroll", true);
		DEBUG = sp.getBoolean("debug", false);	
		SPLASH = sp.getBoolean("splash", true);	
	}

	public void stopGPS() {
		getLocMan().removeUpdates(this);
	}
	
	public void startGPS() {
		setLocMan((LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE));
		getLocMan().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
	}

	public void setToucheableAction(ArrayList<String> toucheableAction) {
		this.toucheableAction = toucheableAction;
	}

	public ArrayList<String> getToucheableAction() {
		return toucheableAction;
	}
	
	private void LaunchAction() {
		Iterator<String> it = toucheableAction.iterator();
		String name;
		while(it.hasNext()) {
			name = it.next();
			if(getCurLocation() != null) {
				Intent bindIntent = new Intent(name+".ACTIVITY");
				bindIntent.addCategory(Intent.CATEGORY_DEFAULT);
				bindIntent.putExtra("latitude", getCurLocation().getLatitude());
				bindIntent.putExtra("longitude", getCurLocation().getLongitude());
				main.startActivityForResult(bindIntent, 42);
			} else {
				Toast.makeText(ctx, "No GPS Signal, please try again later.", Toast.LENGTH_LONG).show();
				
			}
			
		}
	}

	public void addToucheableAction(String action) {
		getToucheableAction().add(action);
	}

	@SuppressWarnings("unused")
	private void setArViews(Vector<ARSphericalView> arViews) {
		this.arViews = arViews;
	}

	public void removeToucheableAction(String replace) {
		if(getToucheableAction().contains(replace))
			getToucheableAction().remove(getToucheableAction().indexOf(replace));
	}
}
