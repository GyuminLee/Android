package com.example.testnetworksample2;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;

public abstract class NetworkRequest {

	public final static int CONNECTION_TIME_OUT = 30000;
	public final static int READ_TIME_OUT = 30000;
	
	public interface OnProcessCompleteListener {
		public void onCompleted(NetworkRequest request);
		public void onError(NetworkRequest request, String errorMessage);
	}
	
	OnProcessCompleteListener mListener;
	Handler mHandler;
	
	public void setOnProcessCompleteListener(OnProcessCompleteListener listener) {
		mListener = listener;
	}
	
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	public abstract URL getRequestURL();
	
	public void setRequestMethod(HttpURLConnection conn) {
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRequestProperty(HttpURLConnection conn) {
		
	}
	
	public void setOutput(HttpURLConnection conn) {
		
	}
	
	public int getConnectionTimeout() {
		return CONNECTION_TIME_OUT;
	}
	
	public int getReadTimeout() {
		return READ_TIME_OUT;
	}
	
	public void process(InputStream is) {
		try {
			parsing(is);
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (mListener != null) {
							mListener.onCompleted(NetworkRequest.this);
						}
					}
				});
			}
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			notifyError("parsing error");
		}
	}
	
	public void notifyError(final String errorMessage) {
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mListener != null) {
						mListener.onError(NetworkRequest.this,errorMessage);
					}
				}
			});
		}
	}
	
	public abstract void parsing(InputStream is) throws ParsingException;
	
	public abstract Object getResult();
}
