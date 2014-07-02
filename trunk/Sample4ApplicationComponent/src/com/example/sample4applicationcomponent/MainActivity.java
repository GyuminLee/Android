package com.example.sample4applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	EditText keyword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keyword = (EditText)findViewById(R.id.editText1);
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
				i.putExtra(MyActivity.PARAM_AGE, 40);
				startActivity(i);
			}
		});
		
		
	}
}
