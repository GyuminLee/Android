package com.example.sampleaubiolibav;

import com.example.sampleaubio.TempoDetector;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AsyncTask<String, Integer, Integer>() {
					@Override
					protected Integer doInBackground(String... params) {
						String mp3file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/winter_blues.mp3";
						int ret = TempoDetector.detectTempo(mp3file);
						return ret;
					}
					
					@Override
					protected void onPostExecute(Integer result) {
						Toast.makeText(MainActivity.this, "result : " + result, Toast.LENGTH_SHORT).show();
					}
				}.execute("");
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
