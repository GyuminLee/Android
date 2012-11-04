package com.example.samplearcamera.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.view.View;

import com.example.samplearcamera.R;
import com.example.samplearcamera.library.ARLocationManager;
import com.example.samplearcamera.library.ARSensorManager;

public class CompassView extends View {

	Location mTargetLocation;
	Bitmap mCompass;
	Bitmap mArrow;
	Paint mPaint;
	boolean isDrawingArrow = false;
	private static final int UPDATE_INTERVAL = 250;
	
	public CompassView(Context context) {
		super(context);
		mCompass = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.comass)).getBitmap();
		mArrow = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.arrow)).getBitmap();
		mPaint = new Paint();
		post(mUpdateRunnable);
	}

	public void setTargetLocation(Location target) {
		mTargetLocation = target;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = mCompass.getWidth();
		int height = mCompass.getHeight();
		setMeasuredDimension(width, height);
	}
	
	Runnable mUpdateRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			invalidate();
			postDelayed(mUpdateRunnable,UPDATE_INTERVAL);
		}
		
	};
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.save();
		int centerX = mCompass.getWidth() / 2;
		int centerY = mCompass.getHeight() / 2;
		float orientation = getNorthOrientation();
		canvas.translate(centerX, centerY);
		canvas.rotate(-orientation);
		canvas.drawBitmap(mCompass, -centerX, -centerY, mPaint);
		orientation = getPOIOrientation();
		if (isDrawingArrow) {
			canvas.rotate(orientation);
			int x = (mCompass.getWidth() - mArrow.getWidth()) / 2;
			int y = (mCompass.getHeight() - mArrow.getHeight()) / 2;
			canvas.drawBitmap(mArrow, x - centerX, y - centerY, mPaint);
		}
		canvas.restore();
	}
	
	private float getNorthOrientation() {
		float[] orientation = ARSensorManager.getInstance().getOrientation();
		float orientationY = (float)Math.toDegrees(orientation[2]);
		return orientationY;
	}
	
	private float getPOIOrientation() {
		Location currentLocation = ARLocationManager.getInstance().getCurrentLocation();
		isDrawingArrow = false;
		if (currentLocation != null && mTargetLocation != null) {
			float bearing = currentLocation.bearingTo(mTargetLocation);
			isDrawingArrow = true;
			return bearing;
		} 
		return 0;
	}
}
