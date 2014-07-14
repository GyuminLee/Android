package com.example.sample4mediarecording;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView surfaceView;
	MediaRecorder mRecorder;
	File mSavedFile;
	boolean isRecording = false;
	Camera mCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		mRecorder = new MediaRecorder();
		mCamera = Camera.open();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			mCamera.setDisplayOrientation(90);
		}
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mRecorder.reset();
				mCamera.unlock();
				mRecorder.setCamera(mCamera);
				mRecorder.setAudioSource(AudioSource.MIC);
				mRecorder.setVideoSource(VideoSource.CAMERA);
				mRecorder.setOutputFormat(OutputFormat.MPEG_4);
				mRecorder.setAudioEncoder(AudioEncoder.AMR_NB);
				mRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);
				long time = System.currentTimeMillis() % 1000;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmm");
				String filename = "myvideo_"
						+ sdf.format(new Date(System.currentTimeMillis()))
						+ ".mp4";
				File dir = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				mSavedFile = new File(dir, filename);
				mRecorder.setOutputFile(mSavedFile.getAbsolutePath());
				mRecorder.setPreviewDisplay(surfaceView.getHolder()
						.getSurface());
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
		});

		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mRecorder.stop();
				addToVideo();
				isRecording = false;
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isRecording) {
			mRecorder.stop();
			addToVideo();
			isRecording = false;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (mRecorder != null) {
				mRecorder.release();
			}
		} catch (Exception e) {
		}

		try {
			if (mCamera != null) {
				mCamera.release();
			}
		} catch (Exception e) {
		}
	}

	private void addToVideo() {
		Bitmap bm = ThumbnailUtils.createVideoThumbnail(
				mSavedFile.getAbsolutePath(), Video.Thumbnails.MINI_KIND);
		ContentValues values = new ContentValues();
		values.put(Video.Media.DISPLAY_NAME, "my video");
		values.put(Video.Media.TITLE, "my video");
		values.put(Video.Media.DATA, mSavedFile.getAbsolutePath());
		values.put(Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
		values.put(Video.Media.MIME_TYPE, "video/mpeg");
		Uri uri = getContentResolver().insert(Video.Media.EXTERNAL_CONTENT_URI,
				values);
		if (uri != null) {
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
					uri);
			sendBroadcast(intent);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
}
