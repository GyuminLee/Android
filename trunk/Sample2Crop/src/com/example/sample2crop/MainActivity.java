package com.example.sample2crop;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	public static final int REQUEST_CODE_CROP = 0;
	File mSavedFile;
	
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView)findViewById(R.id.image);
		Button btn = (Button)findViewById(R.id.btnAlbum);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(
						Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				photoPickerIntent.setType("image/*");
				photoPickerIntent.putExtra("crop", "true");
				photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
				photoPickerIntent.putExtra("outputFormat",
						Bitmap.CompressFormat.JPEG.toString());				
				startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP);
			}
		});
		
		btn = (Button)findViewById(R.id.btnCamera);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				photoPickerIntent.putExtra("crop", "circle");
				photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
				photoPickerIntent.putExtra("outputFormat",
						Bitmap.CompressFormat.JPEG.toString());				
				startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP);
			}
		});
		if (savedInstanceState != null) {
			mSavedFile = new File(savedInstanceState.getString("filename"));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CROP && resultCode == RESULT_OK) {
			Bitmap bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
			imageView.setImageBitmap(bm);
		}
	}
	
	private Uri getTempUri() {
		mSavedFile = new File(Environment.getExternalStorageDirectory(),"temp_" + System.currentTimeMillis()/1000);
		
		return Uri.fromFile(mSavedFile);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("filename", mSavedFile.getAbsolutePath());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
