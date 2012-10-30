package org.tacademy.basic.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MyPopupWindow extends PopupWindow {

	public static final int POPUP_MENU_UPDATE = 1;
	public static final int POPUP_MENU_DELETE = 2;
	public interface OnMyPopupMenuClickListener {
		public void onMyPopupMenuClick(int menuId);
	}
	
	OnMyPopupMenuClickListener mListener;
	public void setOnMyPopupMenuClickListener(OnMyPopupMenuClickListener listener) {
		mListener = listener;
	}
	
	public MyPopupWindow(Context context) {
		super(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
		setContentView(layout);
		setWidth(200);
		setHeight(200);
		setFocusable(false);
		TextView one = (TextView)layout.findViewById(R.id.popupOne);
		one.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onMyPopupMenuClick(POPUP_MENU_UPDATE);
				}
			}
		});
	}

}
