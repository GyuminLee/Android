package com.example.sample4dialogfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyDialogFragment f = new MyDialogFragment();
				f.show(getSupportFragmentManager(), "dialog");
			}
		});
		
		btn = (Button)findViewById(R.id.btn_custom);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyCustomDialogFragment f = new MyCustomDialogFragment();
				f.show(getSupportFragmentManager(), "dialog");
			}
		});
	}
}
