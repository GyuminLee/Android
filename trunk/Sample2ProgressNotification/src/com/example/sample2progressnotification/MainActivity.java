package com.example.sample2progressnotification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText editView;
	NotificationManager mNM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editView = (EditText) findViewById(R.id.editText1);
		mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Button btn = (Button) findViewById(R.id.showNotification);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int progress = Integer.parseInt(editView.getText().toString());
				if (progress < 100) {
					NotificationCompat.Builder builder = new NotificationCompat.Builder(
							MainActivity.this);
					builder.setTicker("download : " + progress + "%");
					builder.setSmallIcon(R.drawable.ic_launcher);
					builder.setContentTitle("download...");
					builder.setProgress(100, progress, false);
					builder.setOngoing(true);
					builder.setWhen(System.currentTimeMillis());
					Intent i = new Intent(MainActivity.this, MainActivity.class);
					PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, i, 0);
					builder.setContentIntent(pi);
					mNM.notify(1, builder.build());
				} else {
					mNM.cancel(1);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
