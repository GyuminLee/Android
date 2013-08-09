package com.example.samplegcmclient;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.rpc.client.HttpJsonRpcClientTransport;
import org.json.rpc.client.JsonRpcInvoker;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.shared.MyWebInterface;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(Config.SENDER_ID);
	}
	
	@Override
	protected void onError(Context arg0, String arg1) {
		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String receiveMessage = intent.getStringExtra(Config.PARAM_MESSAGE);
		
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("msg : " + receiveMessage);
		builder.setAutoCancel(true);
		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setContentTitle("GCM");
		builder.setContentText(receiveMessage);
		builder.setContentInfo("info");
		
		nm.notify(1, builder.build());
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		try {
			HttpJsonRpcClientTransport transport;
			transport = new HttpJsonRpcClientTransport(new URL(Config.URL_STRING));
			JsonRpcInvoker invoker = new JsonRpcInvoker();
			MyWebInterface inter = invoker.get(transport, "myweb", MyWebInterface.class);
			inter.register("dongja94", regId);
			GCMRegistrar.setRegisteredOnServer(context, true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		
	}

}
