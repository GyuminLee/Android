package com.example.sample4service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
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
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(receiver, filter);
	}
	
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(android.content.Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				Log.i(TAG,"screen on");
			}else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Log.i(TAG,"screen off");
			}
		}
	};
	
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
		unregisterReceiver(receiver);
		isRunning = false;
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
	}

}
