package com.example.sample2mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

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
	
	SeekBar progressView;
	SeekBar volumeView;
	AudioManager mAudioManager;
	
	Handler mHandler = new Handler();

	final static int UPDATE_INTERVAL = 200;
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (mPlayerState == PLAYER_STATE_STARTED) {
				progressView.setProgress(mPlayer.getCurrentPosition());
				mHandler.postDelayed(this, UPDATE_INTERVAL);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressView = (SeekBar)findViewById(R.id.progressView);
		volumeView = (SeekBar)findViewById(R.id.volumeView);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mPlayerState = PLAYER_STATE_PREPARED;
		progressView.setMax(mPlayer.getDuration());
		progressView.setProgress(0);
		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volumeView.setMax(maxVolume);
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		volumeView.setProgress(currentVolume);
		volumeView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
			}
		});
		progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int current;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (mPlayerState == PLAYER_STATE_STARTED) {
					mPlayer.seekTo(current);
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					current = progress;
				}
			}
		});
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
					mPlayer.seekTo(progressView.getProgress());
					mPlayer.start();
					mPlayerState = PLAYER_STATE_STARTED;
					mHandler.post(updateRunnable);
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
					progressView.setProgress(0);
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
