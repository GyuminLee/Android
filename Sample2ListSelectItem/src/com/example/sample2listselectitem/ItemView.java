package com.example.sample2listselectitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout implements Checkable {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView textView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.iconSelected);
		textView = (TextView)findViewById(R.id.textView);
	}
	
	public void setText(String text) {
		textView.setText(text);
		drawCheck();
	}

	public void setCheckable(boolean checkable) {
		if (checkable) {
			iconView.setVisibility(View.VISIBLE);
		} else {
			iconView.setVisibility(View.GONE);
		}
	}
	
	boolean isChecked = false;
	
	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked)  {
			isChecked = checked;
			drawCheck();
		}
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		drawCheck();
	}
	
	private void drawCheck() {
		if (isChecked) {
			iconView.setImageResource(android.R.drawable.checkbox_on_background);
		} else {
			iconView.setImageResource(android.R.drawable.checkbox_off_background);
		}
	}
}
