package com.example.sampleocr302;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends Activity {

	public final static String TESSBASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tesseract/";
	public final static String DEFAULT_LANGUAGE = "eng";
	public final static String TESSDATA_PATH = "tessdata";
	TessBaseAPI baseApi;
	public final static int REQUEST_PICTURE = 0;
	ImageView imageView;
	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnRecognizeText);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, REQUEST_PICTURE);
			}
		});
		imageView = (ImageView)findViewById(R.id.imageView1);
		textView = (TextView)findViewById(R.id.textView1);
		new FileCheckTask().execute("");
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			imageView.setImageURI(uri);
			new DetectTextTask().execute(uri);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class DetectTextTask extends AsyncTask<Uri, Integer, String> {
		@Override
		protected String doInBackground(Uri... params) {
			Uri uri = params[0];
			if (baseApi == null) return null;
			Cursor c = getContentResolver().query(uri, new String[] {MediaStore.Images.Media.DATA}, null, null, null);
			if (c.moveToNext()) {
				String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				Bitmap bm = BitmapFactory.decodeFile(path, options);
				baseApi.setImage(bm);
				String text = baseApi.getUTF8Text();
				c.close();
				return text;
			}
			c.close();
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				Toast.makeText(MainActivity.this, "detect : " + result, Toast.LENGTH_SHORT).show();
				textView.setText(result);
			}
		}
	}
	
	class FileCheckTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			File tessbaseFile = new File(TESSBASE_PATH);
			if (!tessbaseFile.exists()) {
				tessbaseFile.mkdir();
			}
			File tessdataFile = new File(tessbaseFile,TESSDATA_PATH);
			if (!tessdataFile.exists()) {
				tessdataFile.mkdir();
			}
			
			for (TesseractData data : TesseractData.fileData) {
				File file = new File(tessdataFile,data.fileName);
				if (!file.exists()) {
					InputStream is = getResources().openRawResource(data.resId);
					try {
						FileOutputStream os = new FileOutputStream(file);
						byte[] buffer = new byte[64 * 1024];
						int len;
						while((len = is.read(buffer)) > 0) {
							os.write(buffer, 0, len);
						}
						os.flush();
						os.close();
						is.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			baseApi = new TessBaseAPI();
			baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
			Toast.makeText(MainActivity.this, "ready for detect", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (baseApi != null) {
			baseApi.end();
		}
	}

}
