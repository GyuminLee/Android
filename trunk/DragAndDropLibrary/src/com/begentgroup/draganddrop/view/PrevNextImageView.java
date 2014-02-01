package com.begentgroup.draganddrop.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.begentgroup.draganddrop.DragController;
import com.begentgroup.draganddrop.DragSource;
import com.begentgroup.draganddrop.DragView;
import com.begentgroup.draganddrop.DropTarget;

public class PrevNextImageView extends ImageView implements DropTarget {

	public final static int BUTTON_PERFORM_TIME = 1000;
	
	DragController mController;
	
	public PrevNextImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	OnClickListener mIconClickListener;
	public void setOnIconClickListener(OnClickListener listener) {
		mIconClickListener = listener;
	}
	
	public PrevNextImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PrevNextImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setDragController(DragController controller) {
		mController = controller;
		mController.addDropTarget(this);
	}

	@Override
	public void onDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		
	}

	@Override
	public void onDragEnter(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		postDelayed(buttonClicked, BUTTON_PERFORM_TIME);
	}
	
	Runnable buttonClicked = new Runnable() {
		
		@Override
		public void run() {
			if (mIconClickListener != null) {
				mIconClickListener.onClick(PrevNextImageView.this);
			}
		}
	};

	@Override
	public void onDragOver(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
	}

	@Override
	public void onDragExit(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		removeCallbacks(buttonClicked);
	}

	@Override
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		return false;
	}

	@Override
	public Rect estimateDropLocation(DragSource source, int x, int y,
			int xOffset, int yOffset, DragView dragView, Object dragInfo,
			Rect recycle) {
		return null;
	}

}
