package com.example.sample3choicelist;

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
	
	TextView textView;
	ImageView iconView;
	int choiceMode = ListView.CHOICE_MODE_NONE;
	boolean isChecked = false;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		textView = (TextView)findViewById(R.id.textView1);
		iconView = (ImageView)findViewById(R.id.imageView1);
	}
	
	public void setText(String text) {
		textView.setText(text);
	}
	
	public void setChoiceMode(int choiceMode) {
		if (this.choiceMode != choiceMode) {
			this.choiceMode = choiceMode;
			if (choiceMode == ListView.CHOICE_MODE_NONE) {
				iconView.setVisibility(View.GONE);
			} else {
				iconView.setVisibility(View.VISIBLE);
			}
			drawCheck();
		}
	}

	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			drawCheck();
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		drawCheck();
	}
	
	private void drawCheck() {
		if (choiceMode == ListView.CHOICE_MODE_SINGLE) {
			if (isChecked) {
				iconView.setImageResource(android.R.drawable.radiobutton_on_background);
			} else {
				iconView.setImageResource(android.R.drawable.radiobutton_off_background);
			}
		} else if (choiceMode == ListView.CHOICE_MODE_MULTIPLE) {
			if (isChecked) {
				iconView.setImageResource(android.R.drawable.checkbox_on_background);
			} else {
				iconView.setImageResource(android.R.drawable.checkbox_off_background);
			}
		}
	}
	

}
