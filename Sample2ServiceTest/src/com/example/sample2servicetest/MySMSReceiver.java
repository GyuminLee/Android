package com.example.sample2servicetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setTicker("sms received...");
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("My Title...");
		builder.setContentText("text...");
		builder.setWhen(System.currentTimeMillis());
		Intent i = new Intent(context,MyActivity.class);
		i.putExtra("message", "sms received...");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		builder.setContentIntent(pi);
		builder.setAutoCancel(true);
		builder.setDefaults(Notification.DEFAULT_ALL);
		nm.notify(1, builder.build());
//		Toast.makeText(context, "sms received", Toast.LENGTH_SHORT).show();
//		abortBroadcast();
	}

}
