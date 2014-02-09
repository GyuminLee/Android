package com.example.samplecropparamtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
	private static final String TEMP_CAMERA_FILE = "temporary_camera.jpg";
	
	private static final int REQUEST_GALLERY = 0;
	private static final int REQUEST_CAMERA = 1;
	private static final int REQUEST_CROP = 2;
	
	Uri imageUri;
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView1);
		Button btn = (Button) findViewById(R.id.btnGetImage);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, REQUEST_GALLERY);
			}
		});

		btn = (Button) findViewById(R.id.btnCamera);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri fileUri = getTempCameraUri();
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(intent,REQUEST_CAMERA);
			}
		});
	}

	private void cropImage(Uri uri) {
		if (uri != null) {
			Intent photoPickerIntent = new Intent(
					"com.android.camera.action.CROP", uri);
			photoPickerIntent.putExtra("aspectX", 1);
			photoPickerIntent.putExtra("aspectY", 1);
			photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					getTempUri());
			photoPickerIntent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			startActivityForResult(photoPickerIntent, REQUEST_CROP);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_GALLERY:
			Uri uri = data.getData();
			cropImage(uri);
			break;
		case REQUEST_CAMERA:
			String imagePath = Environment.getExternalStorageDirectory() + "/"
					+ TEMP_CAMERA_FILE;
			try {
				String url = MediaStore.Images.Media.insertImage(getContentResolver(), imagePath, "camera image", "original image");
				Uri photouri = Uri.parse(url);
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.ORIENTATION, 270);
				getContentResolver().update(photouri, values, null, null);
				cropImage(photouri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case REQUEST_CROP:
			String filePath = Environment.getExternalStorageDirectory() + "/"
					+ TEMP_PHOTO_FILE;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;
			Bitmap selectedImage = BitmapFactory.decodeFile(filePath, options);
			imageView.setImageBitmap(selectedImage);
			break;
		}
	}

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			File file = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE);
			try {
				file.createNewFile();
			} catch (IOException e) {
			}

			return file;
		} else {

			return null;
		}
	}
	
	private Uri getTempCameraUri() {
		return Uri.fromFile(getCameraFile());
	}
	
	private File getCameraFile() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			File file = new File(Environment.getExternalStorageDirectory(),
					TEMP_CAMERA_FILE);
			try {
				file.createNewFile();
			} catch (IOException e) {
			}

			return file;
		} else {

			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
