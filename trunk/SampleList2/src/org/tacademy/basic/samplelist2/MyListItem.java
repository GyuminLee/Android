package org.tacademy.basic.samplelist2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyListItem extends LinearLayout {

	ImageView image;
	TextView text;
	MyData mData;
	
	public interface OnMyItemClickListener {
		public void onItemClick(MyData data);
	}
	OnMyItemClickListener mListener;
	public void setOnMyItemClickListener(OnMyItemClickListener listener) {
		this.mListener = listener;
	}
	
	public MyListItem(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.my_row, this);
		image = (ImageView)findViewById(R.id.imageView1);
		text = (TextView)findViewById(R.id.textView1);
		image.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// ... 
				if (mListener != null) {
					mListener.onItemClick(mData);
				}
			}
		});
	}
	
	public void setData(MyData data) {
		// image.setImageResource();
		mData = data;
		image.setImageResource(data.imageResId);
		text.setText(data.text);
	}

}
