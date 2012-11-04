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
import com.magnitude.app.R;

/**
 * Management of the GPS signal and his representation on screen.
 * 
 * @author Magnitude Client
 */
public class GpsIndicator extends MagnitudeStaticView {
	/**
	 * Resource's reference to the image.
	 */
	private int icon;

	/**
	 * The image to draw on screen.
	 */
	private Bitmap image;

	/**
	 * To be able to access the resources.
	 */
	private Resources res;

	/**
	 * GPS disconnected constant value.
	 */
	public static int GPS_STATUS_DISCONNECTED = 0;

	/**
	 * GPS bad connection constant value.
	 */
	public static int GPS_STATUS_BAD_CONNECTION = 1;

	/**
	 * GPS good connection constant value.
	 */
	public static int GPS_STATUS_GOOD_CONNECTION = 2;

	/**
	 * GPS perfect connection constant value.
	 */
	public static int GPS_STATUS_PERFECT_CONNECTION = 3;

	/**
	 * Constant to convert feet to meter.
	 */
	public static double FEET_TO_METER_ACCURACY = 0.30478513;

	/**
	 * The actual GPS status.
	 */
	private int gpsStatus = GPS_STATUS_DISCONNECTED;

	/**
	 * String to describe the GPS status.
	 */
	private String gpsDescription;

	/**
	 * Constructor.
	 * 
	 * @param ctx
	 *            The context of the application.
	 * @param scrWidth
	 *            Screen's width.
	 * @param scrHeight
	 *            Screen's height.
	 */
	public GpsIndicator(Context ctx) {
		super(ctx, true);
		res = getResources();
		gpsDescription = new String("GPS disconnected");
		setIcon(R.drawable.gps_nosignal);
		this.disconnect();
	}

	/**
	 * Update the icon, GPS status and the description. But before check if
	 * accuracy has changed.
	 * 
	 * @param currentAccuracy
	 *            Old accuracy.
	 * @param newAccuracy
	 *            New accuracy.
	 */
	public void update(float currentAccuracy, float newAccuracy) {
			if (currentAccuracy == 0 || currentAccuracy > 64) {
				setGpsStatus(GPS_STATUS_DISCONNECTED);
				setIcon(R.drawable.gps_nosignal);
				gpsDescription = new String(
						"GPS with bad connection (accuracy = "
								+ currentAccuracy + "m)");
			} else {
				setGpsStatus(GPS_STATUS_PERFECT_CONNECTION);
				setIcon(R.drawable.gps_full);
				gpsDescription = new String(
						"GPS with good connection (accuracy = "
								+ currentAccuracy + "m)");
			}
			updateLayout();
	}

	/**
	 * To set the icon to disconnect (when GPS signal is lost)
	 */
	public void disconnect() {
		setGpsStatus(GPS_STATUS_DISCONNECTED);
		setIcon(R.drawable.gps_nosignal);
		gpsDescription = new String("GPS disconnected (accuracy = - m)");
		updateLayout();
	}

	/**
	 * Update the layout dimension
	 */
	public void updateLayout() {
		layout((int) getX(), (int) getY(),
				(int) getX() + getImage().getWidth(), (int) getY()
						+ getImage().getHeight());
	}

	/**
	 * Draw the icon or the black screen.
	 */
	public void draw(Canvas c) {
		if (isVisible() && getImage() != null) {
			c.drawBitmap(getImage(), getLeft(), getTop(), getP());
		}
	}

	/**
	 * Return the current icon (int reference to the picture).
	 * 
	 * @return return the icon.
	 */
	public int getIcon() {
		return icon;
	}

	/**
	 * Set the icon, the image is updated automatically.
	 * 
	 * @param icon
	 *            the int reference of the picture.
	 */
	public void setIcon(int icon) {
		this.icon = icon;
		image = BitmapFactory.decodeResource(res, icon);
	}

	/**
	 * Return the image.
	 * 
	 * @return the actual image
	 */
	public Bitmap getImage() {
		return image;
	}

	/**
	 * Event on touch. Notify if it's in the view (return true) or not (return
	 * false). Also toast with the gps status.
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int sensitivityUp = 7;
		if (action == MotionEvent.ACTION_DOWN) {
			if (new Region(getLeft()-sensitivityUp, getTop()-sensitivityUp, getRight()+sensitivityUp, getBottom()+sensitivityUp)
					.contains((int) event.getX(), (int) event.getY())) {
				Toast.makeText(getCtx(), gpsDescription, Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		}
		return false;
	}

	public void setGpsStatus(int gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public int getGpsStatus() {
		return gpsStatus;
	}
}
