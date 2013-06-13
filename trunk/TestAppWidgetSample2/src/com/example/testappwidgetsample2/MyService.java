package com.example.testappwidgetsample2;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MyService extends Service {

	int mCount = 0;
	int[] resIds = { R.drawable.gallery_photo_1,
			R.drawable.gallery_photo_2,
			R.drawable.gallery_photo_3,
			R.drawable.gallery_photo_4,
			R.drawable.gallery_photo_5,
			R.drawable.gallery_photo_6,
			R.drawable.gallery_photo_7,
			R.drawable.gallery_photo_8};
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		mCount++;
		
		AppWidgetManager awm = AppWidgetManager.getInstance(this);
		
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_widget_layout);
		views.setTextViewText(R.id.message, "message : " + mCount);
		views.setImageViewResource(R.id.icon, resIds[mCount % resIds.length]);
		Intent i = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		views.setOnClickPendingIntent(R.id.message, pi );
		
		ComponentName cn = new ComponentName(this, MyAppWidgetProvider.class);
		awm.updateAppWidget(cn, views);

		return super.onStartCommand(intent, flags, startId);
	}
}
