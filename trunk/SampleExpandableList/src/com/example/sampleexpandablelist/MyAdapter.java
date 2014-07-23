package com.example.sampleexpandablelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter {

	Context mContext;
	ArrayList<ItemData> mItems = new ArrayList<ItemData>();
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void put(String key, String data) {
		ItemData item = null;
		for (ItemData id : mItems) {
			if (id.key.equals(key)) {
				item = id;
				break;
			}
		}
		if (item == null) {
			item = new ItemData();
			item.key = key;
			mItems.add(item);
		}
		item.items.add(data);
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
		return mItems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ItemData item = mItems.get(groupPosition);
		return item.items.size();
	}

	@Override
	public String getGroup(int groupPosition) {
		ItemData item = mItems.get(groupPosition);
		return item.key;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ItemData item = mItems.get(groupPosition);
		return item.items.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return (((long)groupPosition) << 32 | ((long)childPosition));
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = (TextView)LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(mItems.get(groupPosition).key);
		
		return tv;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = (TextView)LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(mItems.get(groupPosition).items.get(childPosition));
		return tv;
	}

	
	@Override
	public int getGroupTypeCount() {
		return super.getGroupTypeCount();
	}
	
	@Override
	public int getGroupType(int groupPosition) {
		// TODO Auto-generated method stub
		return super.getGroupType(groupPosition);
	}
	
	@Override
	public int getChildTypeCount() {
		// TODO Auto-generated method stub
		return super.getChildTypeCount();
	}
	
	@Override
	public int getChildType(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return super.getChildType(groupPosition, childPosition);
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
