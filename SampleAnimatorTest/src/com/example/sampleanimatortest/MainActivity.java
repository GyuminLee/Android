package com.example.sampleanimatortest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Handler mHandler = new Handler();
	
	long startAnimationTime;
	
	TextView animTextView;
	
	private final static int UPDATE_TIME = 100;
	private final static int DURATION_TIME = 1000;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		animTextView = (TextView)findViewById(R.id.animText);
		Button btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAnimationTime = System.currentTimeMillis();
				mHandler.post(updateRunnable);
			}
		});
		
		btn = (Button)findViewById(R.id.stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mHandler.removeCallbacks(updateRunnable);
			}
		});
	}

	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// animation 시간
			// color
			
			int color = 0;
			long current = System.currentTimeMillis();
			long delay = current - startAnimationTime;
			int remain = (int)delay % DURATION_TIME;
			int count = ((int)delay / DURATION_TIME) % 2;
			if (count == 0) {
				int r = ( 0xFF * (DURATION_TIME - remain) ) / DURATION_TIME;
				color = Color.rgb(r, r, r);
			} else {
				int r = ( 0xFF * remain) / DURATION_TIME;
				color = Color.rgb(r, r, r);
			}
			
			animTextView.setTextColor(color);
			mHandler.postDelayed(this, UPDATE_TIME);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
