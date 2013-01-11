package com.example.testnetworksample;

import java.io.InputStream;
import java.net.URL;

import android.os.Handler;

abstract public class NetworkRequest {

	public interface OnProcessedListener {
		public void onProcessed(NetworkRequest requst);
	}
	OnProcessedListener mListener;
	Handler mHandler;
	
	abstract public URL getRequestURL();
	abstract public Object getResult();
	
	public boolean process(InputStream is) {
		dataProcessing(is);
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onProcessed(NetworkRequest.this);
				}
			}
			
		});
		return true;
	}
	
	public void setOnProcessedListener(OnProcessedListener listener) {
		mListener = listener;
	}
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	
	abstract public void dataProcessing(InputStream is);
	
}
