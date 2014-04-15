package com.example.sample3mediaplayer;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		
		public PlaceholderFragment() {
		}
		
		public static final int PLAYER_IDLE = 0;
		public static final int PLAYER_INITIALIZED = 1;
		public static final int PLAYER_PREPARED = 2;
		public static final int PLAYER_STARTED = 3;
		public static final int PLAYER_PAUSED = 4;
		public static final int PLAYER_COMPLETED = 5;
		public static final int PLAYER_STOPPED = 6;
		public static final int PLAYER_ERROR = 7;
		
		MediaPlayer mPlayer;
		int mPlayerState = PLAYER_IDLE;
		SeekBar progressView;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mPlayer = MediaPlayer.create(getActivity(), R.raw.winter_blues);
			mPlayerState = PLAYER_PREPARED;
			mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					mPlayerState = PLAYER_COMPLETED;
				}
			});
						
			mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					mPlayerState = PLAYER_ERROR;
					return false;
				}
			});
			
		}

		@Override
		public void onDestroy() {
			mPlayer.release();
			mPlayer = null;
			super.onDestroy();
		}
		
		boolean isPausedMusic = false;
		
		@Override
		public void onPause() {
			if (mPlayerState == PLAYER_STARTED) {
				mPlayer.pause();
				isPausedMusic = true;
				mPlayerState = PLAYER_PAUSED;
			}
			super.onPause();
		}
		
		@Override
		public void onResume() {
			if (isPausedMusic) {
				isPausedMusic = false;
				if (mPlayerState == PLAYER_PAUSED) {
					play();
				}
			}
			super.onResume();
		}
		
		public static final int INTERVAL_TIME = 200;
		Handler mHandler = new Handler();
		
		Runnable updateProgress = new Runnable() {
			
			@Override
			public void run() {
				if (mPlayerState == PLAYER_STARTED) {
					if (!isStartTracking) {
						progressView.setProgress(mPlayer.getCurrentPosition());
					}
					mHandler.postDelayed(this, INTERVAL_TIME);
				}
			}
		};
		
		boolean isStartTracking = false;
		
		
		public void play() {
			mPlayer.seekTo(progressView.getProgress());
			mPlayer.start();
			mPlayerState = PLAYER_STARTED;
			mHandler.post(updateProgress);
		}
		
		SeekBar volumeView;
		AudioManager mAudioManager;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			progressView = (SeekBar)rootView.findViewById(R.id.progressView);
			if (mPlayerState == PLAYER_PREPARED) {
				progressView.setMax(mPlayer.getDuration());
				progressView.setProgress(0);
			}
			progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				int progress;
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					isStartTracking = false;
					if (mPlayerState == PLAYER_STARTED) {
						mPlayer.seekTo(progress);
					}
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					
					isStartTracking = true;
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					if (fromUser) {
						this.progress = progress;
					}
				}
			});
			volumeView = (SeekBar)rootView.findViewById(R.id.volumeView);
			mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
			int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volumeView.setMax(maxVolume);
			int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			volumeView.setProgress(currentVolume);
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
					if (fromUser) {
						mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
					}
				}
			});
			Button btn = (Button)rootView.findViewById(R.id.btnStart);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mPlayerState == PLAYER_INITIALIZED || 
							mPlayerState == PLAYER_STOPPED) {
						try {
							mPlayer.prepare();
							mPlayerState = PLAYER_PREPARED;
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (mPlayerState == PLAYER_PAUSED ||
							mPlayerState == PLAYER_PREPARED ||
							mPlayerState == PLAYER_COMPLETED) {
						play();
					}
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnPause);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mPlayerState == PLAYER_STARTED) {
						mPlayer.pause();
						mPlayerState = PLAYER_PAUSED;
					}
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnStop);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mPlayerState == PLAYER_STARTED ||
							mPlayerState == PLAYER_PAUSED ||
							mPlayerState == PLAYER_COMPLETED) {
						mPlayer.stop();
						mPlayerState = PLAYER_STOPPED;
					}
				}
			});
			return rootView;
		}
	}

}
