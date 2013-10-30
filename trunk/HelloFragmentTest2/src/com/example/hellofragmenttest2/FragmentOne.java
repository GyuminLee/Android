package com.example.hellofragmenttest2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentOne extends Fragment {

	TextView messageView;
	EditText keywordView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_one_layout, container, false);
		messageView = (TextView)v.findViewById(R.id.messageView);
		keywordView = (EditText)v.findViewById(R.id.keywordView);
		Button btn = (Button)v.findViewById(R.id.btnSend);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				messageView.setText(keywordView.getText().toString());
			}
		});
		return v;
	}
}
