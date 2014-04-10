package com.example.sample3viewanimation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MyActivity extends ActionBarActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.my_activity);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.set2_anim, R.anim.set_anim);
			}
		});
	    
	    btn = (Button)findViewById(R.id.button2);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				FragmentA f = new FragmentA();
				ft.replace(R.id.container, f);
				ft.setCustomAnimations(R.anim.set2_anim, R.anim.set_anim);
				ft.commit();
			}
		});
	    
	    btn = (Button)findViewById(R.id.button3);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				FragmentB f = new FragmentB();
				ft.replace(R.id.container, f);
				ft.setCustomAnimations(R.anim.set2_anim, R.anim.set_anim, R.anim.alpha_anim, R.anim.translate);
				ft.addToBackStack(null);
				ft.commit();
				
			}
		});
	}
	
}
