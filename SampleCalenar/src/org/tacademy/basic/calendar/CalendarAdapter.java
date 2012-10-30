package org.tacademy.basic.calendar;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CalendarAdapter extends BaseAdapter {

	Context mContext;
	CalendarData mData;
	
	
	public CalendarAdapter(Context context, CalendarData data) {
		mContext = context;
		mData = data;
	}
	
	public void setCalendarData(CalendarData data) {
		mData = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int size = 0;
		if (mData != null) {
			size = mData.days.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.days.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CalendarItemView view = (CalendarItemView)convertView;
		if (view == null) {
			view = new CalendarItemView(mContext);
		}
		view.setData(mData.days.get(position));
		return view;
	}

}
