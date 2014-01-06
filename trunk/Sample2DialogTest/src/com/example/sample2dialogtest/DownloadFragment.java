package com.example.sample2dialogtest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;

public class DownloadFragment extends DialogFragment {

	Handler mHandler = new Handler();
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle("download");
		dialog.setMessage("file1...");
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				dismiss();
			}
		}, 5000);
		return dialog;
	}
}
