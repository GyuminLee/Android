package com.example.sample4thread;

import android.os.Handler;

public class Downloader implements Runnable {

	public interface OnProgressListener {
		public void onProgress(int progress);
		public void onEnd();
	}
	
	String url;
	OnProgressListener mListener;
	Handler mHandler;
	
	public void start(String url, Handler handler, OnProgressListener listener) {
		this.url = url;
		mListener = listener;
		mHandler = handler;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final int progress = i * 5;
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if (mListener != null) {
						mListener.onProgress(progress);
					}
				}
			});
		}
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				if (mListener != null) {
					mListener.onEnd();
				}
			}
		});
	}
}
