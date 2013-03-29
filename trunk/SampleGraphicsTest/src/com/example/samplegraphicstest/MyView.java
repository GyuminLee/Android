package com.example.samplegraphicstest;

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
	Path mPath;
	Bitmap mBitmap;
	
	
	String text = "hello android! hello android! hello android! hello android! hello android!";
	
	public MyView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPath = new Path();
		mPath.addCircle(200, 200, 100, Path.Direction.CW);
		
		InputStream is = context.getResources().openRawResource(R.raw.gallery_photo_1);
		mBitmap = BitmapFactory.decodeStream(is);

		Bitmap scaleBitmap = Bitmap.createScaledBitmap(mBitmap, 100, 100, false);
		mBitmap.recycle();

		mBitmap = scaleBitmap;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.BLUE);
		canvas.drawRect(10, 10, 200, 200, mPaint);
		
		mPaint.setColor(Color.RED);
		canvas.drawCircle(100, 100, 50, mPaint);
		
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(10);
		canvas.drawTextOnPath(text, mPath, 0, 0, mPaint);
		
		canvas.drawBitmap(mBitmap, 100, 100, mPaint);
		
		Matrix m = new Matrix();
		m.setScale(-1, 1, 50, 50);
		m.postTranslate(200, 200);
		m.postRotate(45);
		canvas.drawBitmap(mBitmap, m, mPaint);
		
	}
}
