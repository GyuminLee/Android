package org.tacademy.network.rss.yahooplace;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.R.id;
import org.tacademy.network.rss.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YahooPlaceItemView extends LinearLayout {

	Context mContext;
	TextView name;
	TextView map;
	TextView address;
	YahooPlacesItem mItem;
	
	public interface OnMapClickListener {
		public void onMapClick(YahooPlacesItem item);
	}
	
	OnMapClickListener listener;
	
	public void setOnMapClickListener(OnMapClickListener listener) {
		this.listener = listener;
	}
	
	public YahooPlaceItemView(Context context) {
		super(context);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.yahoo_place_item_view, this);
		name = (TextView)view.findViewById(R.id.name);
		map = (TextView)view.findViewById(R.id.map);
		address = (TextView)view.findViewById(R.id.address);
		
		map.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (listener != null) {
					listener.onMapClick(mItem);
				}
			}
		});
	}

	public void setData(YahooPlacesItem item) {
		mItem = item;
		name.setText(item.name);
		address.setText(item.state + " " + item.county + " " + item.city + " " + item.street);
	}
}
