package com.example.sample4applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText keyword;
	EditText ageView;

	public static final int REQUEST_CODE_RESULT_AGE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keyword = (EditText)findViewById(R.id.editText1);
		ageView = (EditText)findViewById(R.id.editText2);
		Button btn = (Button)findViewById(R.id.button1);
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				String filepath = "/sdcard/xxx.pdf";
//				Intent i = new Intent(Intent.ACTION_VIEW);
//				i.setDataAndType(Uri.fromFile(new File(filepath)), "application/pdf");
//				startActivity(i);
				
//				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
//				startActivity(i);
				
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				i.putExtra(MyActivity.PARAM_NAME, keyword.getText().toString());
				i.putExtra(MyActivity.PARAM_AGE, Integer.parseInt(ageView.getText().toString()));
				startActivityForResult(i, REQUEST_CODE_RESULT_AGE);
			}
		});		
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_RESULT_AGE && resultCode == Activity.RESULT_OK) {
			String message = data.getStringExtra(MyActivity.PARAM_RESULT);
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}
}
