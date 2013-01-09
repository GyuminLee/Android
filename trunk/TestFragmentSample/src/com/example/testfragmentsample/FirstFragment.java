package com.example.testfragmentsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FirstFragment extends Fragment {

	TextView messageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_layout_first, container, false);
		messageView = (TextView)v.findViewById(R.id.message);
		Button btn = (Button)v.findViewById(R.id.btnShowHello);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				messageView.setText("hello");
			}
		});
		return v;
	}
}
