package com.example.samplethreadtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView countView;
	
	private final static String TAG = "MainActivity";
		
	class MyHandler extends Handler {
		public static final int MESSAGE_TYPE_ONE = 1;
		public static final int MESSAGE_TYPE_TWO = 2;
		public static final int MESSAGE_TYPE_TEXTVIEW_UPDATE = 3;

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_TYPE_ONE :
				break;
			case MESSAGE_TYPE_TWO :
				break;
			case MESSAGE_TYPE_TEXTVIEW_UPDATE :
				String text = (String)msg.obj;
				countView.setText(text);
				break;
			}
		}
	}
	
	MyHandler mHandler = new MyHandler();
	
	Handler mPostHandler = new Handler();
	
	
	int mCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		countView = (TextView)findViewById(R.id.countView);
		Button btn = (Button)findViewById(R.id.startCount);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int count = 0;
						while (count < 20) {
							try {
								Thread.sleep(1000);
								count++;
							} catch (Exception e) {
								// TODO Auto-generated catch block
//								e.printStackTrace();
							}
							Log.i(TAG, "count : " + count);
//							countView.setText("count : " + count);
//							mHandler.sendMessage(
//									mHandler.obtainMessage(MyHandler.MESSAGE_TYPE_TEXTVIEW_UPDATE, "count : " + count)
//									);
							mPostHandler.post(new CountRunnable(count));

						}
					}
				}).start();
				
			}
		});
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCount = 0;
				mPostHandler.postDelayed(updateRunnable, 1000);
			}
		});
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPostHandler.removeCallbacks(updateRunnable);
			}
		});
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new MyAsyncTask().execute("");
			}
		});
	}
	
	
	class MyAsyncTask extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			int count = 0;
			while(count < 20) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
				publishProgress(count);
			}
			return count;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			int count = values[0];
			countView.setText("count : " + count);
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			countView.setText("count : end...");
			super.onPostExecute(result);
		}
		
	}
	
	
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mCount++;
			countView.setText("count : " + mCount);
			if (mCount < 20) {
				mPostHandler.postDelayed(this, 1000);
			}
		}
	};

	class CountRunnable implements Runnable {
		int mCount;
		
		public CountRunnable(int count) {
			mCount = count;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			countView.setText("count : " + mCount);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
