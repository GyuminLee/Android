package com.example.sample2threadtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView messageView;
	ProgressBar progressBar;

	public final static int MESSAGE_PROGRESS = 1;
	public final static int MESSAGE_PROGRESS_DONE = 2;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_PROGRESS:
				int progress = msg.arg1;
				String text = (String) msg.obj;
				messageView.setText(text);
				progressBar.setProgress(progress);
				break;
			case MESSAGE_PROGRESS_DONE:
				messageView.setText("done");
				progressBar.setProgress(100);
				break;
			}
		}
	};
	
	class MyRunnable implements Runnable {
		int progress;
		String text;
		public MyRunnable(int progress,String text) {
			this.progress = progress;
			this.text = text;
		}
		
		@Override
		public void run() {
			messageView.setText(text);
			progressBar.setProgress(progress);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.messageView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setMax(100);
		progressBar.setProgress(0);
		messageView.setText("progress : 0");
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						int count = 0;
						while (count < 100) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							count += 5;
							MyRunnable r = new MyRunnable(count, "progress : " + count);
							mHandler.post(r);
//							mHandler.sendMessage(mHandler.obtainMessage(
//									MESSAGE_PROGRESS, count, 0, "proress : "
//											+ count));
						}
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								messageView.setText("done");
								progressBar.setProgress(100);
							}
						});
//						mHandler.sendMessage(mHandler
//								.obtainMessage(MESSAGE_PROGRESS_DONE));
					}
				}).start();
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
