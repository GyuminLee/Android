package com.example.samplenotificationcompacttest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	int mId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				
				NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
				
				builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
				builder.setVibrate(new long[] {400,200,300,100});
				builder.setAutoCancel(true);
				builder.setTicker("tickerText");
				builder.setContentInfo("contentinfo");
				builder.setContentText("contenttext");
				builder.setContentTitle("contenttitle");
				builder.setSubText("sub text");
				builder.setNumber(10);
				builder.setWhen(System.currentTimeMillis());
				Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.gallery_photo_1)).getBitmap();
				builder.setLargeIcon(bm);
//				builder.setProgress(100, 50, false);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setContentIntent(PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this, MyActivity1.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0));
				builder.setDeleteIntent(PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this, MyActivity2.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0));
//				builder.setFullScreenIntent(PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this, MyActivity3.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0), false);
				nm.notify(mId++, builder.build());
				
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.ic_launcher, "tickerText", System.currentTimeMillis());
				notification.setLatestEventInfo(MainActivity.this, "contentTitle", "contentText", 
						PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this, MyActivity1.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0));
				nm.notify(mId++, notification);
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
