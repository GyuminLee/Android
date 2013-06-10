package com.example.testgraphicssample2;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class MyView extends View {

	Paint mPaint;
	String mHello = "Hello Android!";
	Path mPath;
	Bitmap mBitmap;
	Matrix mMatrix;
	float[] vertics = { 10, 210, 60, 260, 110, 210, 160, 260, 210, 210, 
						10, 310, 60, 360, 110, 310, 160, 360, 210, 310 };
	
	
	
	public MyView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setTextSize(40);
		mPaint.setStyle(Paint.Style.STROKE);

		mPath = new Path();
		mPath.addCircle(210, 160, 100, Path.Direction.CW);
		
		InputStream is = context.getResources().openRawResource(R.raw.gallery_photo_1);
		mBitmap = BitmapFactory.decodeStream(is);
		mMatrix = new Matrix();
		mMatrix.setScale(1, -1, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
		mMatrix.postSkew(1, 0);
		mMatrix.postTranslate(100, 100);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
//		canvas.drawBitmap(mBitmap, 10, 310, mPaint);
		canvas.save();
		canvas.rotate(45, 160, 260);
		canvas.drawBitmapMesh(mBitmap, 4, 1, vertics, 0, null, 0, mPaint);
		canvas.restore();
		canvas.drawText(mHello, 10, 110, mPaint);
//		canvas.drawRect(10, 10, 210, 210, mPaint);
//		canvas.drawText(mHello, 10, 250, mPaint);
//		canvas.drawPath(mPath, mPaint);
//		canvas.drawTextOnPath(mHello, mPath, 0, 0, mPaint);
	}

}
