package com.example.sample3fragmentlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyFragment extends Fragment {

	EditText editView;
	TextView textView;

	String filename;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			filename = savedInstanceState.getString("filename");
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("filename", filename);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout, container, false);
		editView = (EditText)v.findViewById(R.id.editText1);
		textView = (TextView)v.findViewById(R.id.textView1);
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				filename = editView.getText().toString();
				textView.setText(editView.getText().toString());
			}
		});
		return v;
	}
	
	public String getText() {
		return editView.getText().toString();
	}
}
