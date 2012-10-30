package org.tacademy.network.rss.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

public class DataProcessThread extends Thread {
	ArrayList<DataRequest> requests = new ArrayList<DataRequest>();
	
	private static DataProcessThread instance;
	private Handler mHandler;
	private boolean isStarted = false;
	private DatabaseHelper helper;
	
	public static DataProcessThread getInstance() {
		if (instance == null) {
			instance = new DataProcessThread();
		}
		return instance;
	}
	
	private DataProcessThread() {
	}
	
	public synchronized boolean endThread() {
		if (isStarted == false) {
			return false;
		}
		isStarted = false;
		notifyAll();
		return true;
	}
	
	public  synchronized boolean startThread(Context context, Handler handler) {


		if (isStarted) {
			return false;
		}

		helper = new DatabaseHelper(context);
		mHandler = handler;

		isStarted = true;
		this.start();
		return true;
	}
	
	public synchronized boolean enqueue(DataRequest request) {
		synchronized(requests) {
			requests.add(request);
		}
		notifyAll();
		return true;
	}
	
	public synchronized DataRequest dequeue() {
		DataRequest request;
		if (requests.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		synchronized(requests) {
			request = requests.remove(0);
		}
		return request;
	}

	@Override
	public void run() {
		while(isStarted) {
			DataRequest request = dequeue();
			if (request == null) continue;
			SQLiteDatabase db;
			if (request.isWritable()) {
				db = helper.getWritableDatabase();
			} else {
				db = helper.getReadableDatabase();
			}
			request.process(db, mHandler);
			db.close();
		}
	}
	
	
}
