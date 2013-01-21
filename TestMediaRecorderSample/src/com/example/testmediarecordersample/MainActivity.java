package com.example.testmediarecordersample;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	MediaRecorder mRecorder;
	
	String fileName = "test.mpeg";
	File filePath;
	
	SurfaceView surfaceView;
	
	SurfaceHolder mHolder;
	
	boolean isPlaying = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecorder = new MediaRecorder();

		surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
		
		
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isPlaying) {
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					
					if (mHolder != null) {
						mRecorder.setPreviewDisplay(mHolder.getSurface());
					}
					
					File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
					filePath = new File(dir,fileName);
					mRecorder.setOutputFile(filePath.getAbsolutePath());
					
					isPlaying = true;
	
					try {
						mRecorder.prepare();
						mRecorder.start();
						
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isPlaying) {
					mRecorder.stop();
					
					ContentValues values = new ContentValues();
					long time = System.currentTimeMillis();
					values.put(MediaStore.Video.Media.TITLE, "testvideo");
					values.put(MediaStore.Video.Media.DATE_ADDED, time);
					values.put(MediaStore.Video.Media.MIME_TYPE, "video/mpeg");
					values.put(MediaStore.Video.Media.DATA, filePath.getAbsolutePath());
					
					Uri addUri = getContentResolver().insert(
							MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
					
					sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,addUri));
					isPlaying = false;
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		mHolder = holder;
		if (isPlaying) {
			mRecorder.setPreviewDisplay(mHolder.getSurface());
		}
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = holder;
		if (isPlaying) {
			mRecorder.setPreviewDisplay(mHolder.getSurface());
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (mHolder == holder) {
			mHolder = null;
			if (isPlaying) {
				mRecorder.setPreviewDisplay(null);
			}
		}
	}

}
