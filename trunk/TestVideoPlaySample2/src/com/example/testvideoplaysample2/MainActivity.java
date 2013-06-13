package com.example.testvideoplaysample2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends Activity {

	TextView titleView;
	VideoView mVideoPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		titleView = (TextView)findViewById(R.id.title);
		mVideoPlayer = (VideoView)findViewById(R.id.videoView1);
		MediaController mController = new MediaController(this);
		mVideoPlayer.setMediaController(mController);
		mController.setMediaPlayer(mVideoPlayer);
		mVideoPlayer.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoPlayer.start();
			}
		});
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, VideoListActivity.class);
				startActivityForResult(i, 0);
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				String title = data.getStringExtra("title");
				titleView.setText(title);
				mVideoPlayer.setVideoURI(uri);
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

}
