package org.tacademy.network.rss.yahooplace;

import org.tacademy.network.rss.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapExplainView extends LinearLayout {

	TextView field1;
	TextView field2;
	
	public MapExplainView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.map_explain_layout, this);
		field1 = (TextView)findViewById(R.id.field1);
		field2 = (TextView)findViewById(R.id.field2);
	}
	
	public void setData(String title, String address) {
		field1.setText(title);
		field2.setText(address);
	}

}
