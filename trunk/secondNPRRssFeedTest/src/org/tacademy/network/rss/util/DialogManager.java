package org.tacademy.network.rss.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

public class DialogManager {
	
	Dialog mLastDialog;
	
	public static final int WAIT_PROGRESS = 1;
	public static final int USER_DIALOG_ID = 10000;
	
	private static DialogManager instance;
	
	public static DialogManager getInstance() {
		if (instance == null) {
			instance = new DialogManager();
		}
		return instance;
	}
	
	private DialogManager() {
		
	}
	
	public Dialog getDialog(Context context, int id) {
		switch(id) {
		case WAIT_PROGRESS :
			ProgressDialog progress = new ProgressDialog(context);
		    progress.setMessage("Please wait while get uploadUrl...");
		    progress.setIndeterminate(true);
		    progress.setCancelable(true);
		    mLastDialog = progress;
			return progress;
		}
		return null;
	}
	
	public Dialog getDialog(Context context, int id, Bundle bundle) {
		if (bundle == null) {
			return getDialog(context,id);
		}
		return null;
	}
	
	public Dialog getLastDialog() {
		return mLastDialog;
	}
}
