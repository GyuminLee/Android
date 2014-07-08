package com.example.sample4handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int NOT_INTIALIZE = -1;
	
	private static final int MESSAGE_FINISH_TIMEOUT = 0;
	private static final int BACK_PRESS_TIMEOUT = 2000;
	boolean isBackPressed = false;
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_FINISH_TIMEOUT :
				isBackPressed = false;
				break;
			}
		}
	};
	TextView messageView;
	int mCount = 0;
	long mStartTime = NOT_INTIALIZE;
	
	@Override
	public void onBackPressed() {
		if (!isBackPressed) {
			isBackPressed = true;
			Toast.makeText(this, "back press", Toast.LENGTH_SHORT).show();
			mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_FINISH_TIMEOUT), BACK_PRESS_TIMEOUT);			
		} else {
			mHandler.removeMessages(MESSAGE_FINISH_TIMEOUT);
			finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.message_view);
		Button btn = (Button)findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mStartTime == NOT_INTIALIZE) {
					mStartTime = System.currentTimeMillis();
				}				
				mHandler.post(updateRunnable);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(updateRunnable);
			}
		});
	}
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			long current = System.currentTimeMillis();
			long delay = 1000 - (current - mStartTime) % 1000;
			mCount = (int)((current - mStartTime) / 1000);
			messageView.setText("count : " + mCount);
			mHandler.postDelayed(this, delay);
		}
	};
}
