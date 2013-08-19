package com.example.sampleprocessbitmap3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class MyRectView extends View {

	Rect[] rects;
	Paint mPaint = new Paint();
	Bitmap mBitmap;
	
	public MyRectView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyRectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyRectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setBitmap(Bitmap bitmap) {
		Bitmap bm565 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bm565);
		canvas.drawBitmap(bitmap, 0, 0, null);

		FaceDetector detector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 10);
		Face[] faces = new Face[10];
		int size = detector.findFaces(bm565, faces);
		if (size > 0) {
			rects = new Rect[size];
		}
		for (int i = 0; i < size; i++) {
			PointF point = new PointF();
			faces[i].getMidPoint(point);
			Rect rect = new Rect();
			
			float distance = faces[i].eyesDistance();
			
			rect.left = (int)(point.x - distance * 1);
			rect.right = (int)(point.x + distance * 1);
			rect.top = (int)(point.y - distance * 0.5);
			rect.bottom = (int)(point.y + distance * 1.5);
			rects[i] = rect;
		}
		bm565.recycle();
		mBitmap = bitmap;
		requestLayout();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mBitmap == null) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		}
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {
			canvas.drawBitmap(mBitmap, 0, 0, null);
			mPaint.setColor(0x80FF0000);
			for (Rect rect : rects) {
				canvas.drawRect(rect, mPaint);
			}
		}
	}

}
