package com.example.sampleanimation;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CustomObjectAnimationSample extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.bouncing_balls);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(new CustomObejctAnimationView(this));
	}
	
	public class CustomObejctAnimationView extends View {

		Ball mBall;
		
		public CustomObejctAnimationView(Context context) {
			super(context);
			mBall = new Ball();
			mBall.startAnimation();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			canvas.drawColor(Color.WHITE);
			if (mBall != null) {
				mBall.draw(canvas);
			}
		}
		
		public class Ball implements ValueAnimator.AnimatorUpdateListener {
			int x;
			int y;
			public final static int RADIUS = 10;
			Paint mPaint = new Paint();
			
			public Ball() {
				x = 10;
				y = 10;
				mPaint.setColor(Color.RED);
				mPaint.setStrokeWidth(2.0f);				
			}
			
			public void startAnimation() {
				ValueAnimator animator = ValueAnimator.ofInt(10, 100, 50);
				animator.setEvaluator(new IntEvaluator());
				animator.setDuration(3000);
				animator.setRepeatCount(ValueAnimator.INFINITE);
				animator.setRepeatMode(ValueAnimator.REVERSE);
				animator.addUpdateListener(this);
				animator.start();
			}

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				x = (Integer)animation.getAnimatedValue();
				invalidate();
			}
			
			public void draw(Canvas canvas) {
				canvas.drawCircle(x, y, RADIUS, mPaint);
			}
		}	
	}
}
