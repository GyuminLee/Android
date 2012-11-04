package com.example.samplearcamera.library;

import java.util.ArrayList;

import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.example.samplearcamera.googleplaces.GooglePlaceItem;
import com.example.samplearcamera.googleplaces.GooglePlaces;
import com.example.samplearcamera.googleplaces.RequestPlaceList;
import com.example.samplearcamera.network.DownloadThread;
import com.example.samplearcamera.network.NetworkRequest;

public class POIManager {
	
	private ArrayList<GooglePlaceItem> items = new ArrayList<GooglePlaceItem>();
	
	private static POIManager instance;
	
	public interface OnUpdatedListener {
		public void onUpdated(ArrayList<GooglePlaceItem> items);
	}
	
	
	public static POIManager getInstance() {
		if (instance == null) {
			instance = new POIManager();
		}
		return instance;
	}
	
	private POIManager() {
		
	}
	
	public void getNewGooglePlaceItems(Location location,final OnUpdatedListener listener,Handler handler) {
		RequestPlaceList request = new RequestPlaceList(location.getLatitude(), location.getLongitude());
		request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			@Override
			public void onDownloadCompleted(int result, NetworkRequest request) {
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					GooglePlaces places = (GooglePlaces)request.getResult();
					items.clear();
					items.addAll(places.items);
					listener.onUpdated(items);
				} else {
					// download fail...
					Log.i("POIManager","fail get googleplace");
				}
			}
		});
		DownloadThread th = new DownloadThread(handler,request);
		th.start();	
	}
	
	public ArrayList<GooglePlaceItem> getCurrentPOI() {
		return items;
	}

}
