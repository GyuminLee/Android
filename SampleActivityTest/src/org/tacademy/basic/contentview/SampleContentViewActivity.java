package org.tacademy.basic.contentview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

public class SampleContentViewActivity extends Activity {
    /** Called when the activity is first created. */
	public static final int REQUEST_SERVICE = 1;
	public static final int REQUEST_ACTIVITY = 2;
	public static final int REQUEST_RESULT_FORWARD = 3;
	public static final int DIALOG_ID_ONE = 1;
	public int mCount = 0;
	
	Dialog mDialog;
	Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.contentview);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, AddContentViewActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.dialog);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putString("title", "test title");
				mCount++;
				b.putString("content", "count : " + mCount);
				showDialog(DIALOG_ID_ONE,b);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						dismissDialog(DIALOG_ID_ONE);
					}
					
				}, 2000);
			}
		});

        btn = (Button)findViewById(R.id.transition);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, TransitionActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
        
        btn = (Button)findViewById(R.id.pendingresult);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("value", "activity");
				PendingIntent pi = createPendingResult(REQUEST_SERVICE, i, PendingIntent.FLAG_ONE_SHOT);
				Intent si = new Intent(SampleContentViewActivity.this,MyService.class);
				si.putExtra("resultpi", pi);
				startService(si);
			}
		});
        
        btn = (Button)findViewById(R.id.resultforward);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, ForwardResultAActivity.class);
				startActivityForResult(i,REQUEST_RESULT_FORWARD);
			}
		});
        
        btn = (Button)findViewById(R.id.invokecurrenttask);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SampleContentViewActivity.this, "SampleContentViewActivity Task Id : " + getTaskId() + "\nIsTaskRoot : " + isTaskRoot(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(SampleContentViewActivity.this,FinishTestActivity.class);
				startActivityForResult(i,REQUEST_ACTIVITY);
			}
		});
        
        btn = (Button)findViewById(R.id.invokenewtask);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SampleContentViewActivity.this, "SampleContentViewActivity Task Id : " + getTaskId(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(SampleContentViewActivity.this,FinishTestActivity.class);
				// 동일한 Package내에서 다른 Task를 생성하기 위해서는 Activity의 TaskAffinity를 다른 값으로 지정해 주어야 함.
				// defualt TaskAffinity는 지정하지 않는 경우 app의 package명으로 지정됨.
				// TaskAffinity를 이용하여 다른 App의 Task에 속하도록 할 수도 있음.
				// NEW_TASK속성을 주면 TaskAffinity가 같은 Task에 속하게 됨.
				// 또는 FLAG_ACTIVITY_MULTIPLE_TASK와 같이 FALG_ACTIVITY_NEW_TASK를 사용하면 별도 TASK가 만들어짐.
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.standard);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, LaunchModeStandardActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.singletop);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, LaunchModeSingleTopActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.singletask);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, LaunchModeSingleTask.class);
				startActivity(i);
			}
		});
		
        btn = (Button)findViewById(R.id.singleinstance);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, LaunchModeSingleInstance.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.intentflag);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, ActivityA.class);
				startActivity(i);
				// Root Activity에서 alwaysRetainTaskState가 설정되면 30분이 지나도 Task가 정리되지 않는다.
				// Root Activity에 alwaysRetainTaskState가 설정되어 있지 않으면 30분이 지나면 Root Activity를 제외한 
				// 나머지 Activity는 자동으로 정리된다.
				// 반대로 Root Activity에 clearTaskOnLaunch가 설정되면 Background로 내려갔다가 Home에서 다시 Launching하면
				// Root Activity가 실행됨.
				// finishOnTaskLaunch는 Root Activity가 아닌 Activity에 설정하여 Home에서 실행될 때 해당 속성이 설정된
				// Activity를 종료한다.
				// finishOnCloseSystemDialogs 속성이 설정되면, System Dialog가 뜬 다음 Dialog가 사라질 때 Activity도 같이 종료되도록 처리한다.
			}
		});
        
        
        btn = (Button)findViewById(R.id.tasktoback);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, ActivityA.class);
				startActivity(i);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// 현재 실행중인 Task를 Background로 보낸다.
						moveTaskToBack(true);
					}
					
				}, 5000);
			}
		});
        
        btn = (Button)findViewById(R.id.runonuithread);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this,RunOnUIThreadActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.onmethod);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this, OnMethodActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.keymode);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this,DefaultKeyActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.windowfeature);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleContentViewActivity.this,WindowFeatureActivity.class);
				startActivity(i);
			}
		});
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
    	// TODO Auto-generated method stub
    	switch(id) {
    		case DIALOG_ID_ONE :
    			AlertDialog alert = (AlertDialog)dialog;
    			alert.setTitle(args.getString("title"));
    			alert.setMessage(args.getString("content"));
    		break;
    	}
    	super.onPrepareDialog(id, dialog, args);
    }
    
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
    	// TODO Auto-generated method stub
    	switch(id) {
    		case DIALOG_ID_ONE :
    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
    			builder.setTitle(args.getString("title"));
    			builder.setMessage(args.getString("content"));
    			return builder.create();
    		default :
    			break;
    	}
    	return null;
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub    	
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (requestCode == REQUEST_SERVICE) {
    		if (resultCode == Activity.RESULT_OK) {
    			String value = data.getStringExtra("value");
    			String value2 = data.getStringExtra("value2");
    			Toast.makeText(this, "value : " + value + "value2 : " + value2, Toast.LENGTH_SHORT).show();
    		}
    	} else if (requestCode == REQUEST_ACTIVITY) {
    		if (resultCode == Activity.RESULT_OK) {
    			// ...
    		}
    	} else if (requestCode == REQUEST_RESULT_FORWARD) {
    		if (resultCode == Activity.RESULT_OK) {
    			String msg = data.getStringExtra("message");
    			Toast.makeText(this, "message : " + msg, Toast.LENGTH_SHORT).show();
    		}
    	}
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	// TODO Auto-generated method stub
    	// activity가 처리하기 전에 처리해야 하는 부분을 여기서 처리함.
    	return super.dispatchKeyEvent(event);
    }
    
    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
    	// TODO Auto-generated method stub
    	// activity가 처리하기 전에 처리해야 하는 부분을 여기서 처리함.
    	return super.dispatchPopulateAccessibilityEvent(event);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	// TODO Auto-generated method stub
    	// activity가 처리하기 전에 처리해야 하는 부분을 여기서 처리함.
    	return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean dispatchTrackballEvent(MotionEvent ev) {
    	// TODO Auto-generated method stub
    	// activity가 처리하기 전에 처리해야 하는 부분을 여기서 처리함.
    	return super.dispatchTrackballEvent(ev);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#finishActivityFromChild(android.app.Activity, int)
     * ActivityGroup을 사용하는 경우 finishActivity로 child Activity를 종료하려고 할 경우 호출됨.
     */
    @Override
    public void finishActivityFromChild(Activity child, int requestCode) {
    	// TODO Auto-generated method stub
    	super.finishActivityFromChild(child, requestCode);
    	Toast.makeText(this, "finishActivityFromChild : " + requestCode, Toast.LENGTH_SHORT).show();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#finishFromChild(android.app.Activity)
     * ActivityGroup을 사용하는 경우 finish로 child Activity가 종료될 경우 호출됨.
     */
    @Override
    public void finishFromChild(Activity child) {
    	// TODO Auto-generated method stub
    	super.finishFromChild(child);
    	Toast.makeText(this, "finishFromChild", Toast.LENGTH_SHORT).show();
    }
    
    
}