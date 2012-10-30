package org.tacademy.basic.sampletabfragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Tab11Fragment extends TabFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.tab_layout, container, false);
		TextView tv = (TextView)v.findViewById(R.id.textView1);
		tv.setText("fragment11");
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putString("test", "test");
				popBackStack(Activity.RESULT_OK,b);
			}
		});
		return v;
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return super.onBackPressed();
	}

}
