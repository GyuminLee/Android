package com.example.testcomponentsample;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	private static final String TAG = "MyService";
	
	public static final String ACTION_TEN_COUNT = "com.example.testcomponentsample.ACTION.TEN_COUNT";
	
	int mCount = 0;
	boolean isRunning = false;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

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
						e.printStackTrace();
					}
					mCount++;
					if (mCount % 10 == 0) {
						Intent i = new Intent(ACTION_TEN_COUNT);
						i.putExtra("count", mCount);
						sendBroadcast(i,"com.example.testcomponentsample.permission.TEN_COUNT");
					}
				}
			}
		}).start();
		Toast.makeText(this, "Service onCreated", Toast.LENGTH_SHORT).show();
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);
	}
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Log.i(TAG, "screen off message received");
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				Log.i(TAG, "screen on message received");
				Toast.makeText(context, "Screen On!!!" , Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String param1 = intent.getStringExtra("param1");
		PendingIntent resultPI = intent.getParcelableExtra("resultPI");
		// .... 
		Intent result = new Intent();
		result.putExtra("serviceParam1", "svalue");
		try {
			resultPI.send(this, Activity.RESULT_OK, result);
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this, "Service onStartCommand : " + mCount + ",param1 : " + param1, Toast.LENGTH_SHORT).show();
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
		Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();
	}
	
	
}
