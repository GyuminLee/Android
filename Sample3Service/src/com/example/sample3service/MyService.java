package com.example.sample3service;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	int mCount = 0;
	boolean isRunning = false;

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
		isRunning = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRunning) {
					Log.i("MyService", "count : " + mCount);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mCount++;
				}
			}
		}).start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand...", Toast.LENGTH_SHORT).show();
		if (intent != null) {
			PendingIntent pi = intent.getParcelableExtra("pendingIntent");
			Intent result = new Intent();
			result.putExtra("count", mCount);
			try {
				pi.send(this, Activity.RESULT_OK, result);
			} catch (CanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int count = intent.getIntExtra("count", 0);
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
