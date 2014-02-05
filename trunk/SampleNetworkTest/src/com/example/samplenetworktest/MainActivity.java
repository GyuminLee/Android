package com.example.samplenetworktest;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.samplenetworktest.NetworkModel.OnResultListener;

public class MainActivity extends Activity {

	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkModel.getInstance().getMyDataList("ysi", new OnResultListener<MyList>() {
					
					@Override
					public void onSuccess(MyList result) {
						ArrayList<String> mData = new ArrayList<String>();
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, mData);
						listView.setAdapter(adapter);
					}
					
					@Override
					public void onError(int code) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
