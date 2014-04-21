package com.example.sample3notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		NotificationManager mNM;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNM = (NotificationManager) getActivity().getSystemService(
					Context.NOTIFICATION_SERVICE);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			Button btn = (Button) rootView
					.findViewById(R.id.btnSendNotification);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			return rootView;
		}

		Handler mHandler = new Handler();
		int progress = 0;
		int mProgressId = 1;
		Runnable updateNotification = new Runnable() {

			@Override
			public void run() {
				if (progress <= 100) {
					NotificationCompat.Builder builder = new NotificationCompat.Builder(
							getActivity());
					builder.setSmallIcon(R.drawable.ic_launcher);
					builder.setTicker("Notification Test...");
					builder.setContentTitle("Noti Test");
					// builder.setContentText("this is noti test...");
					builder.setWhen(System.currentTimeMillis());
					Intent i = new Intent(getActivity(), MyActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					PendingIntent pi = PendingIntent.getActivity(getActivity(),
							0, i, 0);
					builder.setContentIntent(pi);
//					builder.setDeleteIntent(pi);
					builder.setDefaults(Notification.DEFAULT_ALL);
					builder.setAutoCancel(true);
					builder.setOngoing(true);
					builder.setProgress(100, progress, false);
					mNM.notify(mProgressId, builder.build());
					progress += 10;
					mHandler.postDelayed(this, 1000);
				} else {
					mNM.cancel(mProgressId);
				}
			}
		};
	}

}
