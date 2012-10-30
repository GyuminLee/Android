package org.tacademy.network.rss.board;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BoardItemView extends LinearLayout {

	ImageView userImageView;
	TextView titleView;
	ImageView boardImageView;
	BoardItemData mData;
	ImageRequest userImageRequest;
	ImageRequest boardImageRequest;
	String sizeParam = "&width=50&height=50";
	
	public interface OnBoardItemImageClickListener {
		public void onBoardItemUserImageClick(BoardItemData data);
		public void onBoardItemBoardImageClick(BoardItemData data);
	}
	
	private OnBoardItemImageClickListener mListener;
	
	public void setOnBoardItemImageClickListener(OnBoardItemImageClickListener listener) {
		mListener = listener;
	}
	
	public BoardItemView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.board_list_item,this);
		userImageView = (ImageView)findViewById(R.id.userImage);
		userImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onBoardItemUserImageClick(mData);
				}
			}
		});
		boardImageView = (ImageView)findViewById(R.id.boardImage);
		boardImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onBoardItemBoardImageClick(mData);
				}
			}
		});
		titleView = (TextView)findViewById(R.id.boardTitle);		
	}
	
	public void setData(BoardItemData data) {
		mData = data;
		titleView.setText(data.title);

		userImageView.setImageResource(R.drawable.icon);
		if (userImageRequest != null) {
			ImageManager.getInstance().remove(userImageRequest);
			userImageRequest = null;
		}
		if (data.userImageUrl != null && !data.userImageUrl.equals("")) {
			userImageRequest = new ImageRequest(data.userImageUrl+sizeParam);
			userImageRequest.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				public void onDownloadCompleted(int result, NetworkRequest request) {
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						if (userImageRequest == request) {
							Bitmap bmp = userImageRequest.getBitmap();
							userImageView.setImageBitmap(bmp);
							userImageRequest = null;
						}
					} else {
						// Error...
					}
				}
			});
			
			ImageManager.getInstance().enqueue(userImageRequest);
		}

		boardImageView.setImageResource(R.drawable.icon);
		if (boardImageRequest != null) {
			ImageManager.getInstance().remove(boardImageRequest);
			boardImageRequest = null;
		}
		if (data.imageUrl != null && !data.imageUrl.equals("")) {
			boardImageRequest = new ImageRequest(data.imageUrl+sizeParam);
			boardImageRequest.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				public void onDownloadCompleted(int result, NetworkRequest request) {
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						if (boardImageRequest == request) {
							Bitmap bmp = boardImageRequest.getBitmap();
							boardImageView.setImageBitmap(bmp);
							boardImageRequest = null;
						}
					}
				}
			});
			
			ImageManager.getInstance().enqueue(boardImageRequest);
		}
		
	}

}
