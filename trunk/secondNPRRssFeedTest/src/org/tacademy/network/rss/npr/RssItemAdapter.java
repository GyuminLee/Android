package org.tacademy.network.rss.npr;

import java.util.ArrayList;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RssItemAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<SingleNewsItem> items;
	
	public RssItemAdapter(Context context, ArrayList<SingleNewsItem> data) {
		mContext = context;
		items = data;
	}
	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		RssItemView itemView = (RssItemView)convertView;
		
		if (itemView == null) {
			itemView = new RssItemView(mContext);
		}
		itemView.setData(items.get(position));
		return itemView;
	}
	
	public SingleNewsItem getData(int position) {
		return items.get(position);
	}

}
