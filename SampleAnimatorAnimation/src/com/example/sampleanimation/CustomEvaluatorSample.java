package com.example.sampleanimation;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CustomEvaluatorSample extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
        setContentView(R.layout.bouncing_balls);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(new CustomEvaluatorView(this));
	}
	
	public class CustomEvaluatorView extends View {

		Ball mBall;
		
		public CustomEvaluatorView(Context context) {
			super(context);
			Ball startBall = new Ball(10,10);
			Ball endBall = new Ball(100,100);
			ObjectAnimator animator = ObjectAnimator.ofObject(this,"ball",new BallEvaluator(),startBall,endBall);
			animator.setDuration(3000);
			animator.setRepeatCount(ValueAnimator.INFINITE);
			animator.setRepeatMode(ValueAnimator.REVERSE);
			animator.start();
		}

		public void setBall(Ball ball) {
			mBall = ball;
			invalidate();
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
			
			public Ball(int x, int y) {
				this.x = x;
				this.y = y;
				mPaint.setColor(Color.RED);
				mPaint.setStrokeWidth(2.0f);
			}
			
			public void setX(int x) {
				this.x = x;
			}
			
			public void setY(int y) {
				this.y = y;
			}
			
			public int getX() {
				return x;
			}
			
			public int getY() {
				return y;
			}
			
			public void draw(Canvas canvas) {
				canvas.drawCircle(x, y, RADIUS, mPaint);
			}
		}
		
		public class BallEvaluator implements TypeEvaluator<Ball> {
			Ball animationBall = new Ball(0,0);
			IntEvaluator iEval = new IntEvaluator();
			
			@Override
			public Ball evaluate(float fraction, Ball startValue, Ball endValue) {
				int x, y;
				x = iEval.evaluate(fraction, startValue.getX(), endValue.getX());
				y = iEval.evaluate(fraction, startValue.getY(), endValue.getY());
				animationBall.setX(x);
				animationBall.setY(y);
				//return new Ball(x,y);
				return animationBall;
			}
		}
	}

}
