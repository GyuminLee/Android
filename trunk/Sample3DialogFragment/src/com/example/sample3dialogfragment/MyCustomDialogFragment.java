package com.example.sample3dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MyCustomDialogFragment extends DialogFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setCancelable(false);
//		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Dialog dialog = getDialog();
		if (dialog != null) {
			Window w = dialog.getWindow();
			w.setTitle("MyTitle");
		}
		View v = inflater.inflate(R.layout.custom_dialog, container, false);
		Button btn = (Button)v.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();
				MyDialogFragment dialog = new MyDialogFragment();
				FragmentManager fm = ((FragmentActivity)getActivity()).getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				Fragment f = fm.findFragmentByTag("dialog");
				if (f != null) {
					ft.remove(f);
				}
				ft.addToBackStack(null);
				dialog.show(ft, "dialog");
//				dismiss();
			}
		});
		return v;
	}
}
