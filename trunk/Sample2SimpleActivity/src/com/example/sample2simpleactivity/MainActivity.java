package com.example.sample2simpleactivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sample2simpleactivity.child.MyActivity;

public class MainActivity extends Activity {

	private final static int REQUEST_CODE_MY_ACTIVITY = 0;
	private final static int REQUEST_CODE_GET_PICTURE = 1;
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView)findViewById(R.id.imageView1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				i.putExtra(MyActivity.PARAM_NAME, "ysi");
				i.putExtra(MyActivity.PARAM_AGE, 40);
				startActivityForResult(i, REQUEST_CODE_MY_ACTIVITY);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, REQUEST_CODE_GET_PICTURE);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_MY_ACTIVITY && resultCode == RESULT_OK) {
			String text = data.getStringExtra(MyActivity.PARAM_RESULT);
			Toast.makeText(this, "result : " + text, Toast.LENGTH_SHORT).show();
		} else if (requestCode == REQUEST_CODE_GET_PICTURE && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			imageView.setImageURI(uri);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
