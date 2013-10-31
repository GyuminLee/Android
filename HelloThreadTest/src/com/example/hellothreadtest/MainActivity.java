package com.example.hellothreadtest;

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
	ProgressBar progress;
	public static final int MESSAGE_UPDATE_PROGRESS = 0;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_UPDATE_PROGRESS:
				int current = msg.arg1;
				messageView.setText("progress : " + current + "/20");
				progress.setProgress(current);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.messageView);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		progress.setMax(20);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i <= 20; i++) {
							try {
								Thread.sleep(1000);
//								mHandler.sendMessage(mHandler.obtainMessage(
//										MESSAGE_UPDATE_PROGRESS, i, 0));
								final int current = i;
								mHandler.post(new Runnable() {
									
									@Override
									public void run() {
										messageView.setText("progress : " + current + "/20");
										progress.setProgress(current);
									}
								});
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

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
