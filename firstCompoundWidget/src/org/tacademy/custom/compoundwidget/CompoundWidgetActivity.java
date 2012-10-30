package org.tacademy.custom.compoundwidget;

import android.app.Activity;
import android.os.Bundle;

public class CompoundWidgetActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MyCompoundControl ctl = (MyCompoundControl)findViewById(R.id.cwidget);
        ctl.getA();
    }
}