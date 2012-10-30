package org.tacademy.basic.samplelist2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SampleList2Activity extends Activity {
    /** Called when the activity is first created. */
//	String[] data = {"aaa", "bbb", "ccc"};
	MyData[] data = { new MyData(R.drawable.ic_launcher,"aaa"),
		new MyData(R.drawable.ic_launcher,"bbb"),
		new MyData(R.drawable.ic_launcher,"ccc")
	};
	MyAdapter aa;
	ListView list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list = (ListView)findViewById(R.id.listView1);
        // adapter »ý¼º
        // list setting
        aa = new MyAdapter(this,data);
        aa.setOnMyAdapterListener(new MyAdapter.OnMyAdapterListener() {
			
			public void onItemClick(MyData data) {
				// ...
			}
		});
        list.setAdapter(aa);
        list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				MyData data = (MyData)aa.getItem(position);
				Intent i = new Intent(SampleList2Activity.this, TestActivity.class);
				i.putExtra("data", data);
				startActivity(i);
			}
		});
        
    }
}