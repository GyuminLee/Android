package com.example.sample3alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	AlarmManager mAM;

	@Override
	public void onCreate() {
		super.onCreate();
		mAM = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if ((intent.getFlags() & Intent.FLAG_FROM_BACKGROUND) != 0) {
			Toast.makeText(this, "alarm !!!", Toast.LENGTH_SHORT);
			AlarmDBManager.getInstance().actionAlarm();
			long alarmTime = AlarmDBManager.getInstance().getFirstAlarmTime();
			Intent i = new Intent(this, MyService.class);
			PendingIntent operation = PendingIntent.getService(this, 0, i, 0);
			mAM.set(AlarmManager.RTC, alarmTime, operation);
		}
		return Service.START_NOT_STICKY;
	}

}
