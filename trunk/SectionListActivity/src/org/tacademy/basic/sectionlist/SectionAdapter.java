package org.tacademy.basic.sectionlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SectionAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<MyData> items;
	ArrayList<DataType> dataTypeList;
	int mTotal;
	
	public interface OnSectionItemClickListener {
		public void onSectionClick(MyData data);
		public void onItemClick(MyData data);
		public void onMoreClick();
	}
		
	private OnSectionItemClickListener mListener;
	
	public void setOnSectionItemClickListener(OnSectionItemClickListener listener) {
		mListener = listener;
	}

	public SectionAdapter(Context context,ArrayList<MyData> items,int total,ListView listView) {
		mContext = context;
		this.items = items;
		mTotal = total;
		dataTypeList = makeDataType(items,1);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				if (getItemType(position) == DataType.SECTION_TYPE) {
					showSection(((MyData)getItem(position)).sectionId);
					if (mListener != null) {
						mListener.onSectionClick((MyData)getItem(position));
					}
				} else if (getItemType(position) == DataType.MORE_TYPE) {
					if (mListener != null) {
						mListener.onMoreClick();
					}
				} else {
					if (mListener != null) {
						mListener.onItemClick((MyData)getItem(position));
					}
				}
			}
		});
	}
	
	private ArrayList<DataType> makeDataType(ArrayList<MyData> data,int showSection) {
		ArrayList<MyData> sortedList = new ArrayList<MyData>(data);
		Collections.sort(sortedList);

		ArrayList<DataType> typeDataList = new ArrayList<DataType>();
		int sectionId = -1;
		DataType dt;
		for (int i = 0; i < sortedList.size(); i++) {
			MyData md = sortedList.get(i);
			if (sectionId != md.sectionId) {
				dt = new DataType();
				dt.type = DataType.SECTION_TYPE;
				dt.data = md;
				dt.id = -1;
				typeDataList.add(dt);
				sectionId = md.sectionId;
			}
			if (md.sectionId == showSection) {
				dt = new DataType();
				dt.type = DataType.DATA_TYPE;
				dt.data = md;
				dt.id = i;
				typeDataList.add(dt);
			}
		}
		if (isMoreItemView()) {
			dt = new DataType();
			dt.type = DataType.MORE_TYPE;
			dt.data = null;
			dt.id = -1;
			typeDataList.add(dt);
		}
		return typeDataList;
	}

	public void setSelection(int position, boolean selected) {
		DataType dt = dataTypeList.get(position);
		dt.isSelected = selected;
		notifyDataSetChanged();
	}
	
	public boolean isSelected(int position) {
		return dataTypeList.get(position).isSelected;
	}
	
	public ArrayList<Boolean> getSelectioArray() {
		ArrayList<Boolean> selectArray = new ArrayList<Boolean>();
		for (int i = 0; i < dataTypeList.size(); i ++) {
			if (dataTypeList.get(i).isSelected) {
				selectArray.add((Boolean)true);
			} else {
				selectArray.add((Boolean)false);
			}
		}
		return null;
	}
	
	public void showSection(int sectionId) {
		dataTypeList = makeDataType(items,sectionId);
		notifyDataSetChanged();
	}
	
	private boolean isMoreItemView() {
		if (items.size() < mTotal) {
			return true;
		}
		return false;
	}

	public int getCount() {
		return dataTypeList.size();
	}
	
	public Object getItem(int position) {
		DataType dt = dataTypeList.get(position);
		return dt.data;
	}

	public long getItemId(int position) {
		return dataTypeList.get(position).id;
	}

	public int getItemType(int position) {
		return dataTypeList.get(position).type;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		DataType dt = dataTypeList.get(position);
		if (dt.type == DataType.SECTION_TYPE) {
			SectionItemView view;
			if (convertView == null) {
				view = new SectionItemView(mContext);
			} else {
				if (convertView instanceof SectionItemView) {
					view = (SectionItemView)convertView;
				} else {
					view = new SectionItemView(mContext);
				}
			}
			view.setData(((MyData)dt.data).sectionText);
			return view;
		} else if (dt.type == DataType.DATA_TYPE) {
			DataItemView view;
			if (convertView == null) {
				view = new DataItemView(mContext);
			} else {
				if (convertView instanceof DataItemView) {
					view = (DataItemView)convertView;
				} else {
					view = new DataItemView(mContext);
				}
			}
			view.setData(((MyData)dt.data).childText);
			view.setSelection(dt.isSelected);
			return view;
		} else if (dt.type == DataType.MORE_TYPE) {
			TextView view;
			if (convertView == null) {
				view = new TextView(mContext);
			} else {
				if (convertView instanceof TextView) {
					view = (TextView)convertView;
				} else {
					view = new TextView(mContext);
				}
			}
			view.setText("´õº¸±â");
			return view;
		}
		return null;
	}

}
