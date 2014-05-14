package com.example.sampledynamicview;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;

public class MyAdapter extends DataAdapter {

	Context mContext;
	ArrayList<MyData> list = new ArrayList<MyData>();
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(MyData data) {
		list.add(data);
		notifyDataChanged();
	}
	
	public ArrayList<MyData> getList() {
		return list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public View getView(int position) {
		ItemView v = new ItemView(mContext);
		v.setMyData(list.get(position));
		return v;
	}

}
