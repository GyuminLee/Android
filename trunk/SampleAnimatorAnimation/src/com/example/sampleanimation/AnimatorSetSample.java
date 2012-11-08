package com.example.sampleanimation;

import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AnimatorSetSample extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.bouncing_balls);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(new AnimatorSetView(this));
	}
	
	public class AnimatorSetView extends View {

		Ball mBall;
		
		public AnimatorSetView(Context context) {
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
		
		public class Ball {
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

			public void setX(int x) {
				this.x = x;
				invalidate();
			}
			
			public void setY(int y) {
				this.y = y;
				invalidate();
			}
			
			public int getX() {
				return x;
			}
			
			public int getY() {
				return y;
			}
			public void startAnimation() {
				AnimatorSet set = new AnimatorSet();
				
				ObjectAnimator animatorA = ObjectAnimator.ofInt(this,"x",10, 100);
				ObjectAnimator animatorB = ObjectAnimator.ofInt(this,"y",10, 100);
				
				set.play(animatorA).before(animatorB);
				
				set.setDuration(3000);
				set.start();
			}

			public void draw(Canvas canvas) {
				canvas.drawCircle(x, y, RADIUS, mPaint);
			}
		}	
	}

}
