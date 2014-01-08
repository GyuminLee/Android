package com.example.sample2threadtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView messageView;
	ProgressBar progressBar;
	TextView aniView;

	private boolean isBackPressed = false;
	
	public final static int MESSAGE_PROGRESS = 1;
	public final static int MESSAGE_PROGRESS_DONE = 2;
	
	public final static int TIMEOUT_BACK_PRESSED = 3;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_PROGRESS:
				int progress = msg.arg1;
				String text = (String) msg.obj;
				messageView.setText(text);
				progressBar.setProgress(progress);
				break;
			case MESSAGE_PROGRESS_DONE:
				messageView.setText("done");
				progressBar.setProgress(100);
				break;
			case TIMEOUT_BACK_PRESSED:
				isBackPressed = false;
				break;
			}
		}
	};
	
	@Override
	public void onBackPressed() {
		if (isBackPressed) {
			mHandler.removeMessages(TIMEOUT_BACK_PRESSED);
			finish();
		} else {
			isBackPressed = true;
			Toast.makeText(this, "one more back button", Toast.LENGTH_SHORT).show();
			mHandler.sendMessageDelayed(mHandler.obtainMessage(TIMEOUT_BACK_PRESSED), 2000);
		}
	}
	
	class MyRunnable implements Runnable {
		int progress;
		String text;
		public MyRunnable(int progress,String text) {
			this.progress = progress;
			this.text = text;
		}
		
		@Override
		public void run() {
			messageView.setText(text);
			progressBar.setProgress(progress);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.messageView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		aniView = (TextView)findViewById(R.id.textView1);
		progressBar.setMax(100);
		progressBar.setProgress(0);
		messageView.setText("progress : 0");
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						int count = 0;
						while (count < 100) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							count += 5;
							MyRunnable r = new MyRunnable(count, "progress : " + count);
							mHandler.post(r);
//							mHandler.sendMessage(mHandler.obtainMessage(
//									MESSAGE_PROGRESS, count, 0, "proress : "
//											+ count));
						}
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								messageView.setText("done");
								progressBar.setProgress(100);
							}
						});
//						mHandler.sendMessage(mHandler
//								.obtainMessage(MESSAGE_PROGRESS_DONE));
					}
				}).start();
			}
		});
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MyTask().execute("");
			}
		});
		
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ani.starttime = MyAnimationRunnable.NO_START_TIME;
				mHandler.post(ani);
			}
		});
		btn = (Button)findViewById(R.id.button4);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(ani);
			}
		});
	}
	
	MyAnimationRunnable ani = new MyAnimationRunnable();
	
	class MyAnimationRunnable implements Runnable {
		public static final int NO_START_TIME = 1;
		int percent = 0;
		long starttime = NO_START_TIME;
		int duration = 1000;
		
		@Override
		public void run() {
			long now = System.currentTimeMillis();
			if (starttime == NO_START_TIME) {
				starttime = now;
			}
			int interval = (int)(now - starttime) % duration;
			boolean reverse = (((now - starttime) / duration) % 2) == 0? false : true;
			percent = interval * 100 / duration;
			if (reverse) {
				percent = 100 - percent;
			}
//			percent = (percent + 10) % 110;
			int r = 0xFF * percent / 100;
			int color = Color.rgb(r, r, r);
			aniView.setTextColor(color);
			mHandler.postDelayed(this, 100);
		}
		
	}
	
	
	
	class MyTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			// ....
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			int count = 0;
			while (count < 100) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count += 5;
				publishProgress(count);
			}
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			int progress = values[0];
			messageView.setText("progress : " + progress);
			progressBar.setProgress(progress);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			messageView.setText("done");
			progressBar.setProgress(100);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
