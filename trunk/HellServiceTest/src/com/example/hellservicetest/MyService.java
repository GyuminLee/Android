package com.example.hellservicetest;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service implements Runnable {

	boolean isRunning = false;
	int mCount = 0;
	private final String TAG = "MyService";
	
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
	}


	@Override
	public void run() {
		while(isRunning) {
			try {
				Thread.sleep(1000);
				mCount++;
				Log.i(TAG, "count : " + mCount);
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
		super.onDestroy();
	}
}
