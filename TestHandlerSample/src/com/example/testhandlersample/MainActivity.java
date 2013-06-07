package com.example.testhandlersample;

import android.app.Activity;
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
	ProgressBar progressView;
	
	int mCount;
	
	public static final int MESSAGE_TYPE_PRE_UPDATE = 0;
	public static final int MESSAGE_TYPE_UPDATE = 1;
	public static final int MESSAGE_TYPE_POST_UPDATE = 2;
	
	Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_TYPE_PRE_UPDATE :
				String text = (String)msg.obj;
				messageView.setText("Pre Message : " + text);
				break;
			case MESSAGE_TYPE_UPDATE :
				int count = msg.arg1;
				messageView.setText("count : " + count);
				progressView.setProgress(count);
				break;
			case MESSAGE_TYPE_POST_UPDATE :
				String endtext = (String)msg.obj;
				messageView.setText("Post Message : " + endtext);
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.message);
		progressView = (ProgressBar)findViewById(R.id.progressBar1);
		progressView.setMax(20);
		Button btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCount = 0;
				progressView.setProgress(0);
				new MyTask().execute("");
//				mHandler.post(updateRunnable);
/*				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
//						mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_TYPE_PRE_UPDATE, "ready"));
						mHandler.post(new PreWork("ready"));
						while(mCount < 20) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							mCount++;
//							mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_TYPE_UPDATE, mCount, 0));
							mHandler.post(new UpdateWork(mCount));
						}
//						mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_TYPE_POST_UPDATE, "count end"));
						mHandler.post(new PostWork("count end"));
					}
				}).start();
*/
			}
		});
	}
	
	public class MyTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			messageView.setText("pre : ready");
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			while(mCount < 20) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mCount++;
				publishProgress(mCount);
			}
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			messageView.setText("count " + values[0]);
			progressView.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			messageView.setText("Post Work count end");
		}
		
	}
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mCount == 0) {
				messageView.setText("Pre : ready");
				mCount++;
				mHandler.postDelayed(this, 1000);
			} else if (mCount > 20) {
				messageView.setText("Post : count end");
			} else {
				messageView.setText("count : " + mCount);
				progressView.setProgress(mCount);
				mCount++;
				mHandler.postDelayed(this, 1000);
			}
		}
	};
	
	public class PreWork implements Runnable {

		String message;
		
		public PreWork(String message) {
			this.message = message;
		}
		
		@Override
		public void run() {
			messageView.setText("Pre Work : " + message);
		}
	}
	
	public class UpdateWork implements Runnable {

		int count;
		public UpdateWork(int count) {
			this.count = count;
		}
		
		@Override
		public void run() {
			messageView.setText("count : " + count);
			progressView.setProgress(count);
		}
	}
	
	public class PostWork implements Runnable {
		String message;
		public PostWork(String message) {
			this.message = message;
		}
		@Override
		public void run() {
			messageView.setText("Post Work : " + message);
		}
	}


	boolean isBackPressed = false;
	
	@Override
	public void onBackPressed() {
		if (!isBackPressed) {
			Toast.makeText(this, "one more time back key press", Toast.LENGTH_SHORT).show();
			isBackPressed = true;
			mHandler.postDelayed(cleanBackPressed, 2000);
		} else {
			finish();
		}
	}
	
	Runnable cleanBackPressed = new Runnable() {
		
		@Override
		public void run() {
			isBackPressed = false;
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
