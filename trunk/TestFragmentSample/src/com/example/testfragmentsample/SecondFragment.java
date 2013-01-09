package com.example.testfragmentsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondFragment extends Fragment {

	EditText editText;
	TextView textView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_layout_second, container, false);
		editText = (EditText)v.findViewById(R.id.editText1);
		textView = (TextView)v.findViewById(R.id.textView1);
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textView.setText(editText.getText().toString());
			}
		});
		return v;
	}
}
