package com.example.samplelisttest2;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	List<MyData> mList;
	Context mContext;
	
	public MyAdapter(Context context, List<MyData> list) {
		mContext = context;
		mList = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyItemView view = new MyItemView(mContext);
		view.setData(mList.get(position));
		return view;
	}

	public void add(MyData myData) {
		// TODO Auto-generated method stub
		mList.add(myData);
		notifyDataSetChanged();
	}

}
