package com.example.samplewebviewtest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow {

	public MyPopupWindow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.my_popup_layout, null);
		setWidth(200);
		setHeight(200);
		setContentView(view);
		view.findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

}
