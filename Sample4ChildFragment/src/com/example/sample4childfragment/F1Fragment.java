package com.example.sample4childfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class F1Fragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f1_fragment, container, false);
		Button btn = (Button) v.findViewById(R.id.btn_sub1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getChildFragmentManager().beginTransaction()
						.replace(R.id.container, new Sub1Fragment()).commit();
			}
		});
		btn = (Button) v.findViewById(R.id.btn_sub2);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getChildFragmentManager().beginTransaction()
						.replace(R.id.container, new Sub2Fragment()).commit();
			}
		});
		getChildFragmentManager().beginTransaction()
				.add(R.id.container, new Sub1Fragment()).commit();
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
