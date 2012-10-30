package org.tacademy.basic.googleplaces.placelist;

import org.tacademy.basic.googleplaces.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GooglePlaceItemView extends LinearLayout {

	TextView name;
	TextView address;
	GooglePlaceItem item;
	
	public GooglePlaceItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.place_item, this);
		name = (TextView)findViewById(R.id.name);
		address = (TextView)findViewById(R.id.address);
	}
	
	public void setData(GooglePlaceItem data) {
		item = data;
		name.setText(data.name);
		address.setText(data.vicinity);
	}
}
