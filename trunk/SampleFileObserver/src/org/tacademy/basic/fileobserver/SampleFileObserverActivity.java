package org.tacademy.basic.fileobserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SampleFileObserverActivity extends Activity {
    /** Called when the activity is first created. */
	
	public static final String MY_ROOT_NAME = "FileObserver/";
	
	public static final File MY_ROOT = new File(Environment.getExternalStorageDirectory(),MY_ROOT_NAME);
	
	public static final File MY_FILE = new File(MY_ROOT,"myfile.txt");
	private FileObserver fileObserver;
	
	Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (!MY_ROOT.exists()) {
        	MY_ROOT.mkdir();
        }
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					FileOutputStream fos = new FileOutputStream(MY_FILE);
					PrintWriter writer = new PrintWriter(fos);
					writer.println("test");
					writer.flush();
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        fileObserver = new MyFileObserver(MY_ROOT.getAbsolutePath());
    }
    
    
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	fileObserver.stopWatching();
		super.onPause();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
    	fileObserver.startWatching();
		super.onResume();
	}



	private class MyFileObserver extends FileObserver {

		public MyFileObserver(String path) {
			super(path);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onEvent(int event, String path) {
			// TODO Auto-generated method stub
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(SampleFileObserverActivity.this, "file change ...", Toast.LENGTH_SHORT).show();
				}
				
			});
		}
    	
		
    }
    
    
}