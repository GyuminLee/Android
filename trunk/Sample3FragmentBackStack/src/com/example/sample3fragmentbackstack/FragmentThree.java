package com.example.sample3fragmentbackstack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentThree extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		String name = b.getString("name");
	}
	
	public interface OnResultListener {
		public void onResult(String text);
	}
	OnResultListener mListener;
	
	public void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView view = new TextView(getActivity());
		view.setText("FragmentThree");
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onResult("FragmentThree");
				}
			}
		});
		return view;
	}

}
