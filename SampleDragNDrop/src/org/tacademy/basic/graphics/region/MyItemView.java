package org.tacademy.basic.graphics.region;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyItemView extends LinearLayout {

	TextView textView;
	String data;
	LinearLayout layout;
	
	public interface OnButtonClickListener {
		public void onModifyClick(View view,String text);
		public void onDeleteClick(View view,String text);
	}
	
	OnButtonClickListener mListener;
	
	public void setOnButtonClickListener(OnButtonClickListener listener) {
		mListener = listener;
	}
	
	public MyItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.item_layout,this);
		textView = (TextView)findViewById(R.id.textView2);
		layout = (LinearLayout)findViewById(R.id.linearLayout1);
		Button btn = (Button)findViewById(R.id.modify);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setLayoutVisibility(View.GONE);
				if (mListener != null) {
					mListener.onModifyClick(MyItemView.this,data);
				}
			}
		});
		
		btn = (Button)findViewById(R.id.delete);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setLayoutVisibility(View.GONE);
				if (mListener != null) {
					mListener.onDeleteClick(MyItemView.this,data);
				}
			}
		});
		
	}

	public void setLayoutVisibility(int visibility) {
		layout.setVisibility(visibility);
	}
	
	public void setData(String text) {
		data = text;
		textView.setText(text);
	}
}
