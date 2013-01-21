package com.example.testcamerasample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.View;

public class FaceView extends View {

	
	Camera.Face[] faces;
	
	Paint mPaint = new Paint();
	
	public FaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void setFaces(Camera.Face[] faces) {
		this.faces = faces;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawColor(0x00000000);
		if (faces != null) {
			for (int i = 0; i < faces.length; i++) {
				Camera.Face face = faces[i];
				canvas.drawRect(face.rect, mPaint);
			}
		}
	}
	
}
