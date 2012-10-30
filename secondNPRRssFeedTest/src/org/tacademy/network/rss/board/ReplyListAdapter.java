package org.tacademy.network.rss.board;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ReplyListAdapter extends BaseAdapter implements ReplyItemView.OnDataActionListener {

	Context mContext;
	ArrayList<ReplyItemData> items;
	
	public interface OnReplyItemModifyClickListener {
		public void onReplyItemModifyClick(ReplyItemData data);
		public void onReplyItemDeleteClick(ReplyItemData data);
	}
	
	OnReplyItemModifyClickListener mListener;
	
	public void setOnReplyItemModifyClickListener(OnReplyItemModifyClickListener listener) {
		mListener = listener;
	}
	
	public ReplyListAdapter(Context context) {
		mContext = context;
		items = new ArrayList<ReplyItemData>();
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
		ReplyItemView view = (ReplyItemView)convertView;
		if (view == null) {
			view = new ReplyItemView(mContext);
			view.setOnDataActionListener(this);
		}
		view.setData((ReplyItemData)getItem(position));
		return view;
	}

	public void add(ReplyItemData data) {
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void add(ArrayList<ReplyItemData> data) {
		items.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}
	
	public void set(ReplyItemData data) {
		for (ReplyItemData item : items) {
			if (item.id == data.id) {
				item.content = data.content;
				notifyDataSetChanged();
				return;
			}
		}
	}
	
	public void delete(ReplyItemData data) {
		ReplyItemData deleteItem = null;
		for (ReplyItemData item : items) {
			if (item.id == data.id) {
				deleteItem = item;
				break;
			}
		}
		if (deleteItem != null) {
			items.remove(deleteItem);
			notifyDataSetChanged();
		}
	}
	
	@Override
	public void onModifyClick(ReplyItemData data) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onReplyItemModifyClick(data);
		}
	}

	@Override
	public void onDeleteClick(ReplyItemData data) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onReplyItemDeleteClick(data);
		}
	}

}
