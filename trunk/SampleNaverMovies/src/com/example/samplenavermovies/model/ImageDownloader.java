package com.example.samplenavermovies.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader implements Runnable {


	boolean isRunning = true;
	ImageRequestManager requestManager;
	
	public ImageDownloader(ImageRequestManager manager) {
		requestManager = manager;
	}
	
	
	public void stopThread() {
		isRunning = false;
		ImageRequest dummy = new ImageRequest("");
		requestManager.enqueue(dummy);
	}
	
	@Override
	public void run() {
		while(isRunning) {
			ImageRequest request = requestManager.dequeue();
			if (request == null) continue;
			URL url = request.getURL();
			if (url == null) continue;
			int retry = 0;
			for (; retry < 3 && !request.isCancel(); retry++) {
				try {
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					request.setConnectionConfig(conn);
					request.setRequestHeader(conn);
					request.setOutput(conn);
					if (request.isCancel()) break;;
					int responseCode = conn.getResponseCode();
					if (request.isCancel()) {
						conn.disconnect();
						break;
					}
					request.setConnection(conn);
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						request.process(is);
						is.close();
					} else {
						
					}
					conn.disconnect();
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (retry == 3) {
				// error ....
				request.sendError(0, "....");
			}
			requestManager.removeRunnginQueue(request);
		}

	}

}
