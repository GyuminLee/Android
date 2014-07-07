package com.example.samplecustomview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	private final static int MAX = 300;
	private final static int DELTA = 30;
	float[] points;
	Path mPath;
	Bitmap mBitmap;
	Matrix mMatrix;
	Camera mCamera;
	float[] meshPoints;
	
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
		Bitmap bm = Bitmap.createScaledBitmap(mBitmap, 300, 200, false);
		mBitmap.recycle();
		mBitmap = bm;
		mMatrix = new Matrix();
		mCamera = new Camera();
		
		meshPoints = new float[]{ 100, 100, 150, 150, 200, 150, 250, 100 ,
				       100, 200, 150, 250, 200, 250, 250, 200 };
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
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
