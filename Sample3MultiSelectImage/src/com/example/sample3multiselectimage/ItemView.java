package com.example.sample3multiselectimage;

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

	ImageView imageCheckView;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		imageCheckView = (ImageView)findViewById(R.id.imageCheck);
	}

	boolean isChecked = false;
	
	private void updateUI() {
		if (isChecked) {
			imageCheckView.setImageResource(android.R.drawable.checkbox_on_background);
		} else {
			imageCheckView.setImageResource(android.R.drawable.checkbox_off_background);
		}
	}
	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			updateUI();
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		updateUI();
	}

}
