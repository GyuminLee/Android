package org.tacadmey.basic.samplesensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	SensorManager mSensorManager;
	Sensor mSensor;
	
	SensorEventListener mListener = new SensorEventListener() {

		final static int ACCEPT_SHAKE_COUNT = 2;
		final static double THRESHOLD = 1.0;
		final static int MESSAGE_CLEAR_SHAKE_COUNT = 1;
		final static int CLEAR_TIME_OUT = 2000;

		HandlerThread mThread;
		Handler mHandler;
		int mShakeCount = 0;
		double mOldAcc = SensorManager.GRAVITY_EARTH;
		{
			mThread = new HandlerThread("Sensor");
			mThread.start();
			mHandler = new Handler(mThread.getLooper()) {
				public void handleMessage(android.os.Message msg) {
					switch(msg.what) {
					case MESSAGE_CLEAR_SHAKE_COUNT:
						mShakeCount = 0;
						break;
					}
				};
			};
		}

	     private static final float NS2S = 1.0f / 1000000000.0f;
	     private final float[] deltaRotationVector = new float[4];
	     private float timestamp;
	     private final float EPSILON = 1.0f;
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				double dAcc = Math.sqrt(x*x + y*y + z*z);
				if (Math.abs(mOldAcc - dAcc) > THRESHOLD) {
					mShakeCount++;
					mHandler.removeMessages(MESSAGE_CLEAR_SHAKE_COUNT);
					if (mShakeCount > ACCEPT_SHAKE_COUNT) {
						mShakeCount = 0;
						Log.i("MainActivity","call shake");
					} else {
						mHandler.sendEmptyMessageDelayed(MESSAGE_CLEAR_SHAKE_COUNT, CLEAR_TIME_OUT);
					}
				}
				mOldAcc = dAcc;
				Log.i("MainActivity","x:" + event.values[0] 
					+ ",y:" + event.values[1] 
					+ ",z:" + event.values[2]);
			} else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
		          if (timestamp != 0) {
		              final float dT = (event.timestamp - timestamp) * NS2S;
		              // Axis of the rotation sample, not normalized yet.
		              float axisX = event.values[0];
		              float axisY = event.values[1];
		              float axisZ = event.values[2];

		              // Calculate the angular speed of the sample
		              float omegaMagnitude = (float)(Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ));

		              // Normalize the rotation vector if it's big enough to get the axis
		              if (omegaMagnitude > EPSILON) {
		                  axisX /= omegaMagnitude;
		                  axisY /= omegaMagnitude;
		                  axisZ /= omegaMagnitude;
		              }

		              // Integrate around this axis with the angular speed by the timestep
		              // in order to get a delta rotation from this sample over the timestep
		              // We will convert this axis-angle representation of the delta rotation
		              // into a quaternion before turning it into the rotation matrix.
		              float thetaOverTwo = omegaMagnitude * dT / 2.0f;
		              float sinThetaOverTwo = (float)(Math.sin(thetaOverTwo));
		              float cosThetaOverTwo = (float)(Math.cos(thetaOverTwo));
		              deltaRotationVector[0] = sinThetaOverTwo * axisX;
		              deltaRotationVector[1] = sinThetaOverTwo * axisY;
		              deltaRotationVector[2] = sinThetaOverTwo * axisZ;
		              deltaRotationVector[3] = cosThetaOverTwo;
		          }
		          timestamp = event.timestamp;
		          float[] deltaRotationMatrix = new float[9];
		          SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
		          // User code should concatenate the delta rotation we computed with the current rotation
		          // in order to get the updated rotation.
		          // rotationCurrent = rotationCurrent * deltaRotationMatrix;
				
			} else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
				StringBuilder sb = new StringBuilder();
				sb.append("Rotation x : ");
				sb.append(event.values[0]);
				sb.append(",y:");
				sb.append(event.values[1]);
				sb.append(",z:");
				sb.append(event.values[2]);
				if (event.values.length > 3) {
					sb.append(",cos : ");
					sb.append(event.values[3]);
				}
				Log.i("MainActivity",sb.toString());
			}
		}
		
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
        mSensorManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	mSensorManager.unregisterListener(mListener);
    	super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
