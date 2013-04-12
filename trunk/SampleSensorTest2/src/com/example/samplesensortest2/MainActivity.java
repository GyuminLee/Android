package com.example.samplesensortest2;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

	SensorManager mSensorManager;
	float[] accValues = new float[3];
	float[] magValues = new float[3];

	float[] Rm = new float[9];
	float[] Im = new float[9];

	float[] orientation = new float[3];
	
	float proximityMeter;
	long proximityTime;
	
	float oldAcc = SensorManager.GRAVITY_EARTH;
	public final static float LIMIT = 1.0f;
	
	public final static int SHAKE_COUNT = 3;

	int mCount = 0;
	
	Handler mHandler = new Handler();
	
	CompassView compassView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		compassView = (CompassView)findViewById(R.id.compassView);
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Sensor mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_GAME);
		Sensor magSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mSensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_GAME);
//		Sensor proxiSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//		mSensorManager.registerListener(this, proxiSensor, SensorManager.SENSOR_DELAY_GAME);
		mHandler.post(drawRunnable);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mSensorManager.unregisterListener(this);
		mHandler.removeCallbacks(drawRunnable);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		switch(event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER :
			accValues[0] = event.values[0];
			accValues[1] = event.values[1];
			accValues[2] = event.values[2];
//			float acc = (float)Math.sqrt(accValues[0] * accValues[0] + accValues[1] * accValues[1] + accValues[2] * accValues[2]);
//			if (Math.abs(acc - oldAcc) > LIMIT) {
//				mCount++;
//				mHandler.removeCallbacks(resetCount);
//				if (mCount > SHAKE_COUNT) {
//					// shake...
//					mCount = 0;
//					Toast.makeText(this, "Shaking...", Toast.LENGTH_SHORT).show();
//				} else {
//					mHandler.postDelayed(resetCount, 2000);
//				}
//			}
//			oldAcc = acc;
			break;
		case Sensor.TYPE_MAGNETIC_FIELD :
			magValues[0] = event.values[0];
			magValues[1] = event.values[1];
			magValues[2] = event.values[2];
			break;
//		case Sensor.TYPE_PROXIMITY :
//			proximityMeter = event.values[0];
//			proximityTime = event.timestamp;
//			break;
		}
		
		SensorManager.getRotationMatrix(Rm, Im, accValues, magValues);
		SensorManager.getOrientation(Rm, orientation);
	}
	
	Runnable drawRunnable = new Runnable() {
		
		@Override
		public void run() {
			compassView.setOrientation((float)Math.toDegrees(orientation[0]));
			mHandler.postDelayed(this, 100);
		}
	};
	
	
	
	Runnable resetCount = new Runnable() {
		public void run() {
			mCount = 0;
		};
	};

}
