package com.example.hellonaveropenapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;

public class NetworkModel {
	private static NetworkModel instance;
	HashMap<Context, ArrayList<NetworkRequest>> mRequestMap = new HashMap<Context, ArrayList<NetworkRequest>>();

	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}

	private NetworkModel() {

	}

	public interface OnResultListener {
		public void onSuccess(Object result);

		public void onError(int errorCode);
	}

	public void getNetworkData(Context context, NetworkRequest request) {
		request.context = context;
		ArrayList<NetworkRequest>  list = mRequestMap.get(context);
		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);
		new MyDownloadRequest().execute(request);
	}

	public void cleanUpRequest(Context context) {
		ArrayList<NetworkRequest> list = mRequestMap.get(context);
		if (list != null) {
			for (NetworkRequest request : list) {
				request.setCancel();
			}
		}
	}
	
	class MyDownloadRequest extends
			AsyncTask<NetworkRequest, Integer, Boolean> {
		NetworkRequest mRequest;

		@Override
		protected Boolean doInBackground(NetworkRequest... params) {
			NetworkRequest request = params[0];
			mRequest = request;
			URL url = request.getURL();
			int retry = 3;
			while (retry > 0) {
				try {
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod(request.getRequestMethod());
					conn.setConnectTimeout(request.getConnectionTimeout());
					conn.setReadTimeout(request.getReadTimeout());
					request.setRequestHeader(conn);
					if (request.isCancel()) {
						return false;
					}
					request.setOutput(conn);
					if (request.isCancel()) {
						return false;
					}
					int resCode = conn.getResponseCode();
					if (request.isCancel()) {
						return false;
					}
					if (resCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						request.process(is);
						return true;
					} else {
						return false;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				retry--;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				mRequest.sendResult();
			} else {
				mRequest.sendError(0);
			}
			ArrayList<NetworkRequest> list = mRequestMap.get(mRequest.context);
			list.remove(mRequest);
			super.onPostExecute(result);
		}
	}

}
