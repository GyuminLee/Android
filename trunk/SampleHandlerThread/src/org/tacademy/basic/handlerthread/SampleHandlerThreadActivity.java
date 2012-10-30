package org.tacademy.basic.handlerthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SampleHandlerThreadActivity extends Activity {
    /** Called when the activity is first created. */
	private static final String TAG = SampleHandlerThreadActivity.class.getSimpleName();
	
	Handler mHandler = new Handler();
	Handler mSubThreadHandler;
	HandlerThread mHandlerThread;
	private static final String THREAD_NAME = "looperThread";
	private static final int THREAD_PRIORITY = android.os.Process.THREAD_PRIORITY_BACKGROUND;
	
	private static final int MSG_ONE = 1;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mHandlerThread = new HandlerThread(THREAD_NAME, THREAD_PRIORITY);
        mHandlerThread.start();
        mSubThreadHandler = new Handler(mHandlerThread.getLooper()) {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what) {
				case MSG_ONE :
					Log.i(TAG,"sub thread msg one");
					break;
				default :
					break;
				}
			}
        	
        };
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message msg = mSubThreadHandler.obtainMessage();
				msg.what = MSG_ONE;
				mSubThreadHandler.sendMessage(msg);
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSubThreadHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i(TAG,"sub thread post run");
					}
					
				});
			}
		});
    }
	
	
}