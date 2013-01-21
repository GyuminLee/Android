package com.example.testvideoplaysample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

	VideoView mVideoView;
	MediaController mController;
	static final int REQUEST_CODE_VIDEO = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mVideoView = (VideoView)findViewById(R.id.videoView1);
		mController = (MediaController)findViewById(R.id.mediaController1);
		mController.setMediaPlayer(mVideoView);
		mVideoView.setMediaController(mController);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, VideoListActivity.class);
				startActivityForResult(i, REQUEST_CODE_VIDEO);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE_VIDEO) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				mVideoView.setVideoURI(uri);
				mVideoView.start();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
