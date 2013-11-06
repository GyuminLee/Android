package com.example.hellomediaplayertest;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	MediaPlayer mPlayer;
	int mPlayerState = STATE_IDLE;
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_INITIALIZED = 1;
	public static final int STATE_PREPARED = 2;
	public static final int STATE_STARTED = 3;
	public static final int STATE_PAUSED = 4;
	public static final int STATE_STOPPED = 5;
	public static final int STATE_COMPLETED = 6;
	public static final int STATE_ERROR = 7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mPlayerState = STATE_PREPARED;
		Button btn = (Button)findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPlayerState == STATE_INITIALIZED || mPlayerState == STATE_STOPPED) {
					try {
						mPlayer.prepare();
						mPlayerState = STATE_PREPARED;
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if (mPlayerState == STATE_PREPARED ||
					mPlayerState == STATE_PAUSED ||
					mPlayerState == STATE_COMPLETED) {
					mPlayer.start();
					mPlayerState = STATE_STARTED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btnPause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPlayerState == STATE_STARTED) {
					mPlayer.pause();
					mPlayerState = STATE_PAUSED;
				}	
			}
		});
		
		btn = (Button)findViewById(R.id.btnStop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPlayerState == STATE_STARTED || 
					mPlayerState == STATE_PAUSED ||
					mPlayerState == STATE_COMPLETED) {
					mPlayer.stop();
					mPlayerState = STATE_STOPPED;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
