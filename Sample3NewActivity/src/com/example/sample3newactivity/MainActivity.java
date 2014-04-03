package com.example.sample3newactivity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	EditText nameView;
	EditText ageView;
	TextView resultView;
	
	public static final int REQUEST_CODE_MY_ACTIVITY = 0;
	public static final int REQUEST_CODE_GET_IMAGE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nameView = (EditText)findViewById(R.id.editName);
		ageView = (EditText)findViewById(R.id.editAge);
		resultView = (TextView)findViewById(R.id.textResult);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				Intent i = new Intent(MainActivity.this,MyActivity.class);
				String name = nameView.getText().toString();
				int age = Integer.parseInt(ageView.getText().toString());
				Person p = new Person();
				p.name = name;
				p.age = age;
				i.putExtra(MyActivity.PARAM_PERSON, p);
//				i.putExtra(MyActivity.PARAM_NAME, name);
//				i.putExtra(MyActivity.PARAM_AGE, age);
				startActivityForResult(i,REQUEST_CODE_MY_ACTIVITY);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i,REQUEST_CODE_GET_IMAGE);
			}
		});
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				File file = new File("/sdcard/a.pdf");
				Uri uri = Uri.fromFile(file);
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setDataAndType(uri, "application/pdf");
				startActivity(i);
//				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//				i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//				startActivityForResult(i,2);
			}
		});
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_MY_ACTIVITY &&
				resultCode == Activity.RESULT_OK) {
			String message = data.getStringExtra(MyActivity.RESULT_MESSAGE);
			resultView.setText(message);
		} else if (requestCode == REQUEST_CODE_GET_IMAGE) {
			Uri uri = data.getData();
			ImageView imageView = (ImageView)findViewById(R.id.imageView1);
			imageView.setImageURI(uri);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//	}

}
