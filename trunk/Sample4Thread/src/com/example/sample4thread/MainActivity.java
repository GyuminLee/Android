package com.example.sample4thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView messageView;
	ProgressBar progressView;
	
	public static final int MESSAGE_PROGRESS = 0;
	public static final int MESSAGE_DONE = 1;
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_PROGRESS :
				int progress = msg.arg1;
				messageView.setText("progress : " + progress);
				progressView.setProgress(progress);
				break;
			case MESSAGE_DONE :
				messageView.setText("progress done");
				progressView.setProgress(100);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.message_view);
		progressView = (ProgressBar)findViewById(R.id.progressBar1);
		Button btn = (Button)findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				progressView.setMax(100);
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						for (int i = 0; i < 20; i++) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							int progress = i * 5;
							Message msg = mHandler.obtainMessage(MESSAGE_PROGRESS, progress, 0);
							mHandler.sendMessage(msg);
						}
						
						mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_DONE));
					}
				}).start();
			}
		});
	}
}
