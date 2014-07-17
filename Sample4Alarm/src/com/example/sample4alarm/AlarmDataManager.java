package com.example.sample4alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmDataManager {
	private static AlarmDataManager instance;
	public static AlarmDataManager getInstance() {
		if (instance == null) {
			instance = new AlarmDataManager();
		}
		return instance;
	}
	
	private AlarmDataManager() {
		
	}
	
	public void registerAlarm(Context context) {
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, AlarmService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		long time = calculateTime();
		am.set(AlarmManager.RTC_WAKEUP, time, pi);
	}
	
	private long calculateTime() {
		return System.currentTimeMillis() + 10000;
	}
}
