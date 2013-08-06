package com.example.samplenavermovies.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samplenavermovies.R;
import com.example.samplenavermovies.model.ImageRequest;
import com.example.samplenavermovies.model.NaverMovieItem;
import com.example.samplenavermovies.model.NetworkManager;
import com.example.samplenavermovies.model.NetworkRequest;

public class ItemView extends FrameLayout {

	URLImageView imageView;
	TextView movieTitleView;
	TextView authorView;
	NaverMovieItem mData;
	Handler mHandler = new Handler();
	ImageRequest mRequest;
	
	OnItemImageClickListener mListener;
	
	public interface OnItemImageClickListener {
		public void onItemImageClicked(View view, NaverMovieItem data);
	}
	
	public void setOnItemImageClickListener(OnItemImageClickListener listener) {
		mListener = listener;
	}
	
	public ItemView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_view, this);
		imageView = (URLImageView)findViewById(R.id.image);
		movieTitleView = (TextView)findViewById(R.id.movieTitle);
		authorView = (TextView)findViewById(R.id.author);
		
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemImageClicked(ItemView.this, mData);
				}
			}
		});
	}
	
	public void setItemData(NaverMovieItem data) {
		mData = data;
		movieTitleView.setText(Html.fromHtml(data.title));
		authorView.setText(Html.fromHtml(data.director));
		imageView.setImageURL(data.image);
//		if (mRequest != null) {
//			mRequest.cancel();
//			mRequest = null;
//		}
//		mRequest = new ImageRequest(data.image);
//		NetworkManager.getInstance().getNetworkData(mRequest, new NetworkRequest.OnCompletedListener() {
//			
//			@Override
//			public void onSuccess(NetworkRequest request, Object result) {
//				
//				if (request == mRequest && result != null) {
//					Bitmap bm = (Bitmap)result;
//					imageView.setImageBitmap(bm);
//					mRequest = null;
//				}
//				
//			}
//			
//			@Override
//			public void onFail(NetworkRequest request, int errorCode, String errorMsg) {
//				// TODO Auto-generated method stub
//				
//			}
//		}, mHandler);
	}

}
