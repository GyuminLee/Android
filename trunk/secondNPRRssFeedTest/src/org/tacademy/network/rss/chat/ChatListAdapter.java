package org.tacademy.network.rss.chat;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatListAdapter extends BaseAdapter implements 
	FriendItemView.OnUserImageClickListener,
	FriendItemView.OnButtonClickListener,
	FriendItemView.OnDeleteClickListener {

	Context mContext;
	ArrayList<FriendItem> items;
	
	public interface OnItemImageClickListener {
		public void onItemImageClick(FriendItem data);
	}
	
	public interface OnItemAcceptClickListener {
		public void onItemAcceptClick(FriendItem data);
		public void onItemRejectClick(FriendItem data);
	}
	
	public interface OnItemDeleteClickListener {
		public void onItemDeleteClick(FriendItem data);
	}
	
	OnItemImageClickListener mImageListener;
	OnItemAcceptClickListener mAcceptListener;
	OnItemDeleteClickListener mDeleteListener;
	
	public void setOnItemImageClickListener(OnItemImageClickListener listener) {
		mImageListener = listener;
	}
	
	public void setOnItemAcceptClickListener(OnItemAcceptClickListener listener) {
		mAcceptListener = listener;
	}
	
	public void setOnItemDeleteClickListener(OnItemDeleteClickListener listener) {
		mDeleteListener = listener;
	}
	
	public ChatListAdapter(Context context) {
		mContext = context;
		items = new ArrayList<FriendItem>();
	}
	
	public void add(FriendItem data) {
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void add(ArrayList<FriendItem> data) {
		items.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}
	
	public void remove(FriendItem data) {
		FriendItem removeItem = null;
		for (FriendItem item : items) {
			if (item.id == data.id) {
				removeItem = item;
				break;
			}
		}
		items.remove(removeItem);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return items.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		FriendItemView view = (FriendItemView)convertView;
		if (view == null) {
			view = new FriendItemView(mContext);
			view.setOnUserImageClickListener(this);
			view.setOnButtonClickListener(this);
			view.setOnDeleteClickListener(this);
		}
		view.setData((FriendItem)getItem(position));
		
		return view;
	}

	@Override
	public void onUserImageClick(FriendItem data) {
		// TODO Auto-generated method stub
		if (mImageListener != null) {
			mImageListener.onItemImageClick(data);
		}
	}

	@Override
	public void onDeleteClick(FriendItem data) {
		// TODO Auto-generated method stub
		if (mDeleteListener != null) {
			mDeleteListener.onItemDeleteClick(data);
		}
	}

	@Override
	public void onAcceptButtonClick(FriendItem data) {
		// TODO Auto-generated method stub
		if (mAcceptListener != null) {
			mAcceptListener.onItemAcceptClick(data);
		}
	}

	@Override
	public void onRejectButtonClick(FriendItem data) {
		// TODO Auto-generated method stub
		if (mAcceptListener != null) {
			mAcceptListener.onItemRejectClick(data);
		}
	}

}
