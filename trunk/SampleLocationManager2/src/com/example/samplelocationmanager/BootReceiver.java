package com.example.samplelocationmanager;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // ... regiser...
		// addProximityAlert...
		
		ArrayList<Address> list = SaveAddress.getInstance().getList();
		LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		for (Address addr : list) {
			Intent i = new Intent(context, ProximityService.class);
			i.putExtra(ProximityService.PARAM_ADDRESS, addr);
			PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
			lm.addProximityAlert(addr.getLatitude(), addr.getLongitude(), 500, -1, pi);
		}
	}

}
