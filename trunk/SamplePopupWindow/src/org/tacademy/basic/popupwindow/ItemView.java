package org.tacademy.basic.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ItemView extends LinearLayout implements MyPopupWindow.OnMyPopupMenuClickListener {

	TextView text;
	TextView showPopup;
	MyPopupWindow popup;
	View popupLayout;
	View layout;
	public interface OnMyItemMenuClickListener {
		public void onItemMenuUpdateClick(ItemView item);
		public void onItemMenuDeleteClick(ItemView item);
	}
	
	private OnMyItemMenuClickListener mListener;
	
	public void setOnMyItemMenuClickListener(OnMyItemMenuClickListener listener) {
		mListener = listener;
	}
	
	public ItemView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = inflater.inflate(R.layout.item_layout, this);
		text = (TextView)findViewById(R.id.text);
		showPopup = (TextView)findViewById(R.id.showPopup);
		showPopup.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//popup.showAtLocation(popupLayout, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
				popup.setOnMyPopupMenuClickListener(ItemView.this);
				popup.dismiss();
				popup.showAsDropDown(showPopup);
			}
		});
	}

	
	public void setData(String str,MyPopupWindow popup) {
		text.setText(str);
		this.popup = popup;
	}


	public void onMyPopupMenuClick(int menuId) {
		if (menuId == MyPopupWindow.POPUP_MENU_UPDATE) {
			if (mListener != null) {
				mListener.onItemMenuUpdateClick(this);
			}
		} else if (menuId == MyPopupWindow.POPUP_MENU_DELETE) {
			if (mListener != null) {
				mListener.onItemMenuDeleteClick(this);
			}
		}
		popup.dismiss();
	}
}
