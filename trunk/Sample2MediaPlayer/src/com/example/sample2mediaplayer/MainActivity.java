package com.example.sample2mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	final static int PLAYER_STATE_IDLE = 0;
	final static int PLAYER_STATE_INITIALIZED = 1;
	final static int PLAYER_STATE_PREPARED = 2;
	final static int PLAYER_STATE_STARTED = 3;
	final static int PLAYER_STATE_PAUSED = 4;
	final static int PLAYER_STATE_COMPLETED = 5;
	final static int PLAYER_STATE_STOPPED = 6;

	MediaPlayer mPlayer;
	int mPlayerState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mPlayerState = PLAYER_STATE_PREPARED;
		Button btn = (Button) findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPlayerState == PLAYER_STATE_INITIALIZED
						|| mPlayerState == PLAYER_STATE_STOPPED) {
					try {
						mPlayer.prepare();
						mPlayerState = PLAYER_STATE_PREPARED;
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (mPlayerState == PLAYER_STATE_PREPARED
						|| mPlayerState == PLAYER_STATE_PAUSED
						|| mPlayerState == PLAYER_STATE_COMPLETED) {
					mPlayer.start();
					mPlayerState = PLAYER_STATE_STARTED;
				}
			}
		});

		btn = (Button) findViewById(R.id.btnPause);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPlayerState == PLAYER_STATE_STARTED) {
					mPlayer.pause();
					mPlayerState = PLAYER_STATE_PAUSED;
				}
			}
		});

		btn = (Button) findViewById(R.id.btnStop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPlayerState == PLAYER_STATE_STARTED
						|| mPlayerState == PLAYER_STATE_PAUSED
						|| mPlayerState == PLAYER_STATE_COMPLETED) {
					mPlayer.stop();
					mPlayerState = PLAYER_STATE_STOPPED;
				}

			}
		});
		
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mPlayerState = PLAYER_STATE_COMPLETED;
			}
		});
		
		mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mPlayer.reset();
				mPlayerState = PLAYER_STATE_IDLE;
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		super.onDestroy();
	}
}
