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
		public void onResultSuccess(NetworkRequest request);
		public void onResultFail(NetworkRequest request, int errorCode);
	}
	
	public void getNetworkData(NetworkRequest request, OnNetworkResultListener listener) {
		MovieListDownloadTask task = new MovieListDownloadTask();
		task.setOnNetworkResultListener(listener);
		task.execute(request);
	}
	
	public void getNetworkData(NetworkRequest request) {
		getNetworkData(request, new OnNetworkResultListener() {

			@Override
			public void onResultSuccess(NetworkRequest request) {
				request.sendSuccess();
			}

			@Override
			public void onResultFail(NetworkRequest request, int errorCode) {
				request.sendError(errorCode);
			}
			
		});
	}

}
