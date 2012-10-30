package org.tacademy.network.rss.c2dm;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class UserListAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<User> items;
	
	public UserListAdapter(Context context,
			ArrayList<User> items) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		UserItemView view = (UserItemView)convertView;
		if (view == null) {
			view = new UserItemView(mContext);
		}
		view.setData((User)getItem(position));
		return view;
	}

}
