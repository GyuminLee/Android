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
				// ������ Package������ �ٸ� Task�� �����ϱ� ���ؼ��� Activity�� TaskAffinity�� �ٸ� ������ ������ �־�� ��.
				// defualt TaskAffinity�� �������� �ʴ� ��� app�� package������ ������.
				// TaskAffinity�� �̿��Ͽ� �ٸ� App�� Task�� ���ϵ��� �� ���� ����.
				// NEW_TASK�Ӽ��� �ָ� TaskAffinity�� ���� Task�� ���ϰ� ��.
				// �Ǵ� FLAG_ACTIVITY_MULTIPLE_TASK�� ���� FALG_ACTIVITY_NEW_TASK�� ����ϸ� ���� TASK�� �������.
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
				// Root Activity���� alwaysRetainTaskState�� �����Ǹ� 30���� ������ Task�� �������� �ʴ´�.
				// Root Activity�� alwaysRetainTaskState�� �����Ǿ� ���� ������ 30���� ������ Root Activity�� ������ 
				// ������ Activity�� �ڵ����� �����ȴ�.
				// �ݴ�� Root Activity�� clearTaskOnLaunch�� �����Ǹ� Background�� �������ٰ� Home���� �ٽ� Launching�ϸ�
				// Root Activity�� �����.
				// finishOnTaskLaunch�� Root Activity�� �ƴ� Activity�� �����Ͽ� Home���� ����� �� �ش� �Ӽ��� ������
				// Activity�� �����Ѵ�.
				// finishOnCloseSystemDialogs �Ӽ��� �����Ǹ�, System Dialog�� �� ���� Dialog�� ����� �� Activity�� ���� ����ǵ��� ó���Ѵ�.
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
						// ���� �������� Task�� Background�� ������.
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
    	// activity�� ó���ϱ� ���� ó���ؾ� �ϴ� �κ��� ���⼭ ó����.
    	return super.dispatchKeyEvent(event);
    }
    
    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
    	// TODO Auto-generated method stub
    	// activity�� ó���ϱ� ���� ó���ؾ� �ϴ� �κ��� ���⼭ ó����.
    	return super.dispatchPopulateAccessibilityEvent(event);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	// TODO Auto-generated method stub
    	// activity�� ó���ϱ� ���� ó���ؾ� �ϴ� �κ��� ���⼭ ó����.
    	return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean dispatchTrackballEvent(MotionEvent ev) {
    	// TODO Auto-generated method stub
    	// activity�� ó���ϱ� ���� ó���ؾ� �ϴ� �κ��� ���⼭ ó����.
    	return super.dispatchTrackballEvent(ev);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#finishActivityFromChild(android.app.Activity, int)
     * ActivityGroup�� ����ϴ� ��� finishActivity�� child Activity�� �����Ϸ��� �� ��� ȣ���.
     */
    @Override
    public void finishActivityFromChild(Activity child, int requestCode) {
    	// TODO Auto-generated method stub
    	super.finishActivityFromChild(child, requestCode);
    	Toast.makeText(this, "finishActivityFromChild : " + requestCode, Toast.LENGTH_SHORT).show();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#finishFromChild(android.app.Activity)
     * ActivityGroup�� ����ϴ� ��� finish�� child Activity�� ����� ��� ȣ���.
     */
    @Override
    public void finishFromChild(Activity child) {
    	// TODO Auto-generated method stub
    	super.finishFromChild(child);
    	Toast.makeText(this, "finishFromChild", Toast.LENGTH_SHORT).show();
    }
    
    
}