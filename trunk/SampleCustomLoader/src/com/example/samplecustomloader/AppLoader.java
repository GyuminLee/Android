package com.example.samplecustomloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.AsyncTaskLoader;

public class AppLoader extends AsyncTaskLoader<List<ItemData>> {

	PackageManager mPM;
	
	List<ItemData> mAppList;
	
	public AppLoader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mPM = context.getPackageManager();
	}

	@Override
	public List<ItemData> loadInBackground() {
		// TODO Auto-generated method stub
		List<ApplicationInfo> mAppInfos = mPM.getInstalledApplications(
				PackageManager.GET_UNINSTALLED_PACKAGES | 
				PackageManager.GET_DISABLED_COMPONENTS);
		List<ItemData> appList = new ArrayList<ItemData>();
		for(int i = 0; i < mAppInfos.size(); i++) {
			ApplicationInfo appInfo = mAppInfos.get(i);
			ItemData item = new ItemData();
			File mApp = new File(appInfo.sourceDir);
			if (mApp.exists()) {
				item.name = (String) appInfo.loadLabel(mPM);
				item.icon = appInfo.loadIcon(mPM);
				item.intent = mPM.getLaunchIntentForPackage(appInfo.packageName);
				if (item.intent != null) {
					item.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				}
			}
			appList.add(item);
		}
		return appList;
	}
	
	@Override
	public void deliverResult(List<ItemData> data) {
		// TODO Auto-generated method stub
		if (isStarted()) {
			mAppList = data;
			super.deliverResult(data);
		}
	}
	
	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
		if (mAppList != null) {
			deliverResult(mAppList);
		} else {
			forceLoad();
		}
	}
	
	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		super.onReset();
		
		mAppList = null;
	}
}
