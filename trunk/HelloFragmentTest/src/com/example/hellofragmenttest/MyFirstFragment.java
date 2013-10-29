package com.example.hellofragmenttest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFirstFragment extends Fragment {

	TextView messageView;
	EditText keywordView;
	ImageView imageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout, container, false);
		messageView = (TextView)v.findViewById(R.id.messageView);
		keywordView = (EditText)v.findViewById(R.id.keywordView);
		Button btn = (Button)v.findViewById(R.id.btnSend);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				messageView.setText(keywordView.getText().toString());
				Toast.makeText(getActivity(), "fragment button clicked", Toast.LENGTH_SHORT).show();
			}
		});
		
		imageView = (ImageView)v.findViewById(R.id.imagePicture);
		btn = (Button)v.findViewById(R.id.btnGetPicture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i,0);
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			imageView.setImageURI(uri);
		}
	}
	
	public String getTextMessage() {
		return messageView.getText().toString();
	}
}
