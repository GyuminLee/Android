package com.example.sample2servicetest;

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
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
		registerReceiver(myReceiver, filter);
		
	}
	
	class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Intent i = new Intent(MyService.this, MyActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
				Toast.makeText(MyService.this, "Screen on...", Toast.LENGTH_SHORT).show();
			} else if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
				Toast.makeText(MyService.this, "plug event...", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	MyReceiver myReceiver = new MyReceiver();

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
