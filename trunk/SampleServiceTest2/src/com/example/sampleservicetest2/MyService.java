package com.example.sampleservicetest2;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	private final static String TAG = "MyService";
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String param = intent.getStringExtra("service_param1");
		Toast.makeText(this, "onStartCommand..." + param, Toast.LENGTH_SHORT).show();
		PendingIntent resultPI = intent.getParcelableExtra("result");
		// ....
		Intent resultIntent = new Intent();
		resultIntent.putExtra("service_result", "value2");
		try {
			resultPI.send(this, Activity.RESULT_OK, resultIntent);
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(TAG, "onStartCommand");
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onDestroy...", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

}
