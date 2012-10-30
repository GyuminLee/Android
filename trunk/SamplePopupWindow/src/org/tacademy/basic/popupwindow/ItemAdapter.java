package org.tacademy.basic.popupwindow;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemAdapter extends BaseAdapter implements ItemView.OnMyItemMenuClickListener {

	Context mContext;
	ArrayList<String> items;
	MyPopupWindow myPopup;
	
	
	public ItemAdapter(Context context,ArrayList<String> items) {
		this.mContext = context;
		this.items = items;
		myPopup = new MyPopupWindow(context);
	}
	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView v = (ItemView)convertView;
		if (v == null) {
			v = new ItemView(mContext);
			v.setOnMyItemMenuClickListener(this);
		}
		
		v.setData(items.get(position),myPopup);
		return v;
	}

	public void onItemMenuUpdateClick(ItemView item) {
		//...
	}

	public void onItemMenuDeleteClick(ItemView item) {
		// ..
	}

}
