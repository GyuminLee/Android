package com.example.sample2senser;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	SensorManager mSensorManager;

	Sensor mAcc;
	Sensor mMag;

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMag = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	Handler mHandler = new Handler();
	SensorEventListener mListener = new SensorEventListener() {

		float mOldAccValue = -1;
		float mDelta = 1.0f;
		int mShakeCount = 0;
		final static int ACTION_SHAKE_COUNT = 3;

		Runnable timeout = new Runnable() {

			@Override
			public void run() {
				mShakeCount = 0;
			}
		};

		float[] mAccValues = new float[3];
		float[] mMagValues = new float[3];
		float[] mR = new float[9];
		float[] mI = new float[9];
		float[] mOrientation = new float[3];
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				// Log.i(TAG, "acc : " + event.values[0] + "," + event.values[1]
				// + "," + event.values[2] + ", time : " + event.timestamp);
//				float newAccValue = FloatMath.sqrt(event.values[0]
//						* event.values[0] + event.values[1] * event.values[1]
//						+ event.values[2] * event.values[2]);
//				if (mOldAccValue == -1) {
//					mOldAccValue = newAccValue;
//				}
//				if (mOldAccValue - newAccValue > mDelta
//						&& newAccValue - mOldAccValue > mDelta) {
//					Log.i(TAG, "Shake...");
//					mShakeCount++;
//					if (mShakeCount > ACTION_SHAKE_COUNT) {
//						Toast.makeText(MainActivity.this, "shake action...",
//								Toast.LENGTH_SHORT).show();
//						mShakeCount = 0;
//						mHandler.removeCallbacks(timeout);
//					} else {
//						mHandler.removeCallbacks(timeout);
//						mHandler.postDelayed(timeout, 1000);
//					}
//				}
//				mOldAccValue = newAccValue;

				mAccValues[0] = event.values[0];
				mAccValues[1] = event.values[1];
				mAccValues[2] = event.values[2];
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				// Log.i(TAG, "mag : " + event.values[0] + "," + event.values[1]
				// + "," + event.values[2] + ", time : " + event.timestamp);
				mMagValues[0] = event.values[0];
				mMagValues[1] = event.values[1];
				mMagValues[2] = event.values[2];
				break;
			}
			
			SensorManager.getRotationMatrix(mR, mI, mAccValues, mMagValues);
			
			SensorManager.getOrientation(mR, mOrientation);
			
			mOrientation[0] = (float)Math.toDegrees(mOrientation[0]);
			mOrientation[1] = (float)Math.toDegrees(mOrientation[1]);
			mOrientation[2] = (float)Math.toDegrees(mOrientation[2]);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		mSensorManager.registerListener(mListener, mAcc,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(mListener, mMag,
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mSensorManager.unregisterListener(mListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
