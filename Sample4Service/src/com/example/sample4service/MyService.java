package com.example.sample4service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	private static final String TAG = "MyService";
	public static final String PARAM_COUNT = "count";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	boolean isRunning = false;
	int mCount = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		isRunning = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isRunning) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.i(TAG, "count : " + mCount);
					mCount++;
				}
			}
		}).start();
		Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
		if (intent != null) {
			mCount = intent.getIntExtra(PARAM_COUNT, 0);
		}
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning = false;
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
	}

}
