package com.example.testfragmentsample2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentF1 extends Fragment {

	TextView tv;
	EditText et;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f1_layout, container , false);
		tv = (TextView)v.findViewById(R.id.textView1);
		et = (EditText)v.findViewById(R.id.editText1);
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return v;
	}
}
