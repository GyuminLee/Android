package com.example.hellogcmtest;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class GcmService extends IntentService {
	
	public GcmService() {
		super("GCMService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String type = gcm.getMessageType(intent);
		if (type.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
			// ...
			String name = intent.getStringExtra("name");
			int age = Integer.parseInt(intent.getStringExtra("age"));
			// ...
			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			builder.setTicker("gcm message : " + name);
			builder.setAutoCancel(true);
			
			
			NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			nm.notify(1, builder.build());
		}
		
	}

}
