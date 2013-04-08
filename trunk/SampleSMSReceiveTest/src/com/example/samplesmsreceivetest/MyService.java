package com.example.samplesmsreceivetest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		super.onCreate();

		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		
		registerReceiver(screenOnOff, filter);
	}
	
	BroadcastReceiver screenOnOff = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Intent i = new Intent(context, MyActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				Toast.makeText(context, "screen on...", Toast.LENGTH_SHORT).show();
			}
		}
	};
}
