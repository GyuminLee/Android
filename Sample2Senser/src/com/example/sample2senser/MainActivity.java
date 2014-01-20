package com.example.sample2senser;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

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

	SensorEventListener mListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				Log.i(TAG, "acc : " + event.values[0] + "," + event.values[1]
						+ "," + event.values[2] + ", time : " + event.timestamp);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				Log.i(TAG, "mag : " + event.values[0] + "," + event.values[1]
						+ "," + event.values[2] + ", time : " + event.timestamp);
				break;
			}
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
