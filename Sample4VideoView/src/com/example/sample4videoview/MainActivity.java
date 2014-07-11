package com.example.sample4videoview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

	VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		videoView = (VideoView)findViewById(R.id.videoView1);
		MediaController controller = new MediaController(this);
		videoView.setMediaController(controller);
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
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode ==0 && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			videoView.setVideoURI(uri);
		}
	}
}
