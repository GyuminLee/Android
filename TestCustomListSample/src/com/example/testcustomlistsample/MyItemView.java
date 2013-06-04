package com.example.testcustomlistsample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyItemView extends FrameLayout implements Checkable {

	ImageView iconView;
	TextView nameView;
	TextView descView;
	MyData mData;
	Drawable happy, neutral, sad;
	Boolean isChecked;
	
	OnItemImageClickListener mListener;
	
	public interface OnItemImageClickListener {
		public void onItemImageClick(MyData data);
	}
	
	public void setOnItemImageClickListener(OnItemImageClickListener listener) {
		mListener = listener;
	}
	
	public MyItemView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.imageView1);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onItemImageClick(mData);
				}
			}
		});
		
		nameView = (TextView)findViewById(R.id.textView1);
		descView = (TextView)findViewById(R.id.textView2);
		happy = context.getResources().getDrawable(R.drawable.stat_happy);
		neutral = context.getResources().getDrawable(R.drawable.stat_neutral);
		sad = context.getResources().getDrawable(R.drawable.stat_sad);
	}
	
	public void setMyData(MyData data) {
		mData = data;
		nameView.setText(data.name);
		descView.setText(data.desc);
		if (data.age < 25) {
			iconView.setImageDrawable(happy);
		} else if (data.age >= 25 && data.age < 30) {
			iconView.setImageDrawable(neutral);
		} else {
			iconView.setImageDrawable(sad);
		}
		isChecked=false;
		redrawBackground();
	}

	@Override
	public boolean isChecked() {
		
		return isChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		isChecked = checked;
		redrawBackground();
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		redrawBackground();
	}
	
	private void redrawBackground() {
		if (isChecked) {
			setBackgroundColor(Color.BLACK);
		} else {
			setBackgroundColor(Color.WHITE);
		}
	}
}
