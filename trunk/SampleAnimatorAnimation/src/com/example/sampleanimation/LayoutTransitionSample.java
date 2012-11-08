package com.example.sampleanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class LayoutTransitionSample extends Activity {

	LinearLayout container;
	int mCount = 1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
        setContentView(R.layout.bouncing_balls);
        container = (LinearLayout) findViewById(R.id.container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        		ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btn = new Button(this);
        btn.setText("add");
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		        		ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		        Button btn = new Button(LayoutTransitionSample.this);
		        btn.setText("" + mCount++);
		        btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						container.removeView(v);
					}
				});
		        container.addView(btn, Math.min(container.getChildCount(), 2), params);
			}
		});
        container.addView(btn,params);
        
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);
        Animator appearing = AnimatorInflater.loadAnimator(this, R.animator.apearing);
        appearing.setDuration(transition.getDuration(LayoutTransition.APPEARING));
        appearing.addListener(new AnimatorListenerAdapter() {
        	@Override
        	public void onAnimationEnd(Animator animation) {
        		View view = (View)((ObjectAnimator)animation).getTarget();
        		view.setRotationY(0f);
        	}
		});
        transition.setAnimator(LayoutTransition.APPEARING, appearing);
        
        Animator disappering = AnimatorInflater.loadAnimator(this, R.animator.disapearing);
        disappering.setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
        disappering.addListener(new AnimatorListenerAdapter() {
        	@Override
        	public void onAnimationEnd(Animator animation) {
        		View view = (View)((ObjectAnimator)animation).getTarget();
        		view.setRotationX(0);
        	}
        });
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappering);
	}

}
