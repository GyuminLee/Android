package org.tacademy.basic.scrollview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextShowScrollView extends HorizontalScrollView {

	Context mContext;
	LinearLayout mContainer;
	public static final int NOT_SELECTED_COLOR = Color.WHITE;
	public static final int SELECTED_COLOR = Color.RED;
	
	int selectedIndex = -1;
	
	public interface OnNumberTextClickListener {
		public void onNumberClick(int number);
	}
	
	OnNumberTextClickListener mListener;
	
	public void setOnNumberTextClickListener(OnNumberTextClickListener listener) {
		mListener = listener;
	}
	
	public TextShowScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public TextShowScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		// TODO Auto-generated method stub
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.scroll_view_component, this);
		mContainer = (LinearLayout)findViewById(R.id.container);
	}
	
	public void setTextViewWithNumber(int count) {
		mContainer.removeAllViews();
		selectedIndex = -1;
		for (int i = 0; i < count; i++) {
			View view = makeTextView(mContext,i);
			mContainer.addView(view,i);
		}		
	}

	public void setCurrentTextView(int number) {
		TextView view = (TextView)mContainer.getChildAt(number);
		if (view != null) {
			if (selectedIndex != -1) {
				TextView oldSelectedView = (TextView)mContainer.getChildAt(selectedIndex);
				oldSelectedView.setTextColor(NOT_SELECTED_COLOR);
			}
			view.setTextColor(SELECTED_COLOR);
			int left = view.getLeft();
			int widthCenter = (this.getMeasuredWidth() - view.getMeasuredWidth())/ 2;
			int scrollX;
			if (left < widthCenter) {
				scrollX = 0;
			} else {
				scrollX = left - widthCenter;
			}
			this.scrollTo(scrollX, 0);
			selectedIndex = number;
		}
	}
	
	OnClickListener mTextClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int number = (Integer)v.getTag();
			setCurrentTextView(number);
			if (mListener != null) {
				mListener.onNumberClick(number);
			}
		}
	};
	
	public TextView makeTextView(Context context, int number) {
		TextView view = (TextView)LayoutInflater.from(context).inflate(R.layout.my_text, null);
		view.setText(" No." + (number + 1) + " ");
		view.setTag((Integer)number);
		view.setOnClickListener(mTextClickListener);
		return view;
	}
	
	

}
