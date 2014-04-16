package com.example.sample3audiorecording;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
	public static class PlaceholderFragment extends Fragment {

		MediaRecorder mRecoder;

		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mRecoder = new MediaRecorder();
		}

		@Override
		public void onDestroy() {
			mRecoder.release();
			super.onDestroy();
		}

		String mOutputPath;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			Button btn = (Button) rootView.findViewById(R.id.btnStart);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mRecoder.reset();
					mRecoder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecoder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					mRecoder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					File dir = Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
					File file = new File(dir, "audio_"
							+ (System.currentTimeMillis() % 1000000));
					mOutputPath = file.getAbsolutePath();
					mRecoder.setOutputFile(mOutputPath);

					try {
						mRecoder.prepare();
						mRecoder.start();
						mHandler.post(logRunnable);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

			btn = (Button) rootView.findViewById(R.id.btnStop);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mHandler.removeCallbacks(logRunnable);
					mRecoder.stop();

					ContentValues values = new ContentValues();
					values.put(MediaStore.Audio.Media.TITLE, "title");
					values.put(MediaStore.Audio.Media.DISPLAY_NAME, "my audio");
					values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp4");
					values.put(MediaStore.Audio.Media.DATE_ADDED,
							System.currentTimeMillis() / 1000);
					values.put(MediaStore.Audio.Media.DATA, mOutputPath);

					Uri uri = getActivity()
							.getContentResolver()
							.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
									values);

					if (uri != null) {
						getActivity().sendBroadcast(
								new Intent(
										Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
										uri));
					}
				}
			});
			return rootView;
		}
		
		Handler mHandler = new Handler();
		public static int TIME_INTERVAL = 100;
		long startTime = -1;
		
		Runnable logRunnable = new Runnable() {
			
			@Override
			public void run() {
				if (mRecoder != null) {
					int amp = mRecoder.getMaxAmplitude();
					Log.i("MediaRecoder" , "amp : " + amp);
					if (amp < 10000 && startTime == -1) {
						startTime = System.currentTimeMillis();
					}
					if (amp > 10000) {
						startTime = -1;
					}
					
					if (startTime != -1) {
						if (System.currentTimeMillis() - startTime > 60000) {
							// ...
						}
					}
					mHandler.postDelayed(this, TIME_INTERVAL);
				}
			}
		};
	}

}
