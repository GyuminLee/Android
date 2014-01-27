package com.example.sample2fragmentargumentpassing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentOne extends Fragment {
	Bundle b;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		b= getArguments();
	}
	
	public interface OnButtonClickListener {
		public void onButtonClick(String keyword);
	}
	OnButtonClickListener mListener;
	public void setOnButtonClickListener(OnButtonClickListener listener) {
		mListener = listener;
	}

	TextView messageView;
	public final static int REQUEST_CODE_GET_MESSAGE = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f1_layout, container, false);
		messageView = (TextView)v.findViewById(R.id.messageView);
		Button btn = (Button)v.findViewById(R.id.btnShowInput);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment f = new FragmentTwo();
				f.setTargetFragment(FragmentOne.this, REQUEST_CODE_GET_MESSAGE);
				ft.replace(R.id.container, f);
				ft.addToBackStack(null);
				ft.commit();
				if (mListener != null) {
					mListener.onButtonClick(messageView.getText().toString());
				}
			}
		});
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_GET_MESSAGE && resultCode == Activity.RESULT_OK) {
			messageView.setText(data.getStringExtra("keyword"));
		}
	}
}
