package com.example.sample2videoplaysurface;

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

	SurfaceView surfaceView;
	MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView.getHolder().addCallback(this);
		mPlayer = new MediaPlayer();
		Button btn = (Button) findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mPlayer.start();
			}
		});
		btn = (Button) findViewById(R.id.btnPause);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mPlayer.pause();
			}
		});

		btn = (Button) findViewById(R.id.btnList);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,
						VideoListActivity.class);
				startActivityForResult(i, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			mPlayer.reset();
			try {
				mPlayer.setDataSource(this, uri);
				mPlayer.prepare();
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
	protected void onStop() {
		try {
			mPlayer.pause();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onStop();
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

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mPlayer.setDisplay(holder);
//		mPlayer.setSurface(holder.getSurface());
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mPlayer.setDisplay(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mPlayer.setDisplay(null);
	}

}
