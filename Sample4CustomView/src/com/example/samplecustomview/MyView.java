package com.example.samplecustomview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	private final static int MAX = 300;
	private final static int DELTA = 30;
	float[] points;
	Path mPath;
	Path mTextPath;
	Bitmap mBitmap;
	Matrix mMatrix;
	Camera mCamera;
	float[] meshPoints;
	PathEffect mPathEffect;
	Path mArrow;
	Shader mShader;
	ColorFilter mColorFilter;
	
	
	public MyView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		mPaint = new Paint();
		int counts = MAX / DELTA + 1;
		points = new float[2 * 2 * counts];
		for (int i = 0; i < counts; i++) {
			points[i * 4] = 100;
			points[i * 4 + 1] = 100 + i * DELTA;
			points[i * 4 + 2] = 100 + MAX - i * DELTA;
			points[i * 4 + 3] = 100;
		}
		
		mPath = new Path();
		mPath.moveTo(200, 200);
		mPath.lineTo(300, 300);
		mPath.lineTo(200, 300);
		
//		mBitmap = BitmapFactory.decodeResource(getResources(), R.raw.gallery_photo_1);
		InputStream is = getResources().openRawResource(R.raw.gallery_photo_1);
		mBitmap = BitmapFactory.decodeStream(is);
		Bitmap bm = Bitmap.createScaledBitmap(mBitmap, 300, 300, false);
		mBitmap.recycle();
		mBitmap = bm;
		mMatrix = new Matrix();
		mCamera = new Camera();
		
		meshPoints = new float[]{ 100, 100, 150, 150, 200, 150, 250, 100 ,
				       100, 200, 150, 250, 200, 250, 250, 200 };
		mTextPath = new Path();
		mTextPath.addCircle(300, 300, 200, Path.Direction.CW);
		
		float[] intervals = { 20, 10, 10, 5 };
		mPathEffect = new DashPathEffect(intervals, 10);
		
		mArrow = new Path();
		mArrow.moveTo(0, 0);
		mArrow.lineTo(-5, 5);
		mArrow.lineTo(0, 5);
		mArrow.lineTo(5, 0);
		mArrow.lineTo(0, -5);
		mArrow.lineTo(-5, -5);
		mPathEffect = new PathDashPathEffect(mArrow, 20, 0, PathDashPathEffect.Style.ROTATE);
	
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		
	}
	
	private void drawColorFilter(Canvas canvas) {
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrix mb = new ColorMatrix();
		mb.setScale(1, 0.95f, 0.82f, 1);
		cm.setConcat(mb, cm);
		mColorFilter = new ColorMatrixColorFilter(cm);
		
		mPaint.setColorFilter(mColorFilter);
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);		
	}
	private void drawShader(Canvas canvas) {
		int[] colors = {Color.RED, Color.BLUE, Color.RED};
		float[] position = {0, 0.3f, 1};
//		mShader = new LinearGradient(200, 200, 300, 300, colors, position, Shader.TileMode.REPEAT);
//		mShader = new RadialGradient(300, 300, 200, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
//		mShader = new SweepGradient(300, 300, colors, null);
		mShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

		mPaint.setShader(mShader);
//		canvas.drawRect(100, 100, 400, 400, mPaint);
		canvas.drawCircle(150, 150, 150, mPaint);
		
	}
	
	private void drawPathEffect(Canvas canvas) {
		mPaint.setColor(Color.DKGRAY);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(10);
		mPaint.setPathEffect(mPathEffect);
//		canvas.drawRect(100, 100, 400, 400, mPaint);
		canvas.drawCircle(300, 300, 200, mPaint);
	}
	
	private void drawTextOnPath(Canvas canvas) {
		String message = "Hello Android!";
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(40);
		canvas.drawTextOnPath(message, mTextPath, 0, (int)(Math.PI * 200/ 2), mPaint);
	}
	
	private void drawBitmapMesh(Canvas canvas) {
		canvas.drawBitmapMesh(mBitmap, 3, 1, meshPoints, 0, null, 0, mPaint);		
	}
	
	private void draw3DBitmap(Canvas canvas) {
		mCamera.save();
		mCamera.rotateY(30);
		mCamera.getMatrix(mMatrix);
		mCamera.restore();
		
		mMatrix.preTranslate(-mBitmap.getWidth()/2, -mBitmap.getHeight()/2);
		mMatrix.postTranslate(mBitmap.getWidth()/2, mBitmap.getHeight()/2);
		mMatrix.postTranslate(100, 100);
		canvas.drawBitmap(mBitmap, mMatrix,mPaint);
	}

	private void drawBitmap(Canvas canvas) {
		mMatrix.setScale(2, 2, 0, 0);
		mMatrix.postTranslate(100, 100);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
		
		mMatrix.setScale(-2, 2, 0, 0);
		mMatrix.postTranslate(400, 400);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);

		mMatrix.setRotate(45,mBitmap.getWidth()/2, mBitmap.getHeight()/2);
		mMatrix.postTranslate(100, 600);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
		
		mMatrix.setSkew(-1, 0);
		mMatrix.postTranslate(100, 500);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
	}
	private void drawShape(Canvas canvas) {
		mPaint.setColor(Color.YELLOW);
		canvas.drawCircle(250, 250, 250, mPaint);
		
		mPaint.setColor(Color.GRAY);
		RectF roundRect = new RectF();
		roundRect.left = 50;
		roundRect.right = 450;
		roundRect.top = 50;
		roundRect.bottom = 450;
		canvas.drawRoundRect(roundRect, 50, 50, mPaint);
		
		
		mPaint.setColor(Color.DKGRAY);
		Rect rect = new Rect();
		rect.left = 100;
		rect.right = 400;
		rect.top = 100;
		rect.bottom = 400;
		canvas.drawRect(rect, mPaint);
		mPaint.setColor(Color.RED);
		canvas.drawLines(points, mPaint);
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(5);
		canvas.drawPoints(points, mPaint);
		
		mPaint.setColor(Color.CYAN);
		RectF oval = new RectF(200,150,300,350);
		canvas.drawOval(oval, mPaint);
		
		mPaint.setColor(Color.BLACK);
		canvas.drawArc(oval, 30, 90, true, mPaint);
		
		mPaint.setColor(Color.MAGENTA);
		canvas.drawPath(mPath, mPaint);
		
		String message = "Hello, World!";
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(40);
		mPaint.setTextSkewX(-1);
		
		canvas.drawText(message, 100, 40, mPaint);
	}
}
