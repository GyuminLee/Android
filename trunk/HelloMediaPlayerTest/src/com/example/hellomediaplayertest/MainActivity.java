package com.example.hellomediaplayertest;

import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

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

	SeekBar currentView, volumnView;
	AudioManager mAudioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mPlayerState = STATE_PREPARED;
		currentView = (SeekBar) findViewById(R.id.currentView);
		volumnView = (SeekBar) findViewById(R.id.volumneView);

		Button btn = (Button) findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPlayerState == STATE_INITIALIZED
						|| mPlayerState == STATE_STOPPED) {
					try {
						mPlayer.prepare();
						mPlayerState = STATE_PREPARED;
						mPlayer.seekTo(currentView.getProgress());
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (mPlayerState == STATE_PREPARED
						|| mPlayerState == STATE_PAUSED
						|| mPlayerState == STATE_COMPLETED) {
					mPlayer.start();
					mPlayerState = STATE_STARTED;
					mHandler.post(updateCurrent);
				}
			}
		});

		btn = (Button) findViewById(R.id.btnPause);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPlayerState == STATE_STARTED) {
					mPlayer.pause();
					mPlayerState = STATE_PAUSED;
				}
			}
		});

		btn = (Button) findViewById(R.id.btnStop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPlayerState == STATE_STARTED
						|| mPlayerState == STATE_PAUSED
						|| mPlayerState == STATE_COMPLETED) {
					mPlayer.stop();
					mPlayerState = STATE_STOPPED;
					currentView.setProgress(0);
				}
			}
		});
		if (mPlayerState == STATE_PREPARED) {
			int duration = mPlayer.getDuration();
			currentView.setMax(duration);
		}
		currentView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int current;

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (mPlayerState == STATE_STARTED
						|| mPlayerState == STATE_PAUSED
						|| mPlayerState == STATE_COMPLETED
						|| mPlayerState == STATE_PREPARED) {
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

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volumnView.setMax(max);
		int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		volumnView.setProgress(current);
		volumnView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
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
		
		btn = (Button)findViewById(R.id.btnMusic);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MusicListActivity.class);
				startActivityForResult(i, 0);	
			}
		});
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
		
		registerReceiver(mReceiver, filter);
	}

	Boolean mIsPlugging = null;
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int state = intent.getIntExtra("state", -1);
			boolean plugged;
			if (state == 0) {
				plugged = false;
			} else if (state == 1) {
				plugged = true;
			} else {
				return;
			}
			if (mIsPlugging == null) {
				mIsPlugging = plugged;
			}
			
			if (mIsPlugging != plugged) {
				if (mIsPlugging == true && plugged == false) {
					if (mPlayerState == STATE_STARTED) {
						mPlayer.pause();
						mPlayerState = STATE_PAUSED;
					}
				}
				mIsPlugging = plugged;
			}
		}
		
	};
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			mPlayer.reset();
			mPlayerState = STATE_IDLE;
			try {
				String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST , MediaStore.Audio.Media.DATA};
				Cursor c = getContentResolver().query(uri, projection, null, null, null);
				if (c.moveToNext()) {
					String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
					// ...
				}
				c.close();
				mPlayer.setDataSource(this, uri);
				mPlayerState = STATE_INITIALIZED;
				mPlayer.prepare();
				mPlayerState = STATE_PREPARED;
				int duration = mPlayer.getDuration();
				currentView.setMax(duration);
				currentView.setProgress(0);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		if (mPlayerState == STATE_STARTED) {
			mPlayer.pause();
			mPlayerState = STATE_PAUSED;
		}
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		mPlayer.release();
		mPlayer = null;
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	public static final int UPDATE_INTERVAL = 100;
	Handler mHandler = new Handler();
	Runnable updateCurrent = new Runnable() {

		@Override
		public void run() {
			if (mPlayerState == STATE_STARTED) {
				int current = mPlayer.getCurrentPosition();
				currentView.setProgress(current);
				mHandler.postDelayed(this, UPDATE_INTERVAL);
			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
