package com.example.sample4imagelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ItemView extends FrameLayout implements Checkable {

	public ItemView(Context context) {
		super(context);
		init();
	}

	public ItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	ImageView imageCheck;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_view, this);
		imageCheck = (ImageView)findViewById(R.id.image_checked);
	}

	boolean isChecked = false;
	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			drawCheck();
		}
		
	}
	
	private void drawCheck() {
		if (isChecked) {
			imageCheck.setImageResource(android.R.drawable.checkbox_on_background);
		} else {
			imageCheck.setImageResource(android.R.drawable.checkbox_off_background);
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void toggle() {
		setChecked(!isChecked);
	}
}
