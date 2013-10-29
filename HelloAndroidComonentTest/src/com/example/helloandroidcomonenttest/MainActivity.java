package com.example.helloandroidcomonenttest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageView imageView;
	public static final int REQUEST_CODE_MY_ACTIVITY = 0;
	public static final int REQUEST_CODE_GET_CONTENT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btnMyActivity);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				Person p = new Person();
				p.name = "yes";
				p.age = 39;
				p.weight = 90;
				p.height = 170;
				i.putExtra(MyActivity.PARAM_PERSION, p);
				// ...
				startActivityForResult(i, REQUEST_CODE_MY_ACTIVITY);
			}
		});
		
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				startActivity(i);
			}
		});
		imageView = (ImageView)findViewById(R.id.imageView1);
		btn = (Button)findViewById(R.id.btnGetPicture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, REQUEST_CODE_GET_CONTENT);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_MY_ACTIVITY && resultCode == Activity.RESULT_OK) {
			String message = data.getStringExtra(MyActivity.RESULT_STRING);
			int resultAge = data.getIntExtra(MyActivity.RESULT_AGE, 0);
			Toast.makeText(this,
					"result : " + message + ", resultAge : " + resultAge,
					Toast.LENGTH_SHORT).show();
		} else if (requestCode == REQUEST_CODE_GET_CONTENT && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			imageView.setImageURI(uri);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
