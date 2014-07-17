package com.example.sample4alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		long time = System.currentTimeMillis();
		doAction(time);
		AlarmDataManager.getInstance().registerAlarm(this);
		return Service.START_NOT_STICKY;
	}
	
	private void doAction(long time) {
		Intent i = new Intent(this,AlarmActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

}
