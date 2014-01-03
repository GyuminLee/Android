package com.example.sample2parcelable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,MyActivity.class);
				Person p = makePerson();
//				i.putExtra(MyActivity.PARAM_NAME, p.name);
//				i.putExtra(MyActivity.PARAM_AGE, p.age);
//				i.putExtra(MyActivity.PARAM_WEIGHT, p.weight);
				i.putExtra(MyActivity.PARAM_PERSON, p);
				startActivity(i);
			}
		});
	}

	private Person makePerson() {
		Person p = new Person();
		p.name = "ysi";
		p.age = 40;
		p.weight = 80;
		return p;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
