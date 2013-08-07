package com.example.samplenavermovies.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;

public class DataDownloader implements Runnable {

	NetworkRequest mRequest;
	Context mContext;
	
	public DataDownloader(Context context, NetworkRequest request) {
		mContext = context;
		mRequest = request;
	}
	
	@Override
	public void run() {
		if (mRequest == null) return;

		URL url = mRequest.getURL();
		int retry = 0;
		for (; retry < 3 && !mRequest.isCancel(); retry++) {
			try {
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				mRequest.setConnectionConfig(conn);
				mRequest.setRequestHeader(conn);
				mRequest.setOutput(conn);
				if (mRequest.isCancel()) {
					NetworkManager.getInstance().removeRequest(mContext, mRequest);
					return;
				}
				int responseCode = conn.getResponseCode();
				if (mRequest.isCancel()) {
					conn.disconnect();
					NetworkManager.getInstance().removeRequest(mContext, mRequest);
					return;
				}
				mRequest.setConnection(conn);
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					mRequest.process(is);
					is.close();
				} else {
					
				}
				conn.disconnect();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (retry == 3) {
			// error ....
			mRequest.sendError(0, "....");
		}
		
		NetworkManager.getInstance().removeRequest(mContext, mRequest);
		
	}

}
