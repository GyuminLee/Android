package org.tacademy.network.rss.board;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;
import org.tacademy.network.rss.util.PropertyManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReplyItemView extends LinearLayout {

	ImageView userImageView;
	TextView contentView;
	TextView modify;
	TextView delete;
	ReplyItemData mReply;
	ImageRequest imageRequest;
	
	public final static String imageSizeParam="&width=50&height=50";
	
	public interface OnDataActionListener {
		public void onModifyClick(ReplyItemData data);
		public void onDeleteClick(ReplyItemData data);
	}
	
	OnDataActionListener mListener;
	
	public void setOnDataActionListener(OnDataActionListener listener) {
		mListener = listener;
	}
	
	public ReplyItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.reply_item, this);
		userImageView = (ImageView)findViewById(R.id.userImage);
		contentView = (TextView)findViewById(R.id.content);
		modify = (TextView)findViewById(R.id.modify);
		modify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onModifyClick(mReply);
				}
			}
		});
		delete = (TextView)findViewById(R.id.delete);
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
					mListener.onDeleteClick(mReply);
				}
			}
		});
		
	}
	
	public void setData(ReplyItemData item) {
		mReply = item;
		contentView.setText(mReply.content);
		userImageView.setImageResource(R.drawable.icon);
		if (imageRequest != null) {
			ImageManager.getInstance().remove(imageRequest);
		}
		imageRequest = new ImageRequest(item.userImageUrl+imageSizeParam);
		imageRequest.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			public void onDownloadCompleted(int result, NetworkRequest request) {
				ImageRequest ir = (ImageRequest)request;
				if (imageRequest == ir) {
					Bitmap bm = ir.getBitmap();
					if (bm != null) {
						userImageView.setImageBitmap(bm);
					}
				}
			}
		});
		ImageManager im = ImageManager.getInstance();
		im.enqueue(imageRequest);
		if (PropertyManager.getInstance().getUserId() == item.userId) {
			modify.setVisibility(View.VISIBLE);
			delete.setVisibility(View.VISIBLE);
		} else {
			modify.setVisibility(View.GONE);
			delete.setVisibility(View.GONE);
		}
		
	}

}
