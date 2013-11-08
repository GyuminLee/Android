package com.example.hellogcmtest;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyGCMService extends IntentService {
	public MyGCMService() {
		super("mygcmservice");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String type = gcm.getMessageType(intent);
		if (type.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
			String message = intent.getStringExtra("message");
			NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			builder.setTicker("gcm message...");
			builder.setAutoCancel(true);
			builder.setContentTitle("gcm message");
			builder.setContentText(message);
			builder.setSmallIcon(R.drawable.ic_launcher);
			builder.setWhen(System.currentTimeMillis());
			builder.setDefaults(Notification.DEFAULT_ALL);
			Intent i = new Intent(this, MyActivity.class);
			i.putExtra("message", message);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
			builder.setContentIntent(pi);
			builder.setDeleteIntent(pi);
			
			builder.setProgress(100, 40, false);
			builder.setOngoing(true);
			
			nm.notify(1, builder.build());
			
		}
	}

}
