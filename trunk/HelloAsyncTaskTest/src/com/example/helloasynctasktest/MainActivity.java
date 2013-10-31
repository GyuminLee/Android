package com.example.helloasynctasktest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloasynctasktest.MyDownloadTask.OnFileDownloadListener;

public class MainActivity extends Activity {

	TextView messageView;
	ProgressBar progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.messageView);
		progress = (ProgressBar)findViewById(R.id.progressBar1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				new MyProgressUpdateTask().execute("");
				MyDownloadTask task = new MyDownloadTask();
				task.setOnFileDownloadListener(new OnFileDownloadListener() {
					
					@Override
					public void onProgress(int prog) {
						messageView.setText("progress : " + prog + "/100");
						progress.setProgress(prog);
					}
					
					@Override
					public void onError() {
						
						
					}
					
					@Override
					public void onCompleted() {
						Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
						
					}
				});
				task.execute("");
			}
		});
	}
	
	class MyProgressUpdateTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			for (int i = 0; i <= 20; i++) {
				try {
					Thread.sleep(1000);
					publishProgress((Integer)i*5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			int current = values[0];
			messageView.setText("current : " + current + "/100");
			progress.setProgress(current);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if (result != null && result == true) {
				Toast.makeText(MainActivity.this, "progress end...", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
