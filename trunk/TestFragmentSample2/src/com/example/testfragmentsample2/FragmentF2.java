package com.example.testfragmentsample2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentF2 extends Fragment {

	ImageView iv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f2_layout, container, false);
		iv = (ImageView)v.findViewById(R.id.imageView1);
		return v;
	}
}
