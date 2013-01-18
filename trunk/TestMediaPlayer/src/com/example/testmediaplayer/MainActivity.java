package com.example.testmediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	MediaPlayer mPlayer;
	Handler mHandler = new Handler();
	SeekBar mSeekBar;
	boolean isStopped = false;
	boolean isSeekChanged = false;
	
	int mPosition = -1;
	final static int UPDATE_INTERVAL = 200;
	
	Runnable updateRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int postion = mPlayer.getCurrentPosition();
			if (!isSeekChanged) {
				mSeekBar.setProgress(postion);
			}
			
			mHandler.postDelayed(updateRunnable, UPDATE_INTERVAL);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSeekBar = (SeekBar)findViewById(R.id.seekBar1);
		
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int currentProgress = -1;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (currentProgress != -1) {
					if (!isStopped) {
						mPlayer.seekTo(currentProgress);
						mPosition = -1;
					} else {
						mPosition = currentProgress;
					}
				}
				currentProgress = -1;
				isSeekChanged = false;
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				isSeekChanged = true;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					currentProgress = progress;
				}
			}
		});
		
		Button btn = (Button)findViewById(R.id.button1);
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isStopped) {
					try {
						mPlayer.prepare();
						mPlayer.seekTo(mPosition);
						mPosition = -1;
						isStopped = false;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mPlayer.start();
				mHandler.postDelayed(updateRunnable, UPDATE_INTERVAL);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPlayer.pause();
				mHandler.removeCallbacks(updateRunnable);
			}
		});
		
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isStopped = true;
				mSeekBar.setProgress(0);
				mPosition = 0;
				mPlayer.stop();
				mHandler.removeCallbacks(updateRunnable);
			}
		});
		
		
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
				
		int max = mPlayer.getDuration();
		mSeekBar.setMax(max);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (!isStopped) {
			mPlayer.stop();
			isStopped = true;
			mSeekBar.setProgress(0);
			mPosition = 0;
			mHandler.removeCallbacks(updateRunnable);
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mPlayer.release();
		mPlayer = null;
		super.onDestroy();
	}

}
