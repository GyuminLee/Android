package com.example.hellofragmenttest2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FragmentTwo extends Fragment {
	ImageView imagePicture;
	
	public static final int REQUEST_CODE_PICTURE = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_two_layout, container, false);
		imagePicture = (ImageView)v.findViewById(R.id.imagePicture);
		Button btn = (Button)v.findViewById(R.id.btnPicture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, REQUEST_CODE_PICTURE);
			}
		});
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PICTURE && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			imagePicture.setImageURI(uri);
		}
	}
}
