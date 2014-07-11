package com.example.sample4imagefile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageView imageView;
	String path;
	Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView)findViewById(R.id.imageView2);
		Button btn = (Button)findViewById(R.id.btn_get_image);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, 0);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_read_data);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (path != null) {
					try {
						FileInputStream fis = new FileInputStream(new File(path));
						BitmapFactory.Options opts = new BitmapFactory.Options();
						opts.inSampleSize = 4;
						bitmap = BitmapFactory.decodeStream(fis, null, opts);
						imageView.setImageBitmap(bitmap);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
		
		btn = (Button)findViewById(R.id.btn_save_data);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				File camera = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//				if (camera.exists()) {
//					camera.mkdir();
//				}
//				File saveFile = new File(camera,"aaa.jpg");
				File saveFile = new File(Environment.getExternalStorageDirectory(),"aaa.jpg");
				try {
					FileOutputStream fos = new FileOutputStream(saveFile);
					if (bitmap != null) {
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					}
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
			if (c.moveToNext()) {
				path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
				Toast.makeText(this, "path : " + path, Toast.LENGTH_SHORT).show();
			}
			c.close();
		}
	}
}
