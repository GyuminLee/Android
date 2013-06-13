package com.example.testvideoplaysample2;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class SurfacePlayActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surface;
	MediaPlayer mPlayer;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.video_play_surface);
	    surface = (SurfaceView)findViewById(R.id.surfaceView1);
	    surface.getHolder().addCallback(this);
	    surface.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    
	    mPlayer = new MediaPlayer();
	    mPlayer.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mPlayer.start();
			}
		});
	    
	    Button btn = (Button)findViewById(R.id.list);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SurfacePlayActivity.this, VideoListActivity.class);
				startActivityForResult(i, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Uri contentUri = data.getData();
				try {
					mPlayer.setDataSource(this, contentUri);
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
	protected void onDestroy() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		
		super.onDestroy();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
		if (mPlayer != null) {
			mPlayer.setDisplay(holder);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (mPlayer != null) {
			mPlayer.setDisplay(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mPlayer != null) {
			mPlayer.setDisplay(null);
		}
	}

}
