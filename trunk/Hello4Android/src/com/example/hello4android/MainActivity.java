package com.example.hello4android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView messageView;
	EditText inputView;
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.message_view);
		inputView = (EditText)findViewById(R.id.input_view);
		imageView = (ImageView)findViewById(R.id.image_view);
		Button btn = (Button)findViewById(R.id.send_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				messageView.setText(inputView.getText().toString());
			}
		});
		
		btn = (Button)findViewById(R.id.show_google);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:01022573585"));
//				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
				startActivity(i);
			}
		});
		
		btn = (Button)findViewById(R.id.get_picture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i, 0);
			}
		});
		
		btn = (Button)findViewById(R.id.show_my_activity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,MyActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			imageView.setImageURI(uri);
		}
	}
}
