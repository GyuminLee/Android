package com.magnitude.app.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class TextDrawable extends Drawable {

	Drawable mDrawable;
	String mText;
	Paint mTextPaint;
	Rect mTextRect;
	private static final int GAP = 10;
	
	public TextDrawable(Drawable drawable, String text) {
		mDrawable = drawable;
		mText = text;
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(22f);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setFakeBoldText(true);
		mTextPaint.setShadowLayer(6f, 0, 0, Color.BLACK);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setTextAlign(Paint.Align.LEFT);
		mTextRect = new Rect();
		char[] ch = mText.toCharArray();
		mTextPaint.getTextBounds(ch, 0, ch.length, mTextRect);
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		mDrawable.draw(canvas);
		Rect rect = mDrawable.getBounds();
//		rect.bottom += mTextRect.bottom;
		copyBounds(rect);
		int startX = rect.left + ((rect.width() - mTextRect.width()) / 2);
		canvas.drawText(mText, startX, rect.bottom + GAP, mTextPaint);
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		// TODO Auto-generated method stub
		super.onBoundsChange(bounds);
		mDrawable.setBounds(bounds);
	}
	
	@Override
	protected boolean onStateChange(int[] state) {
		// TODO Auto-generated method stub
		return mDrawable.setState(state);
	}

	@Override
	protected boolean onLevelChange(int level) {
		// TODO Auto-generated method stub
		return mDrawable.setLevel(level);
	}
	
	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return mDrawable.getOpacity();
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		mDrawable.setAlpha(alpha);
		mTextPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter filter) {
		// TODO Auto-generated method stub
		mDrawable.setColorFilter(filter);
		mTextPaint.setColorFilter(filter);
	}

}
