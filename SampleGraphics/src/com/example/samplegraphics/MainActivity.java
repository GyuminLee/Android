package com.example.samplegraphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	LinearLayout mContainer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (LinearLayout)findViewById(R.id.container);
        mContainer.addView(new DrawingView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public class DrawingView extends View {

    	Paint mPaint = new Paint();
    	Path mPath;
		public DrawingView(Context context) {
			super(context);
			mPath = new Path();
			mPath.moveTo(10.0f, 10.0f);
			mPath.lineTo(110.0f, 110.0f);
			mPath.lineTo(210.0f, 10.0f);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			float x = 10, y = 10, center = 100, radius = 50;
			RectF oval = new RectF(10, 10, 210, 110);
			mPaint.setColor(Color.RED);
			canvas.drawRect(oval, mPaint);
			mPaint.setColor(Color.BLACK);
			mPaint.setTextSize(20);
			canvas.drawPath(mPath, mPaint);
			canvas.drawTextOnPath("abcdefghijklmnopqrstuvwxyz", mPath,0.0f, 20.0f, mPaint);
			
		}
    	
		
    }
}
