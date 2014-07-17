package com.example.sample4notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	NotificationManager mNM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btn_noti);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker("ticker message...");
				builder.setContentTitle("content title");
				builder.setContentText("content text");
				builder.setWhen(System.currentTimeMillis());

				Intent i = new Intent(MainActivity.this, NotiActivity.class);
				i.putExtra("param", "value");
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this,
						0, i, 0);
				builder.setContentIntent(pi);

				builder.setDefaults(Notification.DEFAULT_ALL);

				builder.setAutoCancel(true);

				mNM.notify(0, builder.build());

			}
		});
		btn = (Button) findViewById(R.id.btn_progress);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				progress = 0;
				mHandler.post(progressRunnable);
			}
		});
		mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	Handler mHandler = new Handler();
	int progress = 0;
	Runnable progressRunnable = new Runnable() {

		@Override
		public void run() {
			if (progress <= 100) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker("progress...");
				builder.setContentTitle("progress");
				builder.setProgress(100, progress, false);
				builder.setWhen(System.currentTimeMillis());
				builder.setOngoing(true);
				mNM.notify(1, builder.build());
				progress += 1;
				mHandler.postDelayed(this, 20);
			} else {
				mNM.cancel(1);
			}

		}
	};
}
