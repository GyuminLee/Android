package com.example.testthreadsample;

import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Runnable {

	TextView tv;
	
	
	static final int MESSAGE_ID_UPDATE_TEXTVIEW = 1;
	
	
//	Handler mHandler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			switch(msg.what) {
//			case MESSAGE_ID_UPDATE_TEXTVIEW :
//				String text = (String)msg.obj;
//				tv.setText(text);
//				break;
//			}
//		};
//	};
	
	Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.textView1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				new Thread(MainActivity.this).start();
				new MyTask().execute(new URL[0]);
			}
		});
	}

	class MyTask extends AsyncTask<URL, String, Integer> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected Integer doInBackground(URL... params) {
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
				publishProgress("count : " + count);
				// ...
			}
			return count;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			tv.setText(values[0]);
			
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(MainActivity.this, "progress end", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	int mCount;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		mCount = 0;
		while(mCount < 20) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mCount++;
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					tv.setText("count : " + mCount);
				}
			});
//			Message msg = mHandler.obtainMessage(MESSAGE_ID_UPDATE_TEXTVIEW, "count : " + count);
////			mHandler.sendMessage(msg);
//			mHandler.sendMessageDelayed(msg, 1000);
////			tv.setText("count : " + count);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
