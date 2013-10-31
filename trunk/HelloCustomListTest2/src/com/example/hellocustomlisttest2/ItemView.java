package com.example.hellocustomlisttest2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ItemView extends FrameLayout implements Checkable {

	TextView textView;
	CheckBox checkBox;
	
	public ItemView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		textView = (TextView)findViewById(R.id.textView1);
		checkBox = (CheckBox)findViewById(R.id.checkBox1);
	}
	
	public void setText(String text) {
		textView.setText(text);
	}

	public void setChoiceMode(int mode) {
		if (mode == ListView.CHOICE_MODE_MULTIPLE) {
			checkBox.setVisibility(View.VISIBLE);
		} else {
			checkBox.setVisibility(View.GONE);
		}
	}
	@Override
	public boolean isChecked() {
		return checkBox.isChecked();
	}

	@Override
	public void setChecked(boolean checked) {
		checkBox.setChecked(checked);
	}

	@Override
	public void toggle() {
		checkBox.toggle();
	}
}
