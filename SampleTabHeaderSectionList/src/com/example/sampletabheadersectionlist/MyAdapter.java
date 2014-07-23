package com.example.sampletabheadersectionlist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TabHost;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	ArrayList<String> items = new ArrayList<String>();
	Context mContext;
	private static final int VIEW_TYPE_COUNT = 3;
	private static final int SUMMARY_VIEW = 0;
	private static final int TAB_WIDGET_VIEW = 1;
	private static final int TEXT_VIEW = 2;

	int currentIndex;
	TabHost.OnTabChangeListener mTabListener;

	public MyAdapter(Context context, int index,
			TabHost.OnTabChangeListener listener) {
		mContext = context;
		currentIndex = index;
		mTabListener = listener;
	}

	public void add(String data) {
		items.add(data);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size() + 2;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		switch (position) {
		case 0:
			return SUMMARY_VIEW;
		case 1:
			return TAB_WIDGET_VIEW;
		default:
			return TEXT_VIEW;
		}
	}

	@Override
	public Object getItem(int position) {
		switch (position) {
		case 0:
		case 1:
			return null;
		default:
			return items.get(position - 2);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (position) {
		case 0:
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.summary_view, null);
			}
			return convertView;
		case 1:
			ItemTabWidget itw = (ItemTabWidget)convertView;
			if (itw == null) {
				itw = new ItemTabWidget(mContext);
				itw.setOnTabChangeListener(mTabListener);
			}
			itw.setCurrentTab(currentIndex);
			return itw;
		default:
			TextView tv = (TextView)convertView;
			if (tv == null) {
				tv = (TextView)LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
			}
			tv.setText(items.get(position - 2));
			return tv;
		}
	}

}
