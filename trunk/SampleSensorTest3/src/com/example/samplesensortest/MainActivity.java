package com.example.samplesensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class MainActivity extends Activity {

	CompassView compassView;

	SensorManager mSensorManager;
	Sensor mGravitySensor;
	Sensor mMagenticSensor;
	
	public static final int DRAWING_INTERVAL = 100;
	
	Handler mHandler = new Handler();
	
	float mBearing = 0;

	SensorEventListener mListener = new SensorEventListener() {

		float[] mGravityValues = new float[3];
		float[] mMagenticValues = new float[3];
		float[] mR = new float[9];
		float[] mI = new float[9];
		float[] mOrientation = new float[3];
		
		public static final int TOTAL = 30;
		
		float[][] mAverage = new float[TOTAL][9];
		int mIndex = 0;
		
		float[] mAverageR = new float[9];
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				mGravityValues[0] = event.values[0];
				mGravityValues[1] = event.values[1];
				mGravityValues[2] = event.values[2];
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				mMagenticValues[0] = event.values[0];
				mMagenticValues[1] = event.values[1];
				mMagenticValues[2] = event.values[2];
				break;
			default:
				break;
			}
			
			SensorManager.getRotationMatrix(mR, mI, mGravityValues, mMagenticValues);
			
			average(mR);

			SensorManager.getOrientation(mAverageR, mOrientation);
			mBearing = (float)Math.toDegrees(mOrientation[0]);
		}
		
		private void average(float[] r) {
			for (int i = 0; i < 9; i ++) {
				mAverage[mIndex][i] = r[i];
			}
			mIndex = (mIndex + 1) % TOTAL;
			for (int i = 0; i < 9; i++) {
				mAverageR[i] = 0;
				for (int j = 0; j < TOTAL; j++) {
					mAverageR[i] += mAverage[j][i];
				}
				mAverageR[i] /= TOTAL;
			}
		}
		

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		compassView = (CompassView) findViewById(R.id.compassView);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mGravitySensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMagenticSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	@Override
	protected void onResume() {
		mSensorManager.registerListener(mListener, mGravitySensor,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(mListener, mMagenticSensor,
				SensorManager.SENSOR_DELAY_GAME);
		mHandler.post(compassUpdateRunnable);
		super.onResume();
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mListener);
		mHandler.removeCallbacks(compassUpdateRunnable);
		super.onPause();
	}
	
	

	Runnable compassUpdateRunnable = new Runnable() {
		
		@Override
		public void run() {
			compassView.setDegree(mBearing);
			mHandler.postDelayed(this, DRAWING_INTERVAL);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
