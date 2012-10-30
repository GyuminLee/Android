package org.tacademy.network.rss.chat;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendItemView extends LinearLayout {

	ImageView userImageView;
	TextView userNameView;
	TextView userEmailView;
	FriendItem mData;
	Button rejectButton;
	Button acceptButton;
	ImageRequest imageRequest;
	
	public interface OnUserImageClickListener {
		public void onUserImageClick(FriendItem data);
	}
	OnUserImageClickListener mImageListener;
	
	public void setOnUserImageClickListener(OnUserImageClickListener listener) {
		mImageListener = listener;
	}
	
	public interface OnButtonClickListener {
		public void onAcceptButtonClick(FriendItem data);
		public void onRejectButtonClick(FriendItem data);
	}
	
	OnButtonClickListener mButtonListener;

	public void setOnButtonClickListener(OnButtonClickListener listener) {
		mButtonListener = listener;
	}
	
	public interface OnDeleteClickListener {
		public void onDeleteClick(FriendItem data);
	}
	
	OnDeleteClickListener mDeleteListener;
	
	public void setOnDeleteClickListener(OnDeleteClickListener listener) {
		mDeleteListener = listener;
	}
	
	public FriendItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.friend_item, this);
		userImageView = (ImageView)findViewById(R.id.userImage);
		userNameView = (TextView)findViewById(R.id.userName);
		userEmailView = (TextView)findViewById(R.id.userEmail);
		acceptButton = (Button)findViewById(R.id.accept);
		acceptButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mData != null) {
					if (mData.type == FriendItem.ITEM_TYPE_REQUEST) {
						if (mDeleteListener != null) {
							mDeleteListener.onDeleteClick(mData);
						}
					} else if (mData.type == FriendItem.ITEM_TYPE_RESPONSE) {
						if (mButtonListener != null) {
							mButtonListener.onAcceptButtonClick(mData);
						}
					}
				}
				
			}
		});
		
		rejectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mData != null) {
					if (mData.type == FriendItem.ITEM_TYPE_RESPONSE) {
						if (mButtonListener != null) {
							mButtonListener.onRejectButtonClick(mData);
						}
					}
				}
			}
		});
		
		rejectButton = (Button)findViewById(R.id.delete);
		userImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mImageListener != null) {
					mImageListener.onUserImageClick(mData);
				}
			}
		});
	}
	
	public void setData(FriendItem data) {
		mData = data;
		userNameView.setText(data.name);
		userEmailView.setText(data.email);
		
		if (mData.type == FriendItem.ITEM_TYPE_FRIEND) {
			acceptButton.setVisibility(View.GONE);
			rejectButton.setVisibility(View.GONE);
		} else if (mData.type == FriendItem.ITEM_TYPE_REQUEST) {
			acceptButton.setVisibility(View.VISIBLE);
			acceptButton.setText("삭제");
			rejectButton.setVisibility(View.GONE);
		} else if (mData.type == FriendItem.ITEM_TYPE_RESPONSE) {
			acceptButton.setVisibility(View.VISIBLE);
			acceptButton.setText("수락");
			rejectButton.setVisibility(View.VISIBLE);
		}
		if (imageRequest != null) {
			ImageManager.getInstance().remove(imageRequest);
			imageRequest = null;
		}
		userImageView.setImageResource(R.drawable.icon);
		if (data.userImageUrl != null) {
			imageRequest = new ImageRequest(mData.userImageUrl + "&width=50&height=50");
			imageRequest.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				@Override
				public void onDownloadCompleted(int result, NetworkRequest request) {
					// TODO Auto-generated method stub
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						if (imageRequest == request) {
							Bitmap bm = imageRequest.getBitmap();
							if (bm != null) {
								userImageView.setImageBitmap(bm);
							}
							imageRequest = null;
						}
					}
				}
			});
			
			ImageManager.getInstance().enqueue(imageRequest);
		}
	}

}
