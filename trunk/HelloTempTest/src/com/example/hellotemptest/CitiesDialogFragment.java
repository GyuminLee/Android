package com.example.hellotemptest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.hellotemptest.NetworkRequest.OnResultListener;

public class CitiesDialogFragment extends DialogFragment {
	public static final String ARGUMENT_CITY = "city";
	
	public interface OnCityResultListener {
		public void onCityResult(Cities cities);
	}
	
	OnCityResultListener mListener;
	
	public void setOnCityResultListener(OnCityResultListener listener) {
		mListener = listener;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		String city = args.getString(ARGUMENT_CITY);
		if (city != null && !city.equals("")) {
			CityRequest request = new CityRequest(city);
			NetworkModel.getInstance().getNetworkData(request, new OnResultListener<Cities>() {

				@Override
				public void onSuccess(NetworkRequest request, Cities result) {
					if (mListener != null) {
						mListener.onCityResult(result);
					}
					dismiss();
				}

				@Override
				public void onError(NetworkRequest request, int error) {
					if (mListener != null) {
						mListener.onCityResult(null);
					}
					dismiss();
				}
				
			});
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ProgressDialog dlg = new ProgressDialog(getActivity());
		dlg.setTitle("Downloading Cities...");
		dlg.setMessage("Please wait...");
		return dlg;
	}
}
