package com.example.samplesensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	CompassView compassView;

	SensorManager mSensorManager;
	Sensor mGravitySensor;
	Sensor mMageticSensor;

	SensorEventListener mListener = new SensorEventListener() {

		float[] mGravityValues = new float[3];
		float[] mMagenticValues = new float[3];
		
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
		mMageticSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	@Override
	protected void onResume() {
		mSensorManager.registerListener(mListener, mGravitySensor,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(mListener, mMageticSensor,
				SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mListener);
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
