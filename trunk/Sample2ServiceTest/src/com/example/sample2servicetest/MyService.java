package com.example.sample2servicetest;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	private static final String TAG = "MyService";

	public static final String PARAM_SET_COUNT = "setCount";
	public static final String PARAM_RESULT_INTENT = "resultIntent";
	public static final String PARAM_RESULT_COUNT = "count";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	int mCount;
	boolean isRunning = false;

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
		mCount = 0;
		isRunning = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRunning) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mCount++;
					Log.i(TAG, "count : " + mCount);
				}

			}
		}).start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			Toast.makeText(this, "onStartCommand...", Toast.LENGTH_SHORT)
					.show();
			int count = intent.getIntExtra(PARAM_SET_COUNT, 0);
			PendingIntent pi = intent.getParcelableExtra(PARAM_RESULT_INTENT);
			Intent result = new Intent();
			result.putExtra(PARAM_RESULT_COUNT, mCount);
			try {
				pi.send(this, Activity.RESULT_OK, result);
			} catch (CanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mCount = count;
		}
		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "onDestroy...", Toast.LENGTH_SHORT).show();
		isRunning = false;
		super.onDestroy();
	}
}
