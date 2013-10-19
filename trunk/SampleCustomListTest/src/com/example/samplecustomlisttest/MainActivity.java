package com.example.samplecustomlisttest;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samplecustomlisttest.MyItemAdapter.OnAdapterImageClickListener;

public class MainActivity extends Activity {

	ListView list;
	MyItemAdapter mAdapter;
	ImageView showImageView;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		list = (ListView)findViewById(R.id.listView1);
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		ItemData item = new ItemData();
		item.title = "title1";
		item.desc = "desc1";
		item.imageId = R.drawable.gallery_photo_1;
		
		data.add(item);
		
		item = new ItemData();
		item.title = "title2";
		item.desc = "desc2";
		item.imageId = R.drawable.gallery_photo_2;
		data.add(item);

		item = new ItemData();
		item.title = "title3";
		item.desc = "desc3";
		item.imageId = R.drawable.gallery_photo_3;
		data.add(item);
		
		mAdapter = new MyItemAdapter(this,data);
		
		mAdapter.setOnAdapterImageClickListener(new OnAdapterImageClickListener() {
			
			@Override
			public void onAdapterImageClicked(MyItemAdapter adapter, ItemView view,
					ItemData item) {
				showImageView.setImageResource(item.imageId);
				showImageView.setVisibility(View.VISIBLE);
			}
		});
		list.setAdapter(mAdapter);
				
		showImageView = (ImageView)findViewById(R.id.showImage);
		showImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showImageView.setVisibility(View.GONE);
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
