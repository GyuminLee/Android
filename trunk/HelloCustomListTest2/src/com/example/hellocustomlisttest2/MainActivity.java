package com.example.hellocustomlisttest2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ListView listView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		listView.setEmptyView(findViewById(R.id.textView1));
//		listView.addHeaderView(new TextView(this));
//		listView.addFooterView(new TextView(this));
		ArrayList<String> data = new ArrayList<String>();
		data.add("a");
		data.add("b");
		data.add("c");
		data.add("d");
		data.add("e");
		data.add("f");
		data.add("g");
		data.add("h");
		data.add("i");
		mAdapter = new MyAdapter(this,data);
		listView.setAdapter(mAdapter);
//		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//		mAdapter.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
