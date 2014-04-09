package com.example.sample3customview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class MyView extends View {

	Paint mPaint = new Paint();
	Bitmap mBitmap;
	Matrix mMatrix;
	GestureDetector mDetector;
	ScaleGestureDetector mScaleDetetor;
	int resId;

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

	float[] mPoints;
	int count = 30;
	float maxWidth = 300;
	float margin = 20;
	Path mPath = new Path();
	Path mCursorPath = new Path();
	String message = "Hello Android!";
	float[][] mMashPoints = {
			{ 100, 100, 150, 100, 200, 100, 250, 100, 100, 300, 150, 300, 200,
					300, 250, 300 },
			{ 100, 100, 150, 110, 200, 110, 250, 100, 100, 300, 150, 310, 200,
					310, 250, 300 },
			{ 100, 100, 150, 135, 200, 135, 250, 100, 100, 300, 150, 335, 200,
					335, 250, 300 },
			{ 100, 100, 150, 150, 200, 150, 250, 100, 100, 300, 150, 350, 200,
					350, 250, 300 } };

	int pointIndex = 0;

	float mScaleFactor = 1.0f;

	private void init() {
		mPoints = new float[(count + 1) * 2 * 2];
		float delta = maxWidth / count;
		int index = 0;
		for (float i = 0; i <= 300; i += delta) {
			mPoints[index++] = margin;
			mPoints[index++] = margin + i;
			mPoints[index++] = margin + 300 - i;
			mPoints[index++] = margin;
		}

		// arrow
		// mPath.moveTo(100, 100);
		// mPath.lineTo(50, 50);
		// mPath.lineTo(150, 50);
		// mPath.lineTo(200, 100);
		// mPath.lineTo(150, 150);
		// mPath.lineTo(50, 150);

		mPath.moveTo(100, 100);
		mPath.lineTo(150, 150);
		mPath.lineTo(200, 50);
		mPath.lineTo(250, 100);
		mPath.lineTo(300, 200);

		mCursorPath.moveTo(0, 0);
		mCursorPath.lineTo(-5, -5);
		mCursorPath.lineTo(0, -5);
		mCursorPath.lineTo(5, 0);
		mCursorPath.lineTo(0, 5);
		mCursorPath.lineTo(-5, 5);
		// mPath.addCircle(100, 100, 100, Path.Direction.CW);
		// RectF rect = new RectF(100,100, 300,300);
		// float[] radii = {10, 5, 20, 10, 30, 15, 40, 20};
		// mPath.addRoundRect(rect, radii, Path.Direction.CW);

		// mPath.moveTo(0, 0);
		// mPath.cubicTo(100, 100, 200, 100, 100, 0);
		// mPath.quadTo(100, 100, 50, 200);

		InputStream is = getResources().openRawResource(
				R.drawable.gallery_photo_1);
		BitmapFactory.Options options = new BitmapFactory.Options();
		mBitmap = BitmapFactory.decodeStream(is);
		int width = mBitmap.getWidth();
		Bitmap bm = Bitmap.createScaledBitmap(mBitmap, width, width, false);
		mBitmap.recycle();
		mBitmap = bm;
		mMatrix = new Matrix();
		// postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// invalidate();
		// postDelayed(this, 200);
		// }
		// }, 200);

		mDetector = new GestureDetector(getContext(),
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						float distance = Math.abs(e1.getX() - e2.getX());
						Log.i("MyView", "fling..." + distance);
						return true;
					}

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						return super.onSingleTapUp(e);
					}

					@Override
					public boolean onDown(MotionEvent e) {
						return true;
					}
				});
		mScaleDetetor = new ScaleGestureDetector(getContext(),
				new ScaleGestureDetector.SimpleOnScaleGestureListener() {
					@Override
					public boolean onScale(ScaleGestureDetector detector) {
						float factor = detector.getScaleFactor();
						mScaleFactor *= factor;
						adjustScale();
						invalidate();
						return true;
					}
				});
	}

	private void adjustScale() {
		if (mScaleFactor < 0.5f) {
			mScaleFactor = 0.5f;
		} else if (mScaleFactor > 4) {
			mScaleFactor = 4;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		// drawLine
		// canvas.drawLines(mPoints, mPaint);
		// mPaint.setColor(Color.RED);
		// mPaint.setStrokeWidth(3);
		// canvas.drawPoints(mPoints, mPaint);
		// canvas.drawLine(0, 0, 200, 200, mPaint);

		// drawRect
		// mPaint.setStrokeWidth(50);
		// mPaint.setStyle(Paint.Style.FILL);
		// RectF rect = new RectF(100, 100, 300, 300);
		// canvas.drawRect(rect, mPaint);
		// canvas.drawRoundRect(rect, 100, 50, mPaint);

		// drawCircle
		// canvas.drawCircle(200, 200, 100, mPaint);

		// drawOval
		// RectF rect = new RectF(100,100, 300, 200);
		// canvas.drawOval(rect, mPaint);

		// drawArc
		// RectF rect = new RectF(100,100, 300, 300);
		// canvas.drawArc(rect, 45, 90, false, mPaint);

		// drawPath
		// mPaint.setStyle(Paint.Style.STROKE);
		// mPaint.setStrokeWidth(20);
		// mPaint.setStrokeCap(Paint.Cap.ROUND);
		// mPaint.setStrokeJoin(Paint.Join.ROUND);
		// canvas.drawPath(mPath, mPaint);

		// drawText
		// mPaint.setTextSize(20);
		// mPaint.setTextSkewX(1);
		// canvas.drawText(message, 0, 100, mPaint);
		// canvas.drawTextOnPath(message, mPath, 0, 0, mPaint);

		// drawBitmap
		// int width = mBitmap.getWidth();
		// int height = mBitmap.getHeight();
		//
		// mMatrix.reset();
		// mMatrix.setTranslate(100, 100);
		// mMatrix.postScale(1, -1, 100 + width/2, 100 + height / 2);
		// mMatrix.postSkew(-1, 0, 100 + width/2, 100 + height / 2);
		// mMatrix.postRotate(45, 100 + width/2, 100 + height / 2);
		//
		// canvas.drawBitmap(mBitmap, mMatrix, mPaint);

		// drawBitmapMesh
		// canvas.drawBitmapMesh(mBitmap, 3, 1, mMashPoints[pointIndex], 0,
		// null, 0, mPaint);
		// pointIndex = (pointIndex + 1) % mMashPoints.length;

		// PathEffect

		// PathEffect effect = new CornerPathEffect(10);
		// float[] intervals ={ 20 , 10 , 40, 10 };
		// PathEffect effect = new DashPathEffect(intervals, 10);
		// PathEffect effect = new PathDashPathEffect(mCursorPath, 10, 0,
		// PathDashPathEffect.Style.ROTATE);
		// mPaint.setStyle(Paint.Style.STROKE);
		// mPaint.setStrokeWidth(10);
		// mPaint.setPathEffect(effect);
		// canvas.drawPath(mPath, mPaint);
		// canvas.drawCircle(200, 200, 100, mPaint);

		// Shader shader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR,
		// Shader.TileMode.MIRROR);
		// Shader shader = new LinearGradient(100, 100, 200, 200, Color.RED,
		// Color.GREEN, Shader.TileMode.MIRROR);
		// int[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
		// float[] position = {0, 0.3f, 1};
		// Shader shader = new LinearGradient(100, 100, 200, 100, colors,
		// position, Shader.TileMode.MIRROR);
		// Shader shader = new RadialGradient(200, 200, 100, Color.RED,
		// Color.GREEN, Shader.TileMode.MIRROR);
		// int[] colors = {Color.RED, Color.GREEN, Color.RED};
		// Shader shader = new SweepGradient(200, 200, colors, null);
		// mPaint.setShader(shader);
		// RectF rect = new RectF(0,0,mBitmap.getWidth(), mBitmap.getHeight());
		// canvas.drawOval(rect, mPaint);
		// canvas.drawRect(0, 0, 200, 200, mPaint);

		// canvas.drawCircle(200, 200, 200, mPaint);

		// ColorFilter
		// ColorMatrix matrix = new ColorMatrix();
		// matrix.setSaturation(0);
		// ColorFilter filter = new ColorMatrixColorFilter(matrix);
		// mPaint.setColorFilter(filter);
		// canvas.drawBitmap(mBitmap, 0, 0, mPaint);

		// Xfermode
		// canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		//
		// Xfermode mode = new PorterDuffXfermode(PorterDuff.Mode.ADD);
		// mPaint.setColor(Color.RED);
		// mPaint.setXfermode(mode);
		// canvas.drawCircle(mBitmap.getWidth()/2, mBitmap.getHeight()/2,
		// mBitmap.getWidth()/2, mPaint);
//		mPaint.setStyle(Paint.Style.FILL);
//		mPaint.setShadowLayer(10, 10, 10, Color.DKGRAY);
		// mMatrix.reset();
		// mMatrix.setTranslate(100, 100);
		// mMatrix.postScale(mScaleFactor, mScaleFactor, 100, 100);
//		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		// canvas.drawCircle(200, 200, 100, mPaint);
		
		canvas.drawBitmap(offScreenBitmap, 0, 0, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// boolean bUsed = mDetector.onTouchEvent(event);
		// if (!bUsed) {
		// bUsed = mScaleDetetor.onTouchEvent(event);
		// }
		// if (!bUsed) {
		// bUsed = super.onTouchEvent(event);
		// }
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getX();
			oldY = event.getY();
			bPressed = true;
			return true;
		case MotionEvent.ACTION_MOVE:
			if (bPressed) {
				float x = event.getX();
				float y = event.getY();
				offScreenCanvas.drawLine(oldX, oldY, x, y, mPaint);
				oldX = x;
				oldY = y;
				invalidate();
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			bPressed = false;
			return true;
		}
		return true;
	}

	boolean bPressed = false;
	float oldX, oldY;

	public void setImageResource(int resId) {
		this.resId = resId;
		mBitmap = BitmapFactory.decodeResource(getResources(), resId);
		requestLayout();
	}

	Canvas offScreenCanvas;
	Bitmap offScreenBitmap;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getPaddingLeft() + getPaddingRight();
		int height = getPaddingTop() + getPaddingBottom();
		if (mBitmap != null) {
			width += mBitmap.getWidth();
			height += mBitmap.getHeight();
		}
		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));

		int viewWidth = getMeasuredWidth();
		int viewHeight = getMeasuredHeight();
		if (offScreenBitmap == null) {
			offScreenBitmap = Bitmap.createBitmap(viewWidth, viewHeight,
					Bitmap.Config.ARGB_8888);
			offScreenCanvas = new Canvas(offScreenBitmap);
		} else if (offScreenBitmap.getWidth() != viewWidth
				|| offScreenBitmap.getHeight() != viewHeight) {
			Bitmap bm = Bitmap.createBitmap(viewWidth, viewHeight,
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bm);
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(offScreenBitmap, 0, 0, null);
			offScreenBitmap.recycle();
			offScreenBitmap = bm;
			offScreenCanvas = canvas;
		}

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable parent = super.onSaveInstanceState();
		SavedState state = new SavedState(parent);
		state.resId = resId;
		return state;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof SavedState) {
			super.onRestoreInstanceState(state);
			return;
		}

		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		int resId = ss.resId;
		setImageResource(resId);
	}

	public static class SavedState extends View.BaseSavedState {

		int resId;

		public SavedState(Parcel source) {
			super(source);
			resId = source.readInt();
		}

		public SavedState(Parcelable parent) {
			super(parent);
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(resId);
		}

		public static Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<MyView.SavedState>() {

			@Override
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

		};
	}
}
