package com.example.samplemultiuploadtest;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {

	AsyncHttpClient client = new AsyncHttpClient();
	String uploadUrl = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btnUpload);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
				Cursor c = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
				RequestParams params = new RequestParams();
				int i = 0;
				while(c.moveToNext() && i < 2) {
					String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
					if (path != null && !path.equals("")) {
						try {
							params.put("upfile", new File(path));
							i++;
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				client.post(uploadUrl, params, new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						String text = new String(responseBody);
						Toast.makeText(MainActivity.this, "message : " + text, Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		client.get("http://dongjahellowebapp.appspot.com/sampleuploadtest", new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				uploadUrl = new String(responseBody);
				uploadUrl = uploadUrl.trim();
				Toast.makeText(MainActivity.this, "upload url : " + uploadUrl, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
