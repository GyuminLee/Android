package com.example.sample2navermovie;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MovieDialogFragment extends DialogFragment {
	NetworkModel.OnNetworkResultListener mListener;
	NetworkRequest mRequest;
	
	public void setRequest(NetworkRequest request) {
		mRequest = request;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NetworkModel.getInstance().getNetworkData(mRequest, new NetworkModel.OnNetworkResultListener() {
			
			@Override
			public void onResultSuccess(NetworkRequest movies) {
				dismiss();
				if (mRequest != null) {
					mRequest.sendSuccess();
				}
			}
			
			@Override
			public void onResultFail(NetworkRequest request, int errorCode) {
				dismiss();
				if (mRequest != null) {
					mRequest.sendError(errorCode);
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
