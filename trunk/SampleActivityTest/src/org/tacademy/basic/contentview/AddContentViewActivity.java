package org.tacademy.basic.contentview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddContentViewActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    LayoutInflater inflater = getLayoutInflater();
	    inflater.setFactory(this);
	    View layout1 = inflater.inflate(R.layout.layout_1, null);
	    View layout2 = inflater.inflate(R.layout.layout_2, null);
	    addContentView(layout1,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
	    addContentView(layout2,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
	    // TODO Auto-generated method stub
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		Log.i(getClass().getSimpleName(),"View name : " + name);
		return super.onCreateView(name, context, attrs);
	}

}
