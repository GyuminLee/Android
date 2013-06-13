package com.example.testmediaplayersample;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

public class MainActivity extends FragmentActivity {

	MediaPlayer mPlayer = null;

	PlayerState mState;

	enum PlayerState {
		IDLE, INITIALIZED, PREPARED, STARTED, PAUSED, STOPED, COMPLTETED, ERROR
	}

	SeekBar progressView;
	SeekBar volumnView;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = PlayerState.PREPARED;
		progressView = (SeekBar) findViewById(R.id.progress);
		volumnView = (SeekBar) findViewById(R.id.volumn);
		list = (ListView) findViewById(R.id.listView1);

		int duration = mPlayer.getDuration();
		progressView.setMax(duration);
		progressView.setProgress(0);

		mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mState = PlayerState.PREPARED;
				mPlayer.start();
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
	}

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
			mPlayer.start();
			mState = PlayerState.STARTED;
		}
	}

	private void pause() {
		if (mState == PlayerState.STARTED) {
			mPlayer.pause();
			mState = PlayerState.PAUSED;
		}
	}

	private void stop() {
		if (mState == PlayerState.STARTED || mState == PlayerState.PAUSED
				|| mState == PlayerState.COMPLTETED
				|| mState == PlayerState.PREPARED) {
			mPlayer.stop();
			mState = PlayerState.STOPED;
		}
	}

	@Override
	protected void onDestroy() {
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
