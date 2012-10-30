package org.tacademy.basic.sampletabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Tab1Fragment extends TabFragment {

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
		tv.setText("fragment1");
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TabFragment f = new Tab11Fragment();
				//addFragment(f);
				addFragmentToBackStack(f);
			}
		});
		return v;
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return super.onBackPressed();
	}

	@Override
	public void onFragmentResult(int requestCode, int resultCode, Bundle b) {
		// TODO Auto-generated method stub
		super.onFragmentResult(requestCode, resultCode, b);
		Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
	}
	
	

}
