package org.tacademy.basic.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class SimpleNotificationActivity extends Activity {
	Button btnGo;
	Button btnStop;
	int notificationid = 1;
	NotificationManager notificationManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnGo = (Button)findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
				//vibrator.vibrate(500);
				//vibrator.vibrate(pattern, repeat)
				
				String serName = Context.NOTIFICATION_SERVICE;
				notificationManager = (NotificationManager)getSystemService(serName);
				
				int icon = R.drawable.star_big_on;
				String tickerText = "1.My Notification TickerText";
				Long when = System.currentTimeMillis();
				Notification notification = new Notification(icon,tickerText,when);
				
				String extendedTitle = "2.My Extended Title";
				String extendedText = "3. This is an extended";
				
				Intent intent = new Intent(getApplicationContext(),NotifyWaitress.class);
				
				PendingIntent launchIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
				notification.setLatestEventInfo(getApplicationContext(), extendedTitle, extendedText, launchIntent);

				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				
				//notification.vibrate = new long[] {200,200,600,600};
				//notification.flags |= Notification.FLAG_INSISTENT;
				
				//notification.flags |= Notification.FLAG_SHOW_LIGHTS;
				//notification.ledARGB = Color.GREEN;
				
				//notification.sound = Uri.parse("file://system/media/audio/ringtones/VeryAlarmed.ogg");
				
				notificationid++;
				
				notificationManager.notify(notificationid,notification);
				
				
			}
		});
        
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notificationid = 1;
				notificationManager.cancel(notificationid);
			}
		});
    }
}