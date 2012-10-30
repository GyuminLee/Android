package org.tacademy.basic.sectionlist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SectionListActivityActivity extends Activity {
    /** Called when the activity is first created. */
	
	ArrayList<MyData> myDataList = new ArrayList<MyData>();
	ListView list;
	SectionAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myDataList.add(new MyData(1,"section1","data1-1"));
        myDataList.add(new MyData(1,"section1","data1-2"));
        myDataList.add(new MyData(2,"section2","data2-1"));
        myDataList.add(new MyData(2,"section2","data2-2"));
        myDataList.add(new MyData(2,"section2","data2-3"));
        myDataList.add(new MyData(3,"section3","data3-1"));
        myDataList.add(new MyData(3,"section3","data3-2"));
        myDataList.add(new MyData(4,"section4","data4-1"));
        myDataList.add(new MyData(4,"section4","data4-2"));
        myDataList.add(new MyData(4,"section4","data4-3"));
        
        list = (ListView)findViewById(R.id.listView1);
        mAdapter = new SectionAdapter(this,myDataList,20,list);
        mAdapter.setOnSectionItemClickListener(new SectionAdapter.OnSectionItemClickListener() {
			
			public void onSectionClick(MyData data) {
				Toast.makeText(getApplicationContext(), "section click", Toast.LENGTH_SHORT).show();
			}
			
			public void onMoreClick() {
				Toast.makeText(getApplicationContext(), "more click", Toast.LENGTH_SHORT).show();
			}
			
			public void onItemClick(MyData data) {
				Toast.makeText(getApplicationContext(), "item click", Toast.LENGTH_SHORT).show();
			}
		});

        list.setAdapter(mAdapter);
        
    }

	public void onMoreItemClick() {
		// ... 
	}
}