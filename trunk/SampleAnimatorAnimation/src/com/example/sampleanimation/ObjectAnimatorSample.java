package com.example.sampleanimation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ObjectAnimatorSample extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.bouncing_balls);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(new ObjectAnimatorView(this));	
	}

	public class ObjectAnimatorView extends View {

		public ObjectAnimatorView(Context context) {
			super(context);
			ObjectAnimator animator = ObjectAnimator.ofInt(this,
					"backgroundColor", Color.BLACK,Color.WHITE);
			animator.setEvaluator(new ArgbEvaluator());
			animator.setDuration(3000);
			animator.setRepeatCount(ValueAnimator.INFINITE);
			animator.setRepeatMode(ValueAnimator.REVERSE);
			animator.start();
		}
	}
	
}
