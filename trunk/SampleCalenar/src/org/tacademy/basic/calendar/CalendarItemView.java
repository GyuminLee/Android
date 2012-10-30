package org.tacademy.basic.calendar;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarItemView extends LinearLayout {

	TextView numberView;
	TextView contentView;
	CalendarItem mItem;
	public final static int NUMBER_COLOR = Color.WHITE;
	public final static int SAT_COLOR = Color.RED;
	public final static int SUN_COLOR = Color.BLUE;
	public final static float IN_MONTH_TEXT_SIZE_SP = 20.0f;

	public final static int OUT_MONTH_TEXT_COLOR = Color.GRAY;
	public final static float OUT_MONTH_TEXT_SIZE_SP = 15.0f;
	
	public CalendarItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.calendar_item, this);
		numberView = (TextView)findViewById(R.id.number);
		contentView = (TextView)findViewById(R.id.content);
	}
	
	public void setData(CalendarItem item) {
		mItem = item;
		float textsize = IN_MONTH_TEXT_SIZE_SP;
		int textColor = NUMBER_COLOR;
		if (!item.inMonth) {
			textsize = OUT_MONTH_TEXT_SIZE_SP;
			textColor = OUT_MONTH_TEXT_COLOR;
		} else {
			textsize = IN_MONTH_TEXT_SIZE_SP;
			switch (item.dayOfWeek) {
				case Calendar.SUNDAY :
					textColor = SUN_COLOR;
					break;
				case Calendar.SATURDAY :
					textColor = SAT_COLOR;
					break;
				default :
					textColor = NUMBER_COLOR;
					break;
			}
		}
		numberView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textsize);
		numberView.setTextColor(textColor);
		numberView.setText("" + item.dayOfMonth);
		// contentView setting
		
		ArrayList items = item.items;
		int size = items.size();
		StringBuilder sb = new StringBuilder();
		sb.append(size + "°³");
		contentView.setText(sb.toString());
		
	}

}
