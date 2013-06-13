package com.example.testappwidgetsample2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
		views.setTextViewText(R.id.message, "message!!!");
		views.setImageViewResource(R.id.icon, R.drawable.gallery_photo_1);
		Intent i = new Intent(context, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		views.setOnClickPendingIntent(R.id.message, pi);
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}

	

}
