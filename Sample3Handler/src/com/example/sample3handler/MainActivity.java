package com.example.sample3handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public static final int MESSAGE_TIMEOUT_BACK = 0;
	public static final int TIMEOUT_BACK_TIME = 2000;

	boolean isBackPressed = false;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_TIMEOUT_BACK:
				isBackPressed = false;
				break;
			}
		}
	};

	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			super.onBackPressed();
			return;
		}
		if (!isBackPressed) {
			isBackPressed = true;
			Toast.makeText(this, "Back Press one more", Toast.LENGTH_SHORT)
					.show();
			mHandler.sendMessageDelayed(
					mHandler.obtainMessage(MESSAGE_TIMEOUT_BACK),
					TIMEOUT_BACK_TIME);
		} else {
			mHandler.removeMessages(MESSAGE_TIMEOUT_BACK);
			super.onBackPressed();
		}
	}

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

		Handler mHandler = new Handler();
		public static final int TIME_INTERVAL = 1000;
		TextView messageView;
		int count = 0;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			messageView = (TextView) rootView.findViewById(R.id.messageView);
			Button btn = (Button) rootView.findViewById(R.id.button1);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!isRunning) {
						mHandler.post(countRunnable);
						isRunning = true;
					}
				}
			});
			btn = (Button) rootView.findViewById(R.id.button2);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mHandler.removeCallbacks(countRunnable);
					isRunning = false;
				}
			});
			return rootView;
		}

		boolean isRunning = false;
		Runnable countRunnable = new Runnable() {

			@Override
			public void run() {
				count++;
				messageView.setText("Count : " + count);
				mHandler.postDelayed(this, TIME_INTERVAL);
			}
		};
	}

}
