package com.loopj.android.http.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WaypointsActivity extends ListActivity {

    private static final String[] samples = new String[]{"GET", "POST", "DELETE", "PUT",  "FILE", "BINARY", "THREADING TIMEOUTS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, samples));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Class<?> targetClass;
        switch (position) {
            case 0:
            default:
                targetClass = GetSample.class;
                break;
            case 1:
                targetClass = PostSample.class;
                break;
            case 2:
                targetClass = DeleteSample.class;
                break;
            case 3:
                targetClass = PutSample.class;
                break;
            case 4:
                targetClass = FileSample.class;
                break;
            case 5:
                targetClass = BinarySample.class;
                break;
            case 6:
                targetClass = ThreadingTimeoutSample.class;
                break;
        }
        if (targetClass != null)
            startActivity(new Intent(this, targetClass));
    }
}
