package com.example.sample3videomediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

		MediaPlayer mPlayer;
		public static final int PLAYER_STATE_IDLE = 0;
		public static final int PLAYER_STATE_STARTED = 1;
		public static final int PLAYER_STATE_PAUSED = 2;
		
		int mPlayerState;
		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mPlayer = new MediaPlayer();
			mPlayerState = PLAYER_STATE_IDLE;
		}
		
		@Override
		public void onDestroy() {
			mPlayer.release();
			super.onDestroy();
		}
		
		@Override
		public void onPause() {
			if (mPlayerState == PLAYER_STATE_STARTED) {
				mPlayer.pause();
				mPlayerState = PLAYER_STATE_PAUSED;
			}
			super.onPause();
		}
		
		SurfaceView screen;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			screen = (SurfaceView)rootView.findViewById(R.id.surfaceView1);
			mPlayer.setDisplay(screen.getHolder());
			Button btn = (Button)rootView.findViewById(R.id.btnStart);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mPlayerState == PLAYER_STATE_PAUSED) {
						mPlayer.start();
						mPlayerState = PLAYER_STATE_STARTED;
					}
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnPause);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mPlayerState == PLAYER_STATE_STARTED) {
						mPlayer.pause();
						mPlayerState = PLAYER_STATE_PAUSED;
					}
				}
			});
			
			btn = (Button)rootView.findViewById(R.id.btnLoad);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), VideoListActivity.class);
					startActivityForResult(i,0);
				}
			});
			return rootView;
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
				Uri uri = data.getData();
				mPlayer.reset();
				mPlayerState = PLAYER_STATE_IDLE;
				try {
					mPlayer.setDisplay(screen.getHolder());
					mPlayer.setDataSource(getActivity(), uri);
					mPlayer.prepare();
					mPlayerState = PLAYER_STATE_PAUSED;
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
		
	}

}
