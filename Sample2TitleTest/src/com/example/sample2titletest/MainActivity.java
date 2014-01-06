package com.example.sample2titletest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebView;

public class MainActivity extends Activity {

	WebView webView;
	boolean isVisibleProgress = false;
	Handler mHandler = new Handler();
	int mCurrent = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//			requestWindowFeature(Window.FEATURE_NO_TITLE);
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		}
//		requestWindowFeature(Window.FEATURE_PROGRESS);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		setProgressBarIndeterminateVisibility(true);
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				setProgressBarIndeterminateVisibility(false);
			}
		}, 3000);
//		setProgressBarVisibility(true);
//		mHandler.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				mCurrent+=250;
//				if (mCurrent < 10000) {
//					setProgress(mCurrent);
//					mHandler.postDelayed(this, 100);
//				} else {
//					setProgressBarVisibility(false);
//				}
//			}
//		}, 100);
//		webView = (WebView)findViewById(R.id.webView1);
////		webView.getSettings().setJavaScriptEnabled(true);
////		webView.setWebChromeClient(new WebChromeClient() {
////			@Override
////			public void onProgressChanged(WebView view, int newProgress) {
////				if (!isVisibleProgress) {
////					setProgressBarVisibility(true);
////					isVisibleProgress = true;
////				}
////				setProgress(newProgress * 100);
////				if (newProgress >= 99) {
////					setProgressBarVisibility(false);
////					isVisibleProgress = false;
////				}
////			}
////		});
//		webView.loadUrl("http://www.naver.com");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
