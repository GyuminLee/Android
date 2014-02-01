package com.begentgroup.draganddrop.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.begentgroup.draganddrop.DragSource;
import com.begentgroup.draganddrop.DragView;
import com.begentgroup.draganddrop.DropTarget;

public class IconDropTargetView extends FrameLayout implements DropTarget {

	private int number;
	private boolean isPossibleDrop = true;

	public interface OnDropListener {
		public void onDrop(View view,int number, Object dragInfo);
	}
	
	OnDropListener mListener;
	
	public void setOnDropListener(OnDropListener listener) {
		mListener = listener;
	}
	
	public IconDropTargetView(Context context) {
		super(context);
	}

	public IconDropTargetView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public IconDropTargetView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		if (mListener != null) {
			mListener.onDrop(this, number, dragInfo);
		}
	}

	@Override
	public void onDragEnter(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
	}

	@Override
	public void onDragOver(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
	}

	@Override
	public void onDragExit(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
	}

	@Override
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		return isPossibleDrop;
	}

	@Override
	public Rect estimateDropLocation(DragSource source, int x, int y,
			int xOffset, int yOffset, DragView dragView, Object dragInfo,
			Rect recycle) {
		// TODO Auto-generated method stub
		return null;
	}

	public  void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setPossibleDrop(boolean isPossible) {
		this.isPossibleDrop = isPossible;
	}
	
	public boolean isPossibleDrop() {
		return isPossibleDrop;
	}
}
