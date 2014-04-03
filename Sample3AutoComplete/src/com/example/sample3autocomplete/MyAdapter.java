package com.example.sample3autocomplete;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements Filterable {

	Context mContext;
	String[] items;
	public MyAdapter(Context context) {
		mContext = context;
	}
	@Override
	public int getCount() {
		if (items == null) return 0;
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = new TextView(mContext);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(items[position]);
		return tv;
	}

	MyFilter myFilter = new MyFilter();
	@Override
	public Filter getFilter() {
		return myFilter;
	}
	
	public class MyFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults result = new FilterResults();
			result.values = new String[] {"abc","def"};
			result.count = 2;
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			items = (String[])results.values;
			notifyDataSetChanged();
			notifyDataSetInvalidated();
		}
	}

}
