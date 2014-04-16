package com.example.sample3videorecording;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements SurfaceHolder.Callback{

		MediaRecorder mRecorder;
		
		public PlaceholderFragment() {
		}

		int degree;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mRecorder = new MediaRecorder();
			int orientation = getActivity().getResources().getConfiguration().orientation; 
			if ( orientation == Configuration.ORIENTATION_LANDSCAPE) {
				degree = 0;
			} else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
				degree = 90;
				
			}
		}
		
		@Override
		public void onDestroy() {
			mRecorder.release();
			mRecorder = null;
			if (mCamera != null) {
				mCamera.release();
				mCamera = null;
			}
			super.onDestroy();
		}
		
		SurfaceView screen;
		
		String mOutputPath;
		Camera mCamera;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			screen = (SurfaceView)rootView.findViewById(R.id.surfaceView1);
			screen.getHolder().addCallback(this);
			Button btn = (Button)rootView.findViewById(R.id.btnStart);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mRecorder.reset();
					mCamera = Camera.open();
					mCamera.setDisplayOrientation(degree);
					mCamera.unlock();
					mRecorder.setCamera(mCamera);
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File file = new File(dir, "video_" + (System.currentTimeMillis() % 1000000));
					mOutputPath = file.getAbsolutePath();
					mRecorder.setOutputFile(mOutputPath);
					mRecorder.setPreviewDisplay(screen.getHolder().getSurface());
//					mRecorder.setVideoSize(320, 480);
					
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
			
			btn = (Button)rootView.findViewById(R.id.btnStop);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mRecorder.stop();
					mCamera.release();
					mCamera = null;
					ContentValues values = new ContentValues();
					values.put(MediaStore.Video.Media.TITLE, "title");
					values.put(MediaStore.Video.Media.DISPLAY_NAME, "my video");
					values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
					values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis()/ 1000);
					values.put(MediaStore.Video.Media.DATA, mOutputPath);
					
					Uri uri = getActivity().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
					if (uri != null) {
						getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
					}
				}
			});
			return rootView;
		}

		Surface mSurface = null;
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mSurface = holder.getSurface();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			mSurface = holder.getSurface();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			mSurface = null;
		}
	}

}
