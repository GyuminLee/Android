package com.example.sampleffmpegexecute;

import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new AsyncTask<String, Integer, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				String exec = "/data/data/com.example.sampleffmpegexecute/lib/libffmpeg.so";
				String param = " -ss 00:00:01 -t 00:00:2.00 -i ";
				String input = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1365070268951.mp4";
				String out = Environment.getExternalStorageDirectory().getAbsolutePath() + "/two_%3d.jpg";
//				String input = Environment.getExternalStorageDirectory() + "1365070268951.mp4";
				try {
					String cmd = exec + param + " " + input + " " +out;
					Process p = Runtime.getRuntime().exec(cmd);
					p.waitFor();
					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
			}
		}.execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
