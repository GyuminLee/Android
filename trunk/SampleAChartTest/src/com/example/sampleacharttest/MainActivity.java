package com.example.sampleacharttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.line);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LineChartFragment f = new LineChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.bar);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BarChartFragment f = new BarChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.bubble);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BubbleChartFragment f = new BubbleChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.combined);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CombinedFragment f = new CombinedFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.cubic);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CubicLineChartFragment f = new CubicLineChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.dial);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialChartFragment f = new DialChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.doughnut);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DoughnutChartFragment f = new DoughnutChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.pie);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PieChartFragment f = new PieChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.rangebar);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RangeChartFragment f = new RangeChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.scatter);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ScatterChartFragment f = new ScatterChartFragment();
				replaceFragment(f);
			}
		});
		btn = (Button)findViewById(R.id.time);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimeChartFragment f = new TimeChartFragment();
				replaceFragment(f);
			}
		});
	}

	void replaceFragment(Fragment f) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.container, f);
		ft.commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
