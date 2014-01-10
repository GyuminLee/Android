package com.example.sample2navermovie;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MovieDialogFragment extends DialogFragment {
	String keyword;
	NetworkModel.OnNetworkResultListener mListener;
	
	public void setOnNetworkResultListener(NetworkModel.OnNetworkResultListener listener) {
		mListener = listener;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		if (b != null) {
			keyword = b.getString("keyword");
		}
		NetworkModel.getInstance().getMovieData(keyword, new NetworkModel.OnNetworkResultListener() {
			
			@Override
			public void onResultSuccess(NaverMovies movies) {
				dismiss();
				if (mListener != null) {
					mListener.onResultSuccess(movies);
				}
			}
			
			@Override
			public void onResultFail(int errorCode) {
				dismiss();
				if (mListener != null) {
					mListener.onResultFail(errorCode);
				}
			}
		});
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ProgressDialog dialog;
		dialog = new ProgressDialog(getActivity());
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle("Download Movie");
		dialog.setMessage("Please wait...");
		return dialog;
	}
}
