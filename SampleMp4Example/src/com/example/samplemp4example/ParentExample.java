package com.example.samplemp4example;

import java.io.IOException;

import android.os.Environment;
import android.os.Handler;

public abstract class ParentExample implements Runnable {
	Handler mHandler;
	public static final String EXAMPLE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mp4parser";
	public interface OnCompleteListener {
		public void onSuccess();
		public void onError();
	}
	OnCompleteListener mListener;
	String[] args;
	public ParentExample(Handler handler, OnCompleteListener listener,String... args) {
		mHandler = handler;
		mListener = listener;
		this.args = args;
	}
	
	protected void sendSuccess() {
		if (mHandler != null && mListener != null) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					mListener.onSuccess();
				}
			});
		}
	}
	
	protected void sendError() {
		if (mHandler != null && mListener != null) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					mListener.onError();
				}
			});
		}
	}
	
	@Override
	public void run() {
		try {
			executeExample();
			sendSuccess();
		} catch(IOException e) {
			e.printStackTrace();
			sendError();
		}
	}
	
	abstract void executeExample() throws IOException;

}
