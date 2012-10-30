package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.upload.UploadResult;
import org.tacademy.network.rss.util.LocalBinder;
import org.tacademy.network.rss.util.PropertyManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

public class C2DMService extends Service {

	public static final int SERVICE_NONE = 0;
	public static final int SERVICE_TYPE_REGISTRATION = 1;
	public static final int SERVICE_TYPE_RECEIVE_DATA = 2;

	public static final String SERVICE_TYPE_KEY = "serviceType";
	public static final String REGISTRATION_ID = "registrationId";
	public static final String MESSAGE_SENDER = "sender";
	public static final String MESSAGE_TYPE = "type";
	public static final String MESSAGE_NAME = "name";
	public static final String MESSAGE_TEXT = "message";
	
	Handler mHandler = new Handler();
		
	public interface OnMessageReceivedListener {
		public void onChatMessageReceived(int sender,String name, String message);
		public void onLocationMessageReceived(int sender,String name, double latitude, double longitude);
	}
	
	OnMessageReceivedListener mListener;
	
	public void setOnMessageListener(OnMessageReceivedListener listener) {
		mListener = listener;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder<C2DMService>(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int type = intent.getIntExtra(SERVICE_TYPE_KEY, SERVICE_NONE);
		if (type == SERVICE_TYPE_REGISTRATION) {
			
			String registrationId = intent.getStringExtra(REGISTRATION_ID);
			String accountId = PropertyManager.getInstance().getUserAccount();
			if (!accountId.equals("")) {
				C2DMRegistrationRequest request = new C2DMRegistrationRequest(accountId,registrationId);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							UploadResult ur = (UploadResult) request.getResult();
							if (ur.result == UploadResult.RESULT_SUCCESS) {
								// registration success
							} else {
								// registtration fail
							}
						} else {
							// network fail
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler,request);
				th.start();
			}
		} else if (type == SERVICE_TYPE_RECEIVE_DATA) {
			int sender = intent.getIntExtra(MESSAGE_SENDER, -1);
			String messageType = intent.getStringExtra(MESSAGE_TYPE);
			String senderName = intent.getStringExtra(MESSAGE_NAME);
			String message = intent.getStringExtra(MESSAGE_TEXT);
			
			if (messageType.equals(C2DMMessageSendRequest.TYPE_CHATTING)) {
				processChatting(sender,senderName,messageType,message);
			} else if (messageType.equals(C2DMMessageSendRequest.TYPE_REQUEST_POSITION)) {
				processRequestPosition(sender,senderName,messageType,message);
			} else if (messageType.equals(C2DMMessageSendRequest.TYPE_REPORT_POSITION)) {
				processReportPosition(sender,senderName,messageType,message);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	

	private void processReportPosition(int sender, String senderName,
			String messageType, String message) {
		// TODO Auto-generated method stub
		String[] tokens = message.split(",");
		double lat=0, lng=0;

		for (int i = 0; i < tokens.length ; i++) {
			String token = tokens[i];
			if (token.startsWith("LAT:")) {
				lat = Double.parseDouble(token.substring(4));
			} else if (token.startsWith("LNG:")) {
				lng = Double.parseDouble(token.substring(4));
			}
		}
		
		if (mListener != null) {
			mListener.onLocationMessageReceived(sender, senderName, lat, lng);
		}
		
	}

	private void processRequestPosition(final int sender, final String senderName,
			final String messageType, final String message) {
		// TODO Auto-generated method stub
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		final Criteria criteria = new Criteria();
		// 정확도
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// 전력사용 정도
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 고도 정보
		criteria.setAltitudeRequired(false);
		// 방위 정보
		criteria.setBearingRequired(false);
		// 속도 정보
		criteria.setSpeedRequired(false);
		// 비용
		criteria.setCostAllowed(true);
		
		lm.requestSingleUpdate(criteria , new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
				sb.append("LAT:"+location.getLatitude()).append(",");
				sb.append("LNG:"+location.getLongitude());
				C2DMMessageSendRequest request = new C2DMMessageSendRequest(sender,C2DMMessageSendRequest.TYPE_REPORT_POSITION,sb.toString());
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							
						} else {
							
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler,request);
				th.start();
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
		}, mHandler.getLooper());
		
	}

	private void processChatting(int sender,String senderName, String messageType, String message) {
		if (mListener != null) {
			mListener.onChatMessageReceived(sender, senderName, message);
		} else {
			NotificationManager nm = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.icon,senderName + " said " + message,System.currentTimeMillis());
			Intent i = new Intent(this,MessageShowActivity.class);
			i.putExtra(MESSAGE_SENDER, sender);
			i.putExtra(MESSAGE_NAME, senderName);
			i.putExtra(MESSAGE_TEXT, message);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
			notification.setLatestEventInfo(this, senderName, message, pi);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			
			notification.vibrate = new long[] {200,200,600,600};
			notification.flags |= Notification.FLAG_INSISTENT;
			
			notification.flags |= Notification.FLAG_SHOW_LIGHTS;
			notification.ledARGB = Color.GREEN;
			
			nm.notify(sender, notification);				
		}		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	
}
