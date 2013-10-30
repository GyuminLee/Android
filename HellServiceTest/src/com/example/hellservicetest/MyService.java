package com.example.hellservicetest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service implements Runnable {

	boolean isRunning = false;
	int mCount = 0;
	private final String TAG = "MyService";
	
	public static final String ACTION_COUNT = "com.example.helloservicetest.action.COUNT";
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();
		isRunning = true;
		new Thread(this).start();
		Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		registerReceiver(myReceiver, filter);
		
		filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(myReceiver, filter);
	}

	BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				Log.i(TAG, "screen on...");
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Log.i(TAG, "screen off...");
			}
		}
		
	};

	@Override
	public void run() {
		while(isRunning) {
			try {
				Thread.sleep(1000);
				mCount++;
				Log.i(TAG, "count : " + mCount);
				if (mCount % 10 == 0) {
					Intent i = new Intent(ACTION_COUNT);
					i.putExtra("count", mCount);
					String permission = getResources().getString(R.string.count_permission);
					sendBroadcast(i,permission);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand...", Toast.LENGTH_SHORT).show();
//		String value = intent.getStringExtra("param1");
//		PendingIntent pi = intent.getParcelableExtra("result");
//		Intent resultData = new Intent();
//		resultData.putExtra("resultValue", "re...");
//		try {
//			pi.send(this, Activity.RESULT_OK, resultData);
//		} catch (CanceledException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return Service.START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "onDestroy...", Toast.LENGTH_SHORT).show();
		isRunning = false;
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}
}
