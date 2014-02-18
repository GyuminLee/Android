package com.example.samplegallerytest;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DropTargetImageView extends ImageView implements DropTarget {

	public interface OnItemDropListener {
		public void onItemDrop(MyData data);
	}
	
	private OnItemDropListener mListener;
	
	public void setOnItemDropListener(OnItemDropListener listener) {
		mListener = listener;
	}
	
	public DropTargetImageView(Context context) {
		super(context);
	}

	public DropTargetImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DropTargetImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		if (mListener != null) {
			mListener.onItemDrop((MyData)dragInfo);
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
		return true;
	}

	@Override
	public Rect estimateDropLocation(DragSource source, int x, int y,
			int xOffset, int yOffset, DragView dragView, Object dragInfo,
			Rect recycle) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
