package org.tacademy.basic.sectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SectionItemView extends LinearLayout {

	TextView section;
	
	public SectionItemView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.section_item, this);
		section = (TextView)findViewById(R.id.sectionText);
	}
	
	public void setData(String text) {
		section.setText(text);
	}

}
