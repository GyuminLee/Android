package com.example.testappwidgetsample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyAppWedgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
		views.setTextViewText(R.id.textView1, "update text");
		views.setImageViewResource(R.id.imageView1, R.drawable.gallery_photo_1);
		views.setProgressBar(R.id.progressBar1, 100, 50, false);
//		views.setString(R.id.compound, "setData", "values");
		
		Intent i = new Intent(context, MainActivity.class);
		
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		
		views.setOnClickPendingIntent(R.id.imageView1, pi);
		
		for (int id : appWidgetIds) {
			appWidgetManager.updateAppWidget(id, views);
		}
	}

	
}
