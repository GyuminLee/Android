package org.tacademy.basic.launcheractivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;

public class SampleLauncherActivityActivity extends LauncherActivity {

	@Override
	protected Intent getTargetIntent() {
		// TODO Auto-generated method stub
		Intent targetIntent = new Intent(Intent.ACTION_MAIN, null);
		targetIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return targetIntent;
	}
    /** Called when the activity is first created. */

}