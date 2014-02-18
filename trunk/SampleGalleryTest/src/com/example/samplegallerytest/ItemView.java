package com.example.samplegallerytest;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView imageView;
	TextView titleView;
	MyData mData;
	GestureDetector mDetector;
	
	public MyData getData() {
		return mData;
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				Toast.makeText(getContext(), "single tab", Toast.LENGTH_SHORT).show();
				return true;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				mController.startDrag(ItemView.this, mSource, mData, DragController.DRAG_ACTION_COPY);
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}
		});
		imageView = (ImageView)findViewById(R.id.image);
		titleView = (TextView)findViewById(R.id.title);	
	}

	DragController mController;
	DragSource mSource;
	public void setDragInfo(DragController controller, DragSource source) {
		mController = controller;
		mSource = source;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//return mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	public void setMyData(MyData data) {
		this.mData = data;
		imageView.setImageResource(data.imageId);
		titleView.setText(data.title);
	}

}
