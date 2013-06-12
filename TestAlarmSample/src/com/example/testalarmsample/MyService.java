package com.example.testalarmsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String value = intent.getStringExtra("param");
		if ((intent.getFlags() & Intent.FLAG_FROM_BACKGROUND) == Intent.FLAG_FROM_BACKGROUND) {
			// AlarmManager... 
		}
		Toast.makeText(this, "Start Service..." + value, Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}

}
