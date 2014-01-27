package com.example.sample2fragmentargumentpassing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.example.sample2fragmentargumentpassing.FragmentOne.OnButtonClickListener;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentOne f = new FragmentOne();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Bundle b = new Bundle();
		b.putString("param", "value1");
		f.setArguments(b);
		f.setOnButtonClickListener(new OnButtonClickListener() {
			
			@Override
			public void onButtonClick(String keyword) {
				
			}
		});
		ft.replace(R.id.container, f);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
