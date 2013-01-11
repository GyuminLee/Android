package com.example.testnetworksample;

import java.io.InputStream;
import java.net.URL;

import android.os.Handler;

public class NetworkModel {

	private static NetworkModel instance;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private NetworkModel() {
		
	}
	
	
	public interface OnNetworkDownloadListener {
		public void onDownloaded();
	}
	
	public void getNetworkData(final String url, 
			final OnNetworkDownloadListener listener,
			final Handler handler) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// ...
				// ...
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onDownloaded();
					}
				});
			}
			
		}).start();
		
	}
	
	public void getNetworkData(NetworkRequest request, 
			NetworkRequest.OnProcessedListener listener,
			Handler handler) {
		request.setOnProcessedListener(listener);
		request.setHandler(handler);
		new DownloadThread(request).start();
	}
	
	class DownloadThread extends Thread {
		NetworkRequest mRequest;
		
		public DownloadThread(NetworkRequest request) {
			mRequest = request;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			URL url = mRequest.getRequestURL();
			// url download
			InputStream is = null;
			// is url È¹µæ
			
			mRequest.process(is);
		}
	}
	
	
	
	
	
	
	
	
}
