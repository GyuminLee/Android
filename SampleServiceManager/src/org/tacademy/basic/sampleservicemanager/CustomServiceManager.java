package org.tacademy.basic.sampleservicemanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.os.IBinder;

public class CustomServiceManager {
	public static IBinder getService(String name) {
		try {
			Method getService = Class.forName("android.os.ServiceManager")
					.getMethod("getService", String.class);
			return (IBinder) getService.invoke(null, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
