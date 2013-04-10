package com.example.samplemediaplayertest;

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

	MediaPlayer mPlayer;
	private final static int NOT_CHANGED = 0;
	int mStoppedProgress = NOT_CHANGED;

	enum PlayerState {
		INITIALIZED,
		PREPARED,
		STARTED,
		PAUSED,
		STOPPED
	}
	
	PlayerState mState = PlayerState.INITIALIZED;
	SeekBar progressView;
	SeekBar volumeView;
	
	public final static int UPDATE_TIME = 200;
	
	Handler mHandler = new Handler();
	
	AudioManager mAudioManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = PlayerState.PREPARED;
		progressView = (SeekBar)findViewById(R.id.progress);
		progressView.setMax(mPlayer.getDuration());
		progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			int mProgress;
			private final static int NOT_PROGRESS = -1;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (mProgress != NOT_PROGRESS) {
					if (mState == PlayerState.STARTED || mState == PlayerState.PREPARED || mState == PlayerState.PAUSED) {
						mPlayer.seekTo(mProgress);
					} else {
						mStoppedProgress = mProgress;
					}
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				mProgress = NOT_PROGRESS;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					mProgress = progress;
				}
			}
		});
		volumeView = (SeekBar)findViewById(R.id.volumneBar);
		
		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		int maxvolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentvolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		volumeView.setMax(maxvolume);
		volumeView.setProgress(currentvolume);
		volumeView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				}
			}
		});
		
		Button btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mState == PlayerState.INITIALIZED || mState == PlayerState.STOPPED) {
					try {
						mPlayer.prepare();
						mPlayer.seekTo(mStoppedProgress);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mState = PlayerState.PREPARED;
				}
				if (mState == PlayerState.PREPARED || mState == PlayerState.PAUSED) {
					mPlayer.start();
					mHandler.post(mProgress);
					mState = PlayerState.STARTED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.pause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mState == PlayerState.STARTED) {
					mPlayer.pause();
					mHandler.removeCallbacks(mProgress);
					mState = PlayerState.PAUSED;
				}
				
			}
		});
		
		btn = (Button)findViewById(R.id.stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mState == PlayerState.PREPARED || mState == PlayerState.STARTED || mState == PlayerState.PAUSED) {
					mPlayer.stop();
					mHandler.removeCallbacks(mProgress);
					mStoppedProgress = NOT_CHANGED;
					progressView.setProgress(0);
					mState = PlayerState.STOPPED;
				}
			}
		});
	}

	Runnable mProgress = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mState == PlayerState.STARTED) {
				progressView.setProgress(mPlayer.getCurrentPosition());
				mHandler.postDelayed(this, UPDATE_TIME);
			}
		}
	};
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (mState == PlayerState.STARTED) {
			mPlayer.pause();
			mHandler.removeCallbacks(mProgress);
			mState = PlayerState.PAUSED;
		}
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
