package com.example.sampleanimation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

public class BackgroundThreadSample extends Activity {

	Handler mBGHandler;
	
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
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					Looper.prepare();
					mBGHandler = new Handler();
					ValueAnimator animator = ValueAnimator.ofInt(Color.BLACK,Color.WHITE);
					animator.setEvaluator(new ArgbEvaluator());
					animator.setDuration(3000);
					animator.setRepeatCount(ValueAnimator.INFINITE);
					animator.setRepeatMode(ValueAnimator.REVERSE);
					animator.addUpdateListener(new AnimatorUpdateListener() {
						
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							// TODO Auto-generated method stub
							final int color = (Integer)animation.getAnimatedValue();
							post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									setBackgroundColor(color);
								}
								
							});
						}
					});
					animator.start();
					Looper.loop();
				}
				
			}).start();
		}
		
	}
	
	@Override
	protected void onDestroy() {
		if (mBGHandler != null) {
			mBGHandler.getLooper().quit();
		}
		super.onDestroy();
	}
	

}
