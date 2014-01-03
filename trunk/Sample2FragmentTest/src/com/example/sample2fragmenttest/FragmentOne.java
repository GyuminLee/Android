package com.example.sample2fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentOne extends Fragment {
	TextView tv;
	ImageView iv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f1_layout, container, false);
		tv = (TextView)v.findViewById(R.id.textView1);
		iv = (ImageView)v.findViewById(R.id.imageView1);
		return v;
	}
}
