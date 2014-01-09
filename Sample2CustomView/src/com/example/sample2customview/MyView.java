package com.example.sample2customview;

import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	Path mPath;
	Path mCursorPath;
	float drawWidth;
	float drawHeight;
	float[] points = { 10, 10, 60, 110, 60, 110, 110, 10, 110, 10, 160, 110,
			160, 110, 210, 10 };
	GestureDetector detector;
	ScaleGestureDetector scaleDetector;
	
	public MyView(Context context) {
		super(context);
		init();
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	Bitmap mBitmap;
	Matrix mMatrix;
	float mScaleFactor = 1.0f;
	
	float[] mashPoint = { 0, 200, 50, 250, 100, 200, 150, 250, 200, 200, 0,
			300, 50, 500, 100, 300, 150, 350, 200, 300 };

	private void init() {
		mPaint = new Paint();
		Resources res = getContext().getResources();
		drawWidth = res.getDimension(R.dimen.my_draw_width);
		drawHeight = res.getDimension(R.dimen.my_draw_height);
		InputStream is = res.openRawResource(R.drawable.gallery_photo_1);
		mBitmap = BitmapFactory.decodeStream(is);
		mMatrix = new Matrix();
		mMatrix.reset();
		// Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmap, 100, 100,
		// false);
		// mBitmap.recycle();
		// mBitmap = scaledBitmap;

		mPath = new Path();
		// mPath.moveTo(0, 0);
		// mPath.lineTo(50, 50);
		// mPath.lineTo(100, 0);
		// mPath.lineTo(150, 50);
		// mPath.lineTo(200, 0);
		mPath.addCircle(200, 200, 100, Path.Direction.CCW);
		// mPath.moveTo(50, 50);
		// mPath.cubicTo(100, 75, 75, 100, 60, 60);
		mCursorPath = new Path();
		mCursorPath.moveTo(0, 0);
		mCursorPath.lineTo(-5, 5);
		mCursorPath.lineTo(0, 5);
		mCursorPath.lineTo(5, 0);
		mCursorPath.lineTo(0, -5);
		mCursorPath.lineTo(-5, -5);

		detector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				invalidate();
				return true;
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				sat = 0.1f;
				invalidate();
				return true;
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return super.onScroll(e1, e2, distanceX, distanceY);
			}
		});
		scaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener(){
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				float f = detector.getScaleFactor();
				sat *= f;
				if (sat > 1) sat = 1;
				else if (sat < 0) sat = 0.1f;
				invalidate();
				return true;
			}
		});
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width, height;
		width = getPaddingLeft() + getPaddingRight();
		height = getPaddingTop() + getPaddingBottom();
		if (mBitmap != null) {
			width += mBitmap.getWidth();
			height += mBitmap.getHeight();
		}
		// int widthmode = MeasureSpec.getMode(widthMeasureSpec);
		// int widthsize = MeasureSpec.getSize(widthMeasureSpec);
		// switch(widthmode) {
		// case MeasureSpec.AT_MOST:
		// width = (width < widthsize)?width:widthsize;
		// break;
		// case MeasureSpec.EXACTLY:
		// width = widthsize;
		// break;
		// case MeasureSpec.UNSPECIFIED:
		// break;
		// }
		//
		// int heightmode = MeasureSpec.getMode(heightMeasureSpec);
		// int heightsize = MeasureSpec.getSize(heightMeasureSpec);
		// switch(heightmode) {
		// case MeasureSpec.AT_MOST:
		// height = (height < heightsize)?height:heightsize;
		// break;
		// case MeasureSpec.EXACTLY:
		// height = heightsize;
		// break;
		// case MeasureSpec.UNSPECIFIED:
		// break;
		// }

		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		
	}
	
	float ex, ey;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean used = detector.onTouchEvent(event);
		used = scaleDetector.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			return true;
		}
		return used;
	}
	
	public void setBitmap(Bitmap bm) {
		mBitmap = bm;
		requestLayout();
	}

	float sat = 1.0f;
	Runnable update = new Runnable() {
		
		@Override
		public void run() {
			invalidate();
		}
	};
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		// int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.RED};
		// float[] positions = {0, 0.3f, 1};
		// LinearGradient shader = new LinearGradient(100, 100, 300, 300,
		// 0xFFFF0000, 0x00FF0000, Shader.TileMode.REPEAT);
		// RadialGradient shader = new RadialGradient(200, 200, 100, Color.RED,
		// Color.BLUE, TileMode.CLAMP);

		// SweepGradient shader = new SweepGradient(200, 200, colors, null);
		// mPaint.setShader(shader);

		// canvas.drawCircle(200, 200, 100, mPaint);
		// canvas.drawRect(100, 100, 300, 300, mPaint);
		// canvas.drawBitmap(mBitmap, 0, 0,mPaint);

		ColorMatrix cm = new ColorMatrix();
		sat = sat - 0.1f;
		if (sat < 0) sat = 1.0f;
		cm.setSaturation(sat);
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
		mPaint.setColorFilter(cf);
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//		postDelayed(update, 100);
		//
		// canvas.drawBitmapMesh(mBitmap, 4, 1, mashPoint, 0, null, 0, mPaint);

		// mMatrix.setScale(1, -1, 0, mBitmap.getHeight());
		// mMatrix.postSkew(0.5f, 0, 0, mBitmap.getHeight());
		// mMatrix.setRotate(45, 0, mBitmap.getHeight());
		// canvas.drawBitmap(mBitmap, mMatrix, mPaint);
		// canvas.drawBitmap(mBitmap, 100, 100, mPaint);
		// mPaint.setStyle(Paint.Style.STROKE);
		// mPaint.setStrokeWidth(10);
		// CornerPathEffect pe = new CornerPathEffect(10);
		// mPaint.setPathEffect(pe);
		// float[] intervals = {10, 5, 20, 5};
		// DashPathEffect dpe = new DashPathEffect(intervals, 15);
		// PathDashPathEffect pdpe = new PathDashPathEffect(mCursorPath, 10, -5,
		// PathDashPathEffect.Style.MORPH);
		// mPaint.setPathEffect(pdpe);
		// canvas.drawLine(100, 100, 300, 300, mPaint);
		// canvas.drawPath(mPath, mPaint);
		// canvas.drawCircle(200, 200, 100, mPaint);
		// mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		// mPaint.setStyle(Paint.Style.STROKE);
		// mPaint.setStrokeWidth(10);
		// mPaint.setStrokeJoin(Paint.Join.ROUND);
		// mPaint.setStrokeMiter(10);
		// mPaint.setStrokeCap(Paint.Cap.ROUND);
		// canvas.drawPoints(points, mPaint);
		// canvas.save();
		// canvas.translate(100, 100);
		// canvas.rotate(45, 100, 100);
		// canvas.drawLines(points, mPaint);
		// canvas.restore();
		// mPaint.setAntiAlias(true);
		// canvas.drawCircle(200, 200, 100, mPaint);
		// RectF oval = new RectF(100,100,300,200);
		// canvas.drawArc(oval, 45, 45, false, mPaint);
		// canvas.drawRoundRect(oval, 20, 30, mPaint);
		// canvas.drawPath(mPath, mPaint);
		// mPaint.setTextSize(40);
		// mPaint.setTextSkewX(0.5f);
		// canvas.drawText("Hello Android g", 0, 100, mPaint);
		// canvas.drawTextOnPath("Hello Android", mPath, 0, 0, mPaint);
	}

}
