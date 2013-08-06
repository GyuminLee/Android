package com.example.samplenavermovies.model;

import java.util.ArrayList;

public class ImageRequestManager {

	ArrayList<ImageRequest> mQueue = new ArrayList<ImageRequest>();
	ArrayList<ImageRequest> mRunningQueue = new ArrayList<ImageRequest>();
	
	public synchronized void enqueue(ImageRequest request) {
		
		for (int i = 0; i < mQueue.size(); i++) {
			ImageRequest r = mQueue.get(i);
			if (r.isPendingRequest(request)) {
				return;
			}
		}
		
		for (int i = 0; i < mRunningQueue.size(); i++) {
			ImageRequest r = mRunningQueue.get(i);
			if (r.isPendingRequest(request)) {
				return ;
			}
		}
		
		mQueue.add(request);
		notify();
	}
	
	public synchronized ImageRequest dequeue() {
		ImageRequest r = null;
		while(mQueue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		r = mQueue.remove(0);
		mRunningQueue.add(r);
		return r;
	}
	
	public void removeRunnginQueue(ImageRequest r) {
		mRunningQueue.remove(r);
	}
}
