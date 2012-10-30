package org.tacademy.basic.sampletabfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class TabFragment extends Fragment {

	public final static int DEFAULT_REQUEST_CODE = -1;
	
	private Bundle mResult;
	private int mRequestCode = DEFAULT_REQUEST_CODE;
	
	public final static String RESULT_CODE = "result_code";
	public final static String REQUEST_CODE = "request_code";
	
	
	public void setResultBundle(Bundle result) {
		mResult = result;
	}
	
	public void setRequestCode(int requestCode) {
		mRequestCode = requestCode;
	}
	
	protected void addFragment(TabFragment f) {
		if (getTabFragmentActivity() != null) {
			getTabFragmentActivity().addFragmentAtCurrentTab(f);
		}
	}
	
	protected void addFragmentToBackStack(TabFragment f) {
		addFragmentToBackStackForResult(f,DEFAULT_REQUEST_CODE);
	}
	
	protected void addFragmentToBackStackForResult(TabFragment f,int requestCode) {
		if (getTabFragmentActivity() != null) {
			f.setRequestCode(requestCode);
			getTabFragmentActivity().addFragmentStackAtCurrentTab(f);
		}
	}
	
	protected boolean popBackStack() {
		return popBackStack(Activity.RESULT_CANCELED,null);
	}
	
	protected boolean popBackStack(int resultCode,Bundle args) {
		if (args == null) {
			args = new Bundle();
		}
		args.putInt(RESULT_CODE, resultCode);
		args.putInt(REQUEST_CODE, mRequestCode);
		return getTabFragmentActivity().popBackStackAtCurrentTab(this,args);
	}
	
	protected void moveFirstFragment() {
		getTabFragmentActivity().moveFirstFragmentAtCurrentTab();
	}
	
	protected void showDialogFragment(DialogFragment f,String tag) {
		f.show(getFragmentManager(),tag);
	}

	private TabFragmentActivity getTabFragmentActivity() {
		Activity activity = getActivity();
		if (activity instanceof TabFragmentActivity) {
			return (TabFragmentActivity)activity;
		}
		return null;
	}
	
	public boolean onBackPressed() {
		return false;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (mResult != null) {
			final int resultCode = mResult.getInt(RESULT_CODE);
			final int requestCode = mResult.getInt(REQUEST_CODE);
				
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					onFragmentResult(requestCode,resultCode,mResult);
					mResult = null;
				}
				
			});
		}
	}
	
	public void onFragmentResult(int requestCode,int resultCode,Bundle b) {
	}
}
