package com.example.testfragmentdialogsample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(getActivity());
		tv.setText("MyDialog");
		tv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		setCancelable(true);
		return tv;
	}
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//		builder.setIcon(R.drawable.ic_launcher);
//		builder.setTitle("My Dialog");
//		builder.setMessage("My Dialog Message");
//		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(getActivity(), "clicked YES", Toast.LENGTH_SHORT).show();
//			}
//		});
//		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		AlertDialog dlg = builder.create();
//		return dlg;
//	}
}
