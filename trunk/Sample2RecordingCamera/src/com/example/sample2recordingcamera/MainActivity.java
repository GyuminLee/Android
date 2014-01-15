package com.example.sample2recordingcamera;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surfaceView;
	MediaRecorder mRecorder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView.getHolder()
				.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().addCallback(this);
		mRecorder = new MediaRecorder();
		Button btn = (Button) findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mRecorder.reset();
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
				mSavedFile = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
						"file_" + System.currentTimeMillis());
				mRecorder.setOutputFile(mSavedFile.getAbsolutePath());
				mRecorder.setPreviewDisplay(mSurface);
				try {
					mRecorder.prepare();
					mRecorder.start();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btn = (Button) findViewById(R.id.btnStop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mRecorder.stop();
				addDB();
//				File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//				MediaScannerConnection.scanFile(MainActivity.this, new String[] {dir.getAbsolutePath()}, null, new OnScanCompletedListener() {
//
//					@Override
//					public void onScanCompleted(String path, Uri uri) {
//						
//						
//					}
//					
//				});
			}
		});
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}
		unregisterReceiver(receiver);
	}
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			
		}
	};

	private void addDB() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Video.Media.TITLE, "my recorder");
		values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
		values.put(MediaStore.Video.Media.DISPLAY_NAME, "my recorder");
		values.put(MediaStore.Video.Media.DATA, mSavedFile.getAbsolutePath());
		values.put(MediaStore.Video.Media.DATE_ADDED,
				System.currentTimeMillis() / 1000);
		Uri uri = getContentResolver().insert(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
		if(uri == null) {
			return;
		}
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
	}

	File mSavedFile;
	Surface mSurface;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mSurface = holder.getSurface();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurface = holder.getSurface();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurface = null;
	}

}
