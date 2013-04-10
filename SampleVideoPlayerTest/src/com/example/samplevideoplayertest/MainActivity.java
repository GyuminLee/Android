package com.example.samplevideoplayertest;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	MediaPlayer mPlayer;
	enum PlayerState {
		IDLE,
		INITIALIZED,
		PREPARED,
		STARTED,
		PAUSED,
		STOPPED
	}
	
	PlayerState mState;
	SurfaceView playView;
	Button playButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = new MediaPlayer();
		mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mState = PlayerState.PREPARED;
				playButton.setEnabled(true);
			}
		});
		
		mState = PlayerState.IDLE;
		playView = (SurfaceView)findViewById(R.id.playView);
		playView.getHolder().addCallback(this);
		playView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		playButton = (Button)findViewById(R.id.play);
		playButton.setEnabled(false);
		playButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mState == PlayerState.INITIALIZED) {
					return;
				}
				if (mState == PlayerState.PREPARED) {
					mPlayer.start();
				}
			}
		});
		
		Button btn = (Button)findViewById(R.id.search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				mPlayer.reset();
				playButton.setEnabled(false);
				mState = PlayerState.IDLE;
				try {
					mPlayer.setDataSource(this, uri);
					mState = PlayerState.INITIALIZED;
					mPlayer.prepareAsync();
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
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		mPlayer.setDisplay(holder);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mPlayer.setDisplay(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mPlayer.setDisplay(null);
	}

}
