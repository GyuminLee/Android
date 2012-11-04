package com.example.samplearcamera.library;

import com.example.samplearcamera.MyApplication;

import android.content.Context;
import android.graphics.Matrix;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.FloatMath;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

public class ARSensorManager implements SensorEventListener {

	private static ARSensorManager instance;
	
	public final static float ROTATE_DEGREE = -90;
	
	private SensorManager mSensorManager;
	private Handler mSensorHandler;
	
	float[] mGravity, mMagnitude;

	ARMatrix mRotationMatrix;
	ARMatrix tempRotateMatrix;
	float mAdjustDegree;
	ARMatrix tempRMatrix;
	float[] tempR, remapR, mInclinationMatrix;
	float[] mOrientation;
		
	public static ARSensorManager getInstance() {
		if (instance == null) {
			instance = new ARSensorManager();
		}
		return instance;
	}
	
	private ARSensorManager() {
		mSensorManager = (SensorManager)MyApplication.getContext().getSystemService(Context.SENSOR_SERVICE);
		HandlerThread th = new HandlerThread("SensorThread");
		th.start();
		mSensorHandler = new Handler(th.getLooper());
		
		// Sensor Value
		mGravity = new float[3];
		mMagnitude = new float[3];
	
		// RotationMatrix
		mRotationMatrix = new ARMatrix();
		tempRotateMatrix = new ARMatrix();
		
		// GeomagneticField Adjust Matrix
		mAdjustDegree = 0;
		
		// tempMatrix
		tempRMatrix = new ARMatrix();

		// getRotationMatrix
		tempR = new float[16];
		
		// remapRoationMatrix
		remapR = new float[16];
		
		// RotationMatrix inclination
		mInclinationMatrix = new float[16];
		
		// Orientation
		mOrientation = new float[3];
		
	}
	
	public void startSensing() {
		Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME, mSensorHandler);
		sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME, mSensorHandler);
	}
	
	public void stopSensing() {
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	private void setValue(float[] dest,float[] source) {
		dest[0] = source[0];
		dest[1] = source[1];
		dest[2] = source[2];
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {

		switch(event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER :
				setValue(mGravity,event.values);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD :
				setValue(mMagnitude,event.values);
				break;
			default :
				break;	
		}
		
		
		SensorManager.getRotationMatrix(tempR, mInclinationMatrix, mGravity, mMagnitude);
		SensorManager.getOrientation(tempR, mOrientation);
		
		tempRotateMatrix.reset();
		tempRotateMatrix.rotateY(mAdjustDegree);
		tempRotateMatrix.rotateX(ROTATE_DEGREE);
		if (Utility.getDisplayRotation() == Surface.ROTATION_90) {
			SensorManager.remapCoordinateSystem(tempR, SensorManager.AXIS_X, SensorManager.AXIS_Z, remapR);
			tempRMatrix.setValuesTranspose(remapR);
			tempRotateMatrix.multipleMatrix(tempRMatrix);
			tempRotateMatrix.rotateY(ROTATE_DEGREE);
			tempRotateMatrix.rotateX(ROTATE_DEGREE);
		} else {
			SensorManager.remapCoordinateSystem(tempR, SensorManager.AXIS_Y, SensorManager.AXIS_Z, remapR);
			tempRMatrix.setValuesTranspose(remapR);
			tempRotateMatrix.multipleMatrix(tempRMatrix);
			tempRotateMatrix.rotateX(ROTATE_DEGREE);
			tempRotateMatrix.rotateY(ROTATE_DEGREE);
		}
		
		tempRotateMatrix.invert();
		computeAverage(tempRotateMatrix,mRotationMatrix);
	}

	float[][] mRs = new float[30][16];
	int mIndex = 0;
	float[] mAverageR = new float[16];
	int mCount = 0;
	
	private void computeAverage(ARMatrix inMatrix,ARMatrix outMatrix) {
		float[] infloat = inMatrix.getValues();
		float[] outfloat = computeAverage(infloat);
		mRotationMatrix.setValues(outfloat);
	}
	
	private float[] computeAverage(float[] R) {

		mCount++;

		mCount = (mCount < mRs.length)? mCount : mRs.length;
		
		for (int j = 0; j < R.length; j++) {
			mRs[mIndex][j] = R[j];
		}
		
		mIndex = (mIndex + 1) % mRs.length;
		
		return average();
	}
	
	private float[] average() {
		int j;
		for (j = 0; j < mAverageR.length; j++) {
			mAverageR[j] = 0;
		}

		for (int i = 0; i < mRs.length; i++) {
			for (j = 0; j < mAverageR.length; j++) {
				mAverageR[j] += mRs[i][j];
			}
		}
		for (j = 0; j < mAverageR.length; j++) {
			mAverageR[j] /= mCount;
		}
		return mAverageR;
	}
	
	public void setLocation(Location location) {
		GeomagneticField gmf = new GeomagneticField((float)location.getLatitude(), 
				(float)location.getLongitude(), (float)location.getAltitude(), 
				System.currentTimeMillis());
		mAdjustDegree = -gmf.getDeclination();
	}
	
	public ARMatrix getRotationMatrix() {
		return mRotationMatrix;
	}
	
	public float[] getOrientation() {
		return mOrientation;
	}
}
