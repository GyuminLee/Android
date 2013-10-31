package com.example.helloasynctasktest;

import android.os.AsyncTask;

public class MyDownloadTask extends AsyncTask<String, Integer, Boolean> {

	public interface OnFileDownloadListener {
		public void onProgress(int progress);
		public void onCompleted();
		public void onError();
	}
	
	OnFileDownloadListener mListener;
	public void setOnFileDownloadListener(OnFileDownloadListener listener) {
		mListener = listener;
	}
	
	public MyDownloadTask() {
		super();
	}
	
	public MyDownloadTask(OnFileDownloadListener listener) {
		super();
		mListener = listener;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(200);
				publishProgress((Integer)i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		if (mListener != null) {
			mListener.onProgress(values[0]);
		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if (mListener != null) {
			if (result != null && result == true) {
				mListener.onCompleted();
			} else {
				mListener.onError();
			}
		}
	}

}
