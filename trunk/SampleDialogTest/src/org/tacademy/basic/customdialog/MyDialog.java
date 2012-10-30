package org.tacademy.basic.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MyDialog extends Dialog {

	Context mContext;
	DatePicker mDatePicker;
	TimePicker mTimePicker;
	
	public interface OnMyDialogClickListener {
		public void onMyButton1Click(int year,int month,int day, int hour, int min);
	}
	
	OnMyDialogClickListener mListener;
	
	public void setOnMyDialogClickListener(OnMyDialogClickListener listener) {
		mListener = listener;
	}
	
	public MyDialog(Context context) {
		super(context);
		mContext = context;
		
		setContentView(R.layout.my_dialog_layout);
		mDatePicker = (DatePicker)findViewById(R.id.datePicker1);
		mTimePicker = (TimePicker)findViewById(R.id.timePicker1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(mContext, "...", Toast.LENGTH_LONG).show();
				if (mListener != null) {
					int year = mDatePicker.getYear();
					int month = mDatePicker.getMonth();
					int day = mDatePicker.getDayOfMonth();
					int hour = mTimePicker.getCurrentHour();
					int min = mTimePicker.getCurrentMinute();
					mListener.onMyButton1Click(year,month,day,hour,min);
					dismiss();
				}
			}
		});
	}

}
