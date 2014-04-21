package com.example.sample3alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    long time = AlarmDBManager.getInstance().getFirstAlarmTime();
	    Intent i = new Intent(context, MyService.class);
	    PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
	    AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	    am.set(AlarmManager.RTC, time, pi);
	    
	}

}
