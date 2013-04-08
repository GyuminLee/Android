package com.example.samplefragmentdailogtest;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyDialogFragment extends DialogFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		TextView v = new TextView(getActivity());
		v.setText("DialogTest");
		return v;
	}
	
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		AlertDialog dlg = new AlertDialog.Builder(getActivity())
//			.setTitle("Dialog")
//			.setMessage("message")
//			.setIcon(R.drawable.ic_launcher)
//			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					dismiss();
//				}
//			}).create();
//		return dlg;
//	}
}
