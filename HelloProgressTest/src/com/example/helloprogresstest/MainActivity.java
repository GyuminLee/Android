package com.example.helloprogresstest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Handler mHandler = new Handler();
	
	int count;
	ProgressBar progress;
	TextView messageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.messageView);
		progress = (ProgressBar)findViewById(R.id.progressBar1);
		progress.setMax(100);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count = 0;
				progress.setProgress(0);
				mHandler.post(runnable);
			}
		});
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(runnable);
			}
		});
		
		btn = (Button)findViewById(R.id.startAnimation);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTime = -1;
				mHandler.post(colorAni);
			}
		});
		
		btn = (Button)findViewById(R.id.stopAnimation);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(colorAni);
			}
		});
	}
	
	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			count++;
			if (count < 100) {
				mHandler.postDelayed(this, 200);
			}
			progress.setProgress(count);
		}
	};

	long startTime = -1;
	
	Runnable colorAni = new Runnable() {
		
		public final static int DURATION = 3000;
		
		@Override
		public void run() {
			if (startTime==-1) {
				startTime = System.currentTimeMillis();
			}
			long currentTime = System.currentTimeMillis();
			int interval = (int)(currentTime - startTime);
			int repeat = interval / DURATION;
			int gap = interval % DURATION;
			int redColor;
			if (repeat % 2 == 0) {
				redColor = ( 0xFF * gap ) / DURATION;
			} else {
				redColor = ( 0xFF * (DURATION - gap) / DURATION);
			}
			int color = Color.rgb(redColor, 0, 0);
			messageView.setTextColor(color);
			
			mHandler.postDelayed(this, 100);			
		}
	};
	
	
	boolean isBackPressed = false;
	Runnable backReset = new Runnable() {
		
		@Override
		public void run() {
			isBackPressed = false;
		}
	};
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		if (!isBackPressed) {
			Toast.makeText(this, "one more back key...", Toast.LENGTH_SHORT).show();
			isBackPressed = true;
			mHandler.postDelayed(backReset, 2000);
		} else {
			mHandler.removeCallbacks(backReset);
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
