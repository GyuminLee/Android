package com.example.sample4sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

public class MainActivity extends Activity {

	SensorManager mSM;
	Sensor mAccSensor;
	Sensor mMagSensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSM = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mAccSensor = mSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMagSensor = mSM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mHandlerThread = new HandlerThread("sensorHandler");
		mHandlerThread.start();
		mHandlerThreadHandler = new Handler(mHandlerThread.getLooper());
	}
	Handler mHandler = new Handler();
	HandlerThread mHandlerThread;
	Handler mHandlerThreadHandler;
	
	SensorEventListener mListener = new SensorEventListener() {
		
		double oldG = SensorManager.GRAVITY_EARTH;
		public final static double DELTA = 1.0;
		int count = 0;
		public final static int THREDHOLD = 3;
		Runnable resetRunnable = new Runnable() {
			
			@Override
			public void run() {
				count = 0;
			}
		};
		
		float[] accValue = new float[3];
		float[] magValue = new float[3];
		float[] mR = new float[9];
		float[] mI = new float[9];
		float[] mOrientation = new float[3];
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch(event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER :
				double ax = event.values[0];
				double ay = event.values[1];
				double az = event.values[2];
				accValue[0] = event.values[0];
				accValue[1] = event.values[1];
				accValue[2] = event.values[2];
				double g = Math.sqrt(ax * ax + ay * ay + az * az);
				if (Math.abs(g - oldG) > DELTA) {
					mHandlerThreadHandler.removeCallbacks(resetRunnable);
					count++;
					if (count >= THREDHOLD) {
						// shake
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								onShake();
							}
						});
						count = 0;
					}
					mHandlerThreadHandler.postDelayed(resetRunnable, 1000);
				}
				oldG = g;
				break;
			case Sensor.TYPE_MAGNETIC_FIELD :
				double mx = event.values[0];
				double my = event.values[1];
				double mz = event.values[2];
				magValue[0] = event.values[0];
				magValue[1] = event.values[1];
				magValue[2] = event.values[2];				
				break;
			}
			
			SensorManager.getRotationMatrix(mR, mI, accValue, magValue);
			SensorManager.getOrientation(mR, mOrientation);
			double degrees = Math.toDegrees((double)mOrientation[0]);
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	};
	
	private void onShake() {
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mSM.registerListener(mListener, mAccSensor, SensorManager.SENSOR_DELAY_GAME, mHandlerThreadHandler);
		mSM.registerListener(mListener, mMagSensor, SensorManager.SENSOR_DELAY_GAME, mHandlerThreadHandler);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mSM.unregisterListener(mListener);
	}
}
