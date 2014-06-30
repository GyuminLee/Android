package com.example.sample4layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button tab1, tab2, tab3;
	View content1, content2, content3;
	
	int currentTabIndex = -1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame_layout);
		tab1 = (Button)findViewById(R.id.button1);
		tab2 = (Button)findViewById(R.id.button2);
		tab3 = (Button)findViewById(R.id.button3);
		content1 = findViewById(R.id.content1);
		content2 = findViewById(R.id.content2);
		content3 = findViewById(R.id.content3);
		
		tab1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				processTab(0);
			}
		});
		tab2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				processTab(1);
			}
		});
		tab3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				processTab(2);
			}
		});
		
		processTab(0);
	}
	
	private void processTab(int index) {
		switch(index) {
		case 0 :
			tab1.setSelected(true);
			tab2.setSelected(false);
			tab3.setSelected(false);
			content1.setVisibility(View.VISIBLE);
			content2.setVisibility(View.GONE);
			content3.setVisibility(View.GONE);
			break;
		case 1 :
			tab1.setSelected(false);
			tab2.setSelected(true);
			tab3.setSelected(false);
			content1.setVisibility(View.GONE);
			content2.setVisibility(View.VISIBLE);
			content3.setVisibility(View.GONE);
			break;
		case 2 :
			tab1.setSelected(false);
			tab2.setSelected(false);
			tab3.setSelected(true);
			content1.setVisibility(View.GONE);
			content2.setVisibility(View.GONE);
			content3.setVisibility(View.VISIBLE);
			break;
		}
	}
}
