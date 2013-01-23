package com.example.testappwidgetsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RemoteViews.RemoteView;

@RemoteView
public class MyCompoundWidget extends FrameLayout {

	public MyCompoundWidget(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MyCompoundWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MyCompoundWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.my_compound_view_layout, this);
		
	}
	
	public void setData(String title) {
		
	}
}
