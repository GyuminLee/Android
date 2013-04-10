package com.example.samplemediarecordertest;

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

	SurfaceHolder mHolder;
	SurfaceView preview;
	MediaRecorder mRecorder;
	boolean isRecording = false;
	File mRecordingFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		preview = (SurfaceView)findViewById(R.id.surfaceView1);
		preview.getHolder().addCallback(this);
		preview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mRecorder = new MediaRecorder();
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isRecording) {
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					if (mHolder != null && mHolder.getSurface() != null) {
						mRecorder.setPreviewDisplay(mHolder.getSurface());
					}
					
					File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
					mRecordingFile = new File(dir,"test_" + System.currentTimeMillis() + ".mp4");
					mRecorder.setOutputFile(mRecordingFile.getAbsolutePath());
					try {
						mRecorder.prepare();
						mRecorder.start();
						isRecording = true;
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
				if (isRecording) {
					isRecording = false;
					mRecorder.stop();
					addToDB();
				}
			}
		});
	}
	
	public void addToDB() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Video.Media.TITLE, "test recording");
		long time = System.currentTimeMillis() / 1000;
		values.put(MediaStore.Video.Media.DATE_ADDED, time);
		values.put(MediaStore.Video.Media.MIME_TYPE, "video/mpeg");
		values.put(MediaStore.Video.Media.DATA, mRecordingFile.getAbsolutePath());
		Uri addUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);		
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, addUri));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (isRecording) {
			isRecording = false;
			mRecorder.stop();
			addToDB();
		}
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
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
		// TODO Auto-generated method stub
		mHolder = holder;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = holder;
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = null;
	}

}
