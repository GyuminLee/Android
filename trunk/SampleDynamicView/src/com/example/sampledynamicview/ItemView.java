package com.example.sampledynamicview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	EditText editText;
	MyData mData;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		editText = (EditText)findViewById(R.id.editText1);
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (mData != null) {
					mData.mText = s.toString();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setMyData(MyData data) {
		mData = data;
		editText.setText(data.mText);
	}
}
