package org.tacademy.basic.sampletabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Tab3Fragment extends TabFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.tab_layout, container, false);
		TextView tv = (TextView)v.findViewById(R.id.textView1);
		tv.setText("fragment3");
		return v;
	}
}
