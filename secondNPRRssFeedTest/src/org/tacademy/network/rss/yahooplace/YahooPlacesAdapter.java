package org.tacademy.network.rss.yahooplace;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class YahooPlacesAdapter extends BaseAdapter implements YahooPlaceItemView.OnMapClickListener{

	Context mContext;
	YahooPlaces places;
	
	public interface OnItemMapClickListener {
		public void onItemMapClick(YahooPlacesItem item);
	}
	
	OnItemMapClickListener listener;
	
	public void setOnItemMapClickListener(OnItemMapClickListener listener) {
		this.listener = listener;
	}
	
	public YahooPlacesAdapter(Context context,YahooPlaces places) {
		this.mContext = context;
		this.places = places;
	}
	
	public int getCount() {
		return places.items.size();
	}

	public Object getItem(int position) {
		return places.items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {
		YahooPlaceItemView view = (YahooPlaceItemView)convertView;
		if (view == null) {
			view = new YahooPlaceItemView(mContext);
			view.setOnMapClickListener(this);
		}
		view.setData((YahooPlacesItem)getItem(position));
		return view;
	}

	public void onMapClick(YahooPlacesItem item) {
		if (listener != null) {
			listener.onItemMapClick(item);
		}
	}

}
