package com.example.testcomponentsample;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

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
				}
			}
		}).start();
		Toast.makeText(this, "Service onCreated", Toast.LENGTH_SHORT).show();
	}
	
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
		Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();
	}
	
	
}
