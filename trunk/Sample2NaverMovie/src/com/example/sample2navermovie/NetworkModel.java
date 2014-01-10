package com.example.sample2navermovie;

public class NetworkModel {
	
	private static NetworkModel instance;
	
	public static NetworkModel getInstance() {
		if (instance == null) {
			instance = new NetworkModel();
		}
		return instance;
	}
	
	private void NetworkModel() {
		
	}
	
	public interface OnNetworkResultListener {
		public void onResultSuccess(NetworkRequest movies);
		public void onResultFail(int errorCode);
	}
	
	public void getNetworkData(NetworkRequest request, OnNetworkResultListener listener) {
		MovieListDownloadTask task = new MovieListDownloadTask();
		task.setOnNetworkResultListener(listener);
		task.execute(request);
	}
	
	public void getNetworkData(NetworkRequest request) {
		final NetworkRequest r = request;
		getNetworkData(request, new OnNetworkResultListener() {

			@Override
			public void onResultSuccess(NetworkRequest request) {
				r.sendSuccess();
			}

			@Override
			public void onResultFail(int errorCode) {
				r.sendError();
			}
			
		});
	}

}
