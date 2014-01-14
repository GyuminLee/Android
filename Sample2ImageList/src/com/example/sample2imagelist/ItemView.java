package com.example.sample2imagelist;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class ItemView extends LinearLayout implements Checkable {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}



	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		drawBackgroundColor();
	}


	boolean isChecked = false;
	
	@Override
	public boolean isChecked() {
		return isChecked;
	}



	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			drawBackgroundColor();
		}
	}



	@Override
	public void toggle() {
		isChecked = !isChecked;
		drawBackgroundColor();
	}
	
	private final static int NOT_SELECT_COLOR = Color.WHITE;
	private final static int SELECT_COLOR = Color.DKGRAY;
	
	private void drawBackgroundColor() {
		if (isChecked) {
			setBackgroundColor(SELECT_COLOR);
		} else {
			setBackgroundColor(NOT_SELECT_COLOR);
		}
	}
	
}
