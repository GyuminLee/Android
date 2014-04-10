package com.example.sample3thread;

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
import android.widget.ProgressBar;
import android.widget.TextView;

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

		TextView messageView;
		ProgressBar progressView;
		public static final int MESSAGE_IN_PROGRESS = 0;
		public static final int MESSAGE_PROGRESS_DONE = 1;
		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what) {
				case MESSAGE_IN_PROGRESS :
					int progress = msg.arg1;
					progressView.setProgress(progress);
					messageView.setText("progress : " + progress);
					break;
				case MESSAGE_PROGRESS_DONE :
					messageView.setText("progress done");
					break;
				}
			}
		};
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			messageView = (TextView)rootView.findViewById(R.id.textView1);
			progressView = (ProgressBar)rootView.findViewById(R.id.progressBar1);
			progressView.setMax(100);
			progressView.setProgress(0);
			messageView.setText("progress : 0");
			Button btn = (Button)rootView.findViewById(R.id.btnStart);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new Thread(new Runnable() {
						int progress = 0;
						@Override
						public void run() {
							while(progress <= 100) {
								try {
//									Message msg = mHandler.obtainMessage(MESSAGE_IN_PROGRESS, progress, 0);
//									mHandler.sendMessage(msg);
									mHandler.post(new UpdateProgressRunnable(progress));
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								progress+=5;
							}
//							mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_PROGRESS_DONE));
							mHandler.post(new UpdateDoneRunnable());
						}
					}).start();
				}
			});
			return rootView;
		}
		
		public class UpdateProgressRunnable implements Runnable {

			int progress;
			
			public UpdateProgressRunnable(int progress) {
				this.progress = progress;
			}
			
			@Override
			public void run() {
				progressView.setProgress(progress);
				messageView.setText("progress : " + progress);
			}
			
		}
		
		public class UpdateDoneRunnable implements Runnable {

			@Override
			public void run() {
				messageView.setText("progress done");
			}
			
		}
	}


}
