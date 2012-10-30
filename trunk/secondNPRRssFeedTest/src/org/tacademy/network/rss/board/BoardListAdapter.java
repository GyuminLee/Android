package org.tacademy.network.rss.board;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BoardListAdapter extends BaseAdapter implements BoardItemView.OnBoardItemImageClickListener {

	private ArrayList<BoardItemData> items;
	private Context mContext;
	private int totalCount = 0;
	
	public interface OnBoardItemImageClickListener {
		public void onBoardItemImageClick(String imageUrl);
	}
	
	private OnBoardItemImageClickListener mListener;
	
	public void setOnBoardItemImageClickListener(OnBoardItemImageClickListener listener) {
		mListener = listener;
	}
	
	public BoardListAdapter(Context context) {
		mContext = context;
		items = new ArrayList<BoardItemData>();
	}
	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return items.get(position).id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		BoardItemView view = (BoardItemView)convertView;
		if (view == null) {
			view = new BoardItemView(mContext);
			view.setOnBoardItemImageClickListener(this);
		}
		view.setData((BoardItemData)getItem(position));
		return view;
	}

	public void addItem(BoardItemData item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void setItem(BoardItemData item) {
		for (BoardItemData data : items) {
			if (data.id == item.id) {
				data.title = item.title;
				data.imageUrl = item.imageUrl;
				data.content = item.content;
				return;
			}
		}
	}

	public void addItem(ArrayList<BoardItemData> itemArray) {
		items.addAll(itemArray);
		notifyDataSetChanged();
	}

	public void clearAll() {
		// TODO Auto-generated method stub
		items.clear();
		notifyDataSetChanged();
	}

	public void setTotalCount(int total) {
		// TODO Auto-generated method stub
		totalCount = total;
		
	}

	@Override
	public void onBoardItemUserImageClick(BoardItemData data) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onBoardItemImageClick(data.userImageUrl);
		}
	}

	@Override
	public void onBoardItemBoardImageClick(BoardItemData data) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onBoardItemImageClick(data.imageUrl);
		}
	}

}
