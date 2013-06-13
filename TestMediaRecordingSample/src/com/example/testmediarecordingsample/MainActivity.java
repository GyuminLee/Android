package com.example.testmediarecordingsample;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	SurfaceView surfaceView;
	MediaRecorder mRecorder;
	File mOutputDirectory;
	File mOutputFile;
	Camera mCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mRecorder = new MediaRecorder();
		mRecorder.setOnErrorListener(new OnErrorListener() {
			
			@Override
			public void onError(MediaRecorder mr, int what, int extra) {
				
			}
		});
		mOutputDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
		
		Button btn = (Button)findViewById(R.id.recoding);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mOutputFile = new File(mOutputDirectory, "myFile" + System.currentTimeMillis());

				mCamera.unlock();
				mRecorder.setCamera(mCamera);
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
				
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
				
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
				mRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
				mRecorder.setOutputFile(mOutputFile.getAbsolutePath());
				
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
		});
		
		btn = (Button)findViewById(R.id.stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRecorder.stop();
				mRecorder.reset();
				
				ContentValues values = new ContentValues();
				values.put(MediaStore.Video.Media.TITLE, "MyRecording");
				values.put(MediaStore.Video.Media.DISPLAY_NAME, "My Recording");
				values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
				values.put(MediaStore.Video.Media.MIME_TYPE, "video/mpeg");
				values.put(MediaStore.Video.Media.DATA, mOutputFile.getAbsolutePath());
				
				Uri addUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,addUri));
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mRecorder.release();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
