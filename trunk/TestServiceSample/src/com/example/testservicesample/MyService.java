package com.example.testservicesample;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	int mCount = 0;
	boolean isRunning = false;
	private final static String TAG = "MyService";
	public static final String EVENT_MODULAR_ZERO = "com.example.testservicesample.action.MODULAR_ZERO";
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isRunning) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mCount++;
					
					if ((mCount % 10) == 0) {
						Intent i = new Intent(EVENT_MODULAR_ZERO);
						i.putExtra("count", mCount);
						sendBroadcast(i);
					}
					
					Log.i(TAG, "mCount : " + mCount);
				}
			}
		});
		isRunning = true;
		th.start();
		Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (intent != null) {
//			int count = intent.getIntExtra("count", mCount);
			
//			PendingIntent pi = (PendingIntent)intent.getParcelableExtra("resultPI");
			
			Toast.makeText(this, "mCount : " + mCount, Toast.LENGTH_SHORT).show();
			
//			Intent resultIntent = new Intent();
//			resultIntent.putExtra("count", mCount);
			
//			try {
//				pi.send(this, Activity.RESULT_OK, resultIntent);
//			} catch (CanceledException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//			mCount = count;
		} else {
			Toast.makeText(this, "Service restart", Toast.LENGTH_SHORT).show();
		}
		
		return Service.START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isRunning = false;
		Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
	}

}
