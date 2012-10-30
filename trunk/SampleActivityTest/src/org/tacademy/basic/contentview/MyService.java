package org.tacademy.basic.contentview;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		PendingIntent pi = intent.getParcelableExtra("resultpi");
		Intent i = new Intent();
		i.putExtra("value2", "service");
		try {
			pi.send(this, Activity.RESULT_OK, i);
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Service.START_STICKY;
	}
}
