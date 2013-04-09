package com.example.sampledatabasetest.manager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

public abstract class DBRequest {

	Handler mHandler;
	
	public interface OnQueryCompleteListener {
		public void onQueryCompleted(DBRequest request);
	}
	
	OnQueryCompleteListener mListener;
	
	public void setOnQueryCompleteListener(OnQueryCompleteListener listener) {
		mListener = listener;
	}
	
	protected abstract void processQuery(SQLiteDatabase db);
	
	public abstract Object getResult();
	
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
	
	public void process(SQLiteDatabase db) {
		processQuery(db);
		if (mHandler != null) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mListener != null) {
						mListener.onQueryCompleted(DBRequest.this);
					}
				}
			});
		} else {
			if (mListener != null) {
				mListener.onQueryCompleted(this);
			}
		}
	}
}
