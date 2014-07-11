package com.example.sample4mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
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
	
	SeekBar progressView;
	Handler mHandler = new Handler();
	boolean isProgressPressed = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressView = (SeekBar)findViewById(R.id.seekBar1);
		
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = STATE_PREPARED;
		progressView.setMax(mPlayer.getDuration());
		progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progress;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (mState == STATE_STARTED) {
					mPlayer.seekTo(progress);
				}
				isProgressPressed = false;
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				isProgressPressed = true;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					this.progress = progress;
				}
			}
		});
		
		Button btn = (Button)findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == STATE_INITIALIZED || mState == STATE_STOPPED) {
					try {
						mPlayer.prepare();
						mState = STATE_PREPARED;
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if (mState == STATE_PREPARED || mState == STATE_PAUSED || mState == STATE_COMPLETED) {
					mPlayer.seekTo(progressView.getProgress());
					mPlayer.start();
					mState = STATE_STARTED;
					mHandler.post(progressRunnable);
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
					progressView.setProgress(0);
				}
			}
		});
		
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(MainActivity.this, "completed", Toast.LENGTH_SHORT).show();
				mState = STATE_COMPLETED;
				progressView.setProgress(0);
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
	
	public static final int INTERVAL = 200;
	
	Runnable progressRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (mState == STATE_STARTED) {
				if (!isProgressPressed) {
					progressView.setProgress(mPlayer.getCurrentPosition());
				}
				mHandler.postDelayed(this, INTERVAL);
			}
			
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
		}
	}
}
