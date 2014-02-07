package com.example.sample2fragmenttab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSubOne extends ChildFragment {
	ImageView imageView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_sub_one, container, false);
		TextView tv = (TextView)v.findViewById(R.id.subOne);
		tv.setText("sub one");
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentOne f = (FragmentOne)getTargetFragment();
				int code = getTargetRequestCode();
				Intent data = new Intent();
				data.putExtra("next", 1);
				f.onActivityResult(code, Activity.RESULT_OK, data);
			}
		});
		imageView = (ImageView)v.findViewById(R.id.imageView1);
		Button btn = (Button)v.findViewById(R.id.takePicture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, 0);
			}
		});
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			Toast.makeText(getActivity(), "receive result", Toast.LENGTH_SHORT).show();
		}
	}
}
