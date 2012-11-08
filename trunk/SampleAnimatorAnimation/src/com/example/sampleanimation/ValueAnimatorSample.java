package com.example.sampleanimation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ValueAnimatorSample extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
        setContentView(R.layout.bouncing_balls);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(new ValueAnimatorView(this));
        
	}
	
	public class ValueAnimatorView extends View {

		public ValueAnimatorView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			ValueAnimator animator = ValueAnimator.ofInt(Color.BLACK,Color.WHITE);
			animator.setEvaluator(new ArgbEvaluator());
			animator.setDuration(3000);
			animator.setRepeatCount(ValueAnimator.INFINITE);
			animator.setRepeatMode(ValueAnimator.REVERSE);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					// TODO Auto-generated method stub
					int color = (Integer)animation.getAnimatedValue();
					setBackgroundColor(color);
				}
			});
			animator.start();
		}
		
	}

}
