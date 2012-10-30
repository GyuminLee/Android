package org.tacademy.basic.sampletabfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TabHost;

public class SampleTabActivity extends TabFragmentActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        setupTab(R.id.realtabcontent);
        
        addTab(getNewTabSpec("tab1").setIndicator("Tab1"), Tab1Fragment.class, null,false);
        addTab(getNewTabSpec("tab2").setIndicator("Tab2"), Tab2Fragment.class, null);
        addTab(getNewTabSpec("tab3").setIndicator("Tab3"), Tab3Fragment.class, null);
        setCurrentTab(0);
    }    
}
