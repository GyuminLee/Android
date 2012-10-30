package org.tacademy.basic.popupwindow;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SamplePopupWindowActivity extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ArrayList<String> items = new ArrayList<String>();
        items.add("aaaa");
        items.add("bbbb");
        items.add("cccc");
        
        ItemAdapter adapter = new ItemAdapter(this,items);
        ListView list = (ListView)findViewById(R.id.listView1);
        list.setAdapter(adapter);
        
    }
}