package com.example.samplenavermovies.model;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
	
	public void remove(ImageRequest ir) {
		mQueue.remove(ir);
	}

	AtomicInteger mInt = new AtomicInteger();
	
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
		if (mQueue.size() > 0) {
			r = mQueue.remove(0);
			mRunningQueue.add(r);
		} else {
			r = null;
		}
		return r;
	}
	
	public void removeRunnginQueue(ImageRequest r) {
		mRunningQueue.remove(r);
	}
}
