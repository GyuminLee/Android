package com.example.testfragmentsample2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentF2 extends Fragment {

	ImageView iv;
	private final static int REQUEST_CODE_GET_IMAGE = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f2_layout, container, false);
		iv = (ImageView)v.findViewById(R.id.imageView1);
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i,REQUEST_CODE_GET_IMAGE);
			}
		});
		
		Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_GET_IMAGE) {
			if (resultCode == Activity.RESULT_OK) {
				Uri uri = data.getData();
				iv.setImageURI(uri);
			}
		}
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.f2_menu, menu);
	}
	
}
