package com.example.sampledynamicview;

import android.view.View;

public abstract class DataAdapter {
	
	public interface DataChangeObserver {
		public void onDataChanged();
	}
	
	DataChangeObserver mObserver;
	public void setDataChangeObserver(DataChangeObserver observer) {
		mObserver = observer;
	}
	abstract public int getCount();
	abstract public View getView(int position);
	
	public void notifyDataChanged() {
		if (mObserver != null) {
			mObserver.onDataChanged();
		}
	}
}
