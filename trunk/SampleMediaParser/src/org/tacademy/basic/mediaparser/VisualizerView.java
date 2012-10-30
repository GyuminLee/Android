package org.tacademy.basic.mediaparser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class VisualizerView extends View {

	MediaParser mParser;
	public final static int GRAPH_GAP_TOP = 20;
	int mCurrentPosition;
	Paint mCenterLinePaint = new Paint();
	Paint mFrameLinePaint = new Paint();
	Paint mCurrentLinePaint = new Paint();
	Paint mTextPaint = new Paint();
	
	public VisualizerView(Context context) {
		this(context,null);
	}
	
	public VisualizerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mCurrentPosition = 0;
		mCenterLinePaint.setColor(Color.BLACK);
		mFrameLinePaint.setColor(Color.BLUE);
		mCurrentLinePaint.setColor(Color.RED);
		mTextPaint.setColor(Color.BLACK);
	}
	
	public void setMediaParser(MediaParser parser) {
		mParser = parser;
	}
	
	public void setCurrentPosition(int position) {
		if (mParser != null) {
			mCurrentPosition = position;
			invalidate();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = this.getMeasuredWidth();
		int height = this.getMeasuredHeight();
		int centerX = width / 2;
		int centerY = height / 2;
		canvas.drawColor(Color.WHITE);
		canvas.drawLine(0, centerY, width, centerY, mCenterLinePaint);
		if (mParser != null && mParser.isReady()) {
			int currentFrameNumber = mParser.getFrameFromMiliSecond(mCurrentPosition);
			int frameCount = mParser.getFrameCount();
			int drawStartFrameNumber = 0;
			int drawCurrentLineX = centerX;
			if (frameCount < width) {
				drawStartFrameNumber = 0;
				drawCurrentLineX = currentFrameNumber;
			} else if (currentFrameNumber < centerX) {
				drawStartFrameNumber = 0;
				drawCurrentLineX = currentFrameNumber;
			} else if (currentFrameNumber > (frameCount - centerX)) {
				drawStartFrameNumber = frameCount - width;
				drawCurrentLineX = (width - (frameCount - currentFrameNumber));
			} else {
				drawStartFrameNumber = currentFrameNumber - centerX;
				drawCurrentLineX = centerX;
			}
			
			int[] frameValues = mParser.getFrameValues();
			int maxFrameValue = 0;
			
			for (int idx = 0; idx < frameCount; idx++) {
				if (maxFrameValue < frameValues[idx]) {
					maxFrameValue = frameValues[idx];
				}
			}
			
			float screenFactor = (centerY - GRAPH_GAP_TOP) / maxFrameValue;
			for (int i = 0,frameNumber = drawStartFrameNumber; 
				i < width && frameNumber < frameCount ; 
				i++,frameNumber++) {
				
				float lineHeight = frameValues[frameNumber] * screenFactor;
				
				canvas.drawLine(i, centerY+lineHeight, i, centerY - lineHeight, mFrameLinePaint);
			}
			
			canvas.drawLine(drawCurrentLineX, 0, drawCurrentLineX, height, mCurrentLinePaint);
			
		}
		
	}
	
	
}
