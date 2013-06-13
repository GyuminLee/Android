package com.example.testmediaplayersample;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

	MediaPlayer mPlayer = null;

	PlayerState mState;

	enum PlayerState {
		IDLE, INITIALIZED, PREPARED, STARTED, PAUSED, STOPED, COMPLTETED, ERROR
	}

	SeekBar progressView;
	SeekBar volumnView;
	ListView list;
	Handler mHandler = new Handler();
	AudioManager mAudioManager;
	TextView titleView;

	SimpleCursorAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = PlayerState.PREPARED;
		progressView = (SeekBar) findViewById(R.id.progress);
		volumnView = (SeekBar) findViewById(R.id.volumn);
		list = (ListView) findViewById(R.id.listView1);
		titleView = (TextView)findViewById(R.id.title);
		
		String[] from = { MediaStore.Audio.Media.TITLE };
		int[] to = {android.R.id.text1};
		
		mAdapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_1, 
				null, 
				from, 
				to, 
				0);
		list.setAdapter(mAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
				
				Cursor c = (Cursor)mAdapter.getItem(position);
				int titleIndex = c.getColumnIndex(MediaStore.Audio.Media.TITLE);
				String title = c.getString(titleIndex);
				titleView.setText(title);
				
				stop();
				mPlayer.reset();
				mState = PlayerState.IDLE;
				try {
					mPlayer.setDataSource(MainActivity.this, contentUri);
					mState = PlayerState.INITIALIZED;
					mPlayer.prepare();
					mState = PlayerState.PREPARED;
					int duration = mPlayer.getDuration();
					progressView.setMax(duration);
					progressView.setProgress(0);
					play();
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
		});
		getSupportLoaderManager().initLoader(0, null, this);
		
		int duration = mPlayer.getDuration();
		progressView.setMax(duration);
		progressView.setProgress(0);
		
		progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			int progress = -1;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (mState == PlayerState.STARTED) {
					mPlayer.seekTo(progress);
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				progress = seekBar.getProgress();
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					this.progress = progress;
				}
			}
		});

		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volumnView.setMax(maxVolume);
		int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		volumnView.setProgress(current);
		volumnView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
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
				if (fromUser) {
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				}
			}
		});
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mState = PlayerState.COMPLTETED;
				progressView.setProgress(0);
			}
		});

		Button btn = (Button) findViewById(R.id.btnPlay);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				play();
			}
		});

		btn = (Button) findViewById(R.id.btnPause);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pause();
			}
		});

		btn = (Button) findViewById(R.id.btnStop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stop();
			}
		});
		registerReceiver(handsetReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
	}
	
	BroadcastReceiver handsetReceiver = new BroadcastReceiver() {
		boolean isFirst = true;
		boolean currentPlug = true;
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (isFirst) {
				int state = intent.getIntExtra("state", -1);
				if (state == 0) {
					currentPlug = false;
				} else if (state == 1) {
					currentPlug = true;
				}
				isFirst = false;
				return;
			}
			
			int state = intent.getIntExtra("state", -1);
			if (state == 0) {
				if (currentPlug == true) {
					pause();
				}
			}
		}
	};
	
	public final static int UPDATE_INTERVAL = 100;
	
	Runnable progressUpdate = new Runnable() {
		
		public void run() {
			if (mState == PlayerState.STARTED) {
				int current = mPlayer.getCurrentPosition();
				progressView.setProgress(current);
				mHandler.postDelayed(this, UPDATE_INTERVAL);
			}
		}
	};

	private void play() {
		if (mState == PlayerState.INITIALIZED || mState == PlayerState.STOPED) {
			try {
				mPlayer.prepare();
				mState = PlayerState.PREPARED;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (mState == PlayerState.PREPARED || mState == PlayerState.PAUSED
				|| mState == PlayerState.COMPLTETED) {
			int current = progressView.getProgress();
			mPlayer.seekTo(current);
			mPlayer.start();
			mState = PlayerState.STARTED;
			mHandler.post(progressUpdate);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		pause();
		super.onStop();
	}
	
	private void pause() {
		if (mState == PlayerState.STARTED) {
			mPlayer.pause();
			mState = PlayerState.PAUSED;
			mHandler.removeCallbacks(progressUpdate);
		}
	}

	private void stop() {
		if (mState == PlayerState.STARTED || mState == PlayerState.PAUSED
				|| mState == PlayerState.COMPLTETED
				|| mState == PlayerState.PREPARED) {
			mPlayer.stop();
			mState = PlayerState.STOPED;
			progressView.setProgress(0);
			mHandler.removeCallbacks(progressUpdate);
		}
	}

	@Override
	protected void onDestroy() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		unregisterReceiver(handsetReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle args) {
		String[] columns = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE};
		String selection = "((" + MediaStore.Audio.Media.TITLE + " NOTNULL) AND ("
	            + MediaStore.Audio.Media.TITLE + " != '' ))";
		String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE LOCALIZED ASC";
		return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, selection, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

}
