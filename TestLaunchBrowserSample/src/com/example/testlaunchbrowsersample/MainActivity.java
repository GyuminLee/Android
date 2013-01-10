package com.example.testlaunchbrowsersample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	final static int REQUEST_CODE_MYACTIVITY = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(Intent.ACTION_DIAL, 
//						Uri.parse("tel:0100000000"));
				Intent i = new Intent(MainActivity.this,MyActivity.class);
//				i.putExtra(MyActivity.PARAM_NAME, "ysi");
//				i.putExtra(MyActivity.PARAM_AGE, 39);
				Profile p = new Profile("ysi",39);
				i.putExtra(MyActivity.PARAM_PROFILE, p);
				startActivityForResult(i,REQUEST_CODE_MYACTIVITY);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE_MYACTIVITY) {
			if (resultCode == Activity.RESULT_OK) {
				String resultName = data.getStringExtra(MyActivity.RESULT_PARAM_NAME);
				int resultAge = data.getIntExtra(MyActivity.RESULT_PARAM_AGE, -1);
				Toast.makeText(this, "resultName : " + resultName + ", resultAge" + resultAge, 
						Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
