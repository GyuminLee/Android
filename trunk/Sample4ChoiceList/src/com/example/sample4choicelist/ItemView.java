package com.example.sample4choicelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ItemView extends FrameLayout implements Checkable {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView messageView;
	ImageView checkView;
	MyData mData;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.icon_view);
		messageView = (TextView)findViewById(R.id.message_view);
		checkView = (ImageView)findViewById(R.id.image_check);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		iconView.setImageResource(data.resId);
		messageView.setText(data.message);
	}

	public void setChoiceMode(int choiceMode) {
		if (choiceMode == ListView.CHOICE_MODE_MULTIPLE) {
			checkView.setVisibility(View.VISIBLE);
			drawIsCheck();
		} else {
			checkView.setVisibility(View.GONE);
		}
	}
	
	boolean isChecked;
	
	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			drawIsCheck();
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
	
	private void drawIsCheck() {
		if (isChecked) {
			checkView.setImageResource(android.R.drawable.checkbox_on_background);
		} else {
			checkView.setImageResource(android.R.drawable.checkbox_off_background);
		}
	}

}
