package org.tacademy.basic.sectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataItemView extends LinearLayout {
	TextView data;
	public DataItemView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.data_item, this);
		data = (TextView)findViewById(R.id.itemData);
	}
	
	public void setData(String text) {
		data.setText(text);
	}

	public void setSelection(boolean isSelected) {
		// TODO Auto-generated method stub
		// view.setVisibility(...);
	}

}
