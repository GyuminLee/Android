package com.example.sample3appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
		views.setTextViewText(R.id.textView1, "AppWidgetTest...");
		views.setImageViewResource(R.id.imageView1, R.drawable.ic_launcher);
		Intent i = new Intent(context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		views.setOnClickPendingIntent(R.id.imageView1, pi);
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}
}
