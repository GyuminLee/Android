package com.example.sample4mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	MediaPlayer mPlayer;
	int mState;
	public static final int STATE_IDLE = 1;
	public static final int STATE_INITIALIZED = 2;
	public static final int STATE_PREPARED = 3;
	public static final int STATE_STARTED = 4;
	public static final int STATE_PAUSED = 5;
	public static final int STATE_COMPLETED = 6;
	public static final int STATE_STOPPED = 7;
	public static final int STATE_ERROR = 8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = STATE_PREPARED;
		
		Button btn = (Button)findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == STATE_INITIALIZED || mState == STATE_STOPPED) {
					try {
						mPlayer.prepare();
						mState = STATE_PREPARED;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (mState == STATE_PREPARED || mState == STATE_PAUSED || mState == STATE_COMPLETED) {
					mPlayer.start();
					mState = STATE_STARTED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_pause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == STATE_STARTED) {
					mPlayer.pause();
					mState = STATE_PAUSED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == STATE_STARTED || mState == STATE_PAUSED || mState == STATE_COMPLETED) {
					mPlayer.stop();
					mState = STATE_STOPPED;
				}
			}
		});
		
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(MainActivity.this, "completed", Toast.LENGTH_SHORT).show();
				mState = STATE_COMPLETED;
			}
		});
		
		mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mState = STATE_ERROR;
				return false;
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
		}
	}
}
