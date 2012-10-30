package com.example.hellonetwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {

	URLImageView mImageView;
	TextView mTextView;
	Context mContext;
	NaverMovieItem mData;
	ImageRequest mRequest;
	Handler mHandler = new Handler();
	
	public interface OnImageClickListener {
		public void onImageClick(NaverMovieItem data);
	}
	
	private OnImageClickListener mListener;
	
	public void setOnImageClickListener(OnImageClickListener listener) {
		mListener = listener;
	}
	
	public ItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.item_layout, this);
		mImageView = (URLImageView)findViewById(R.id.imageView1);
		mTextView = (TextView)findViewById(R.id.textView1);
		mImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onImageClick(mData);
				}
			}
		});
	}
	
	NetworkRequest.OnProcessCompletedListener mCompleted = new NetworkRequest.OnProcessCompletedListener() {
		
		@Override
		public void onCompleted(NetworkRequest request) {
			// TODO Auto-generated method stub
			if (mRequest == request) {
				mImageView.setImageBitmap((Bitmap)request.getResult());
				mRequest = null;
			}
		}
	};
	
	public void setData(NaverMovieItem data) {
		mData = data;
		//mImageView.setImageResource(data.imageResId);
		mImageView.setImageURL(data.image);
		mTextView.setText(data.title);
	}

}
