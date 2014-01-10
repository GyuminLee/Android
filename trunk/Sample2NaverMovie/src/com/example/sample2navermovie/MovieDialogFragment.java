package com.example.sample2navermovie;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MovieDialogFragment extends DialogFragment {
	NetworkModel.OnNetworkResultListener mListener;
	MovieRequest mRequest;
	
	public void setOnNetworkResultListener(MovieRequest request) {
		mRequest = request;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NetworkModel.getInstance().getMovieData(mRequest, new NetworkModel.OnNetworkResultListener() {
			
			@Override
			public void onResultSuccess(MovieRequest movies) {
				dismiss();
				if (mRequest != null) {
					mRequest.sendSuccess();
				}
			}
			
			@Override
			public void onResultFail(int errorCode) {
				dismiss();
				if (mRequest != null) {
					mRequest.sendError();
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
