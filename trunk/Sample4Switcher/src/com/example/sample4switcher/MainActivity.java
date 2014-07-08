package com.example.sample4switcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageSwitcher switcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		switcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				ImageView iv = (ImageView)switcher.getNextView();
//				iv.setImageResource(R.drawable.photo1);
				switcher.showNext();
			}
		});
	}
}
