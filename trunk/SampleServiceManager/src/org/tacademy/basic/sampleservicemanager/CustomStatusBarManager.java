package org.tacademy.basic.sampleservicemanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomStatusBarManager {

	public static final int DISABLE_EXPAND = 0x00000001;
	public static final int DISABLE_NOTIFICATION_ICONS = 0x00000002;
	public static final int DISABLE_NOTIFICATION_ALERTS = 0x00000004;
	public static final int DISABLE_NOTIFICATION_TICKER = 0x00000008;
	public static final int DISABLE_NONE = 0x00000000;

	public static final String STATUS_BAR_SERVICE = "statusbar";

	public Object mStatusBarManager;

	public CustomStatusBarManager(Object statusBarManager) {
		mStatusBarManager = statusBarManager;
	}

	public void disable(int what) {
		try {
			Method disable = mStatusBarManager.getClass().getMethod(
					"disable", Integer.class);
			disable.invoke(mStatusBarManager, (Integer) what);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void expand() {
		try {
			Method expand = mStatusBarManager.getClass().getMethod("expand",
					null);
			expand.invoke(mStatusBarManager, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void collapse() {
		try {
			Method collapse = mStatusBarManager.getClass().getMethod("", null);
			collapse.invoke(mStatusBarManager, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIcon(String slot, int iconId, int iconLevel) {
		try {
			Method setIcon = mStatusBarManager.getClass().getMethod(
					"setIcon", String.class, Integer.class, Integer.class);
			setIcon.invoke(mStatusBarManager, slot, (Integer) iconId,
					(Integer) iconLevel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeIcon(String slot) {
		try {
			Method removeIcon = mStatusBarManager.getClass().getMethod(
					"removeIcon", String.class);
			removeIcon.invoke(mStatusBarManager, slot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIconVisibility(String slot, boolean visible) {
		try {
			Method setIconVisibility = mStatusBarManager.getClass().getMethod(
					"setIconVisibility", String.class, Boolean.class);
			setIconVisibility
					.invoke(mStatusBarManager, slot, (Boolean) visible);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
