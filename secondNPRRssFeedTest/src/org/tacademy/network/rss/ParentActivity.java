package org.tacademy.network.rss;

import org.tacademy.network.rss.util.DialogManager;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Debug;
import android.widget.Toast;

public class ParentActivity extends Activity {

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Debug.startMethodTracing(this.getClass().getSimpleName());
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Debug.stopMethodTracing();
		super.onStop();
	}
	public void showProgress() {
		showDialog(DialogManager.WAIT_PROGRESS);
	}
	
	public void dismissProgress() {
		dismissDialog(DialogManager.WAIT_PROGRESS);
	}
	
	public void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		return DialogManager.getInstance().getDialog(this, id, bundle);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return DialogManager.getInstance().getDialog(this, id);
	}

}
