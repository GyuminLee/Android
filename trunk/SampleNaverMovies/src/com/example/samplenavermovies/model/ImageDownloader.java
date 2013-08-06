package com.example.samplenavermovies.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader implements Runnable {


	ImageRequestManager requestManager;
	
	public ImageDownloader(ImageRequestManager manager) {
		requestManager = manager;
	}
	
	@Override
	public void run() {
		while(true) {
			ImageRequest request = requestManager.dequeue();
			if (request == null) continue;
			URL url = request.getURL();
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
