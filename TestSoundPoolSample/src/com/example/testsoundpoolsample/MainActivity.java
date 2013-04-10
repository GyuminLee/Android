package com.example.testsoundpoolsample;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	SoundPool mSoundPool;
	int mSoundId;
	Button btnStart, btnPause, btnResume, btnStop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		
		mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				// TODO Auto-generated method stub
				btnStart.setEnabled(true);
				btnPause.setEnabled(true);
				btnResume.setEnabled(true);
				btnStop.setEnabled(true);
			}
		});
		
		mSoundId = mSoundPool.load(this, R.raw.test_cbr, 0);
		btnStart = (Button)findViewById(R.id.button1);
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSoundPool.play(mSoundId, 1.0f, 1.0f, 1, 0, 1.0f);
			}
		});
		
		btnPause = (Button)findViewById(R.id.button2);
		btnPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSoundPool.pause(mSoundId);
			}
		});
		
		btnResume = (Button)findViewById(R.id.button3);
		btnResume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSoundPool.resume(mSoundId);
			}
		});
		
		btnStop = (Button)findViewById(R.id.button4);
		btnStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSoundPool.stop(mSoundId);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
