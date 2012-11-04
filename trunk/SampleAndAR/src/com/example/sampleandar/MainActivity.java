package com.example.sampleandar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.view.Menu;
import android.view.SurfaceHolder;

import com.example.sampleandar.graphics.LightingRenderer;
import com.example.sampleandar.graphics.Model3D;
import com.example.sampleandar.models.Model;
import com.example.sampleandar.parser.ObjParser;
import com.example.sampleandar.parser.ParseException;
import com.example.sampleandar.util.AssetsFileUtil;
import com.example.sampleandar.util.BaseFileUtil;

import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.Config;
import edu.dhbw.andar.exceptions.AndARException;

public class MainActivity extends AndARActivity implements SurfaceHolder.Callback {

	private Model model;
	private Model3D model3d;
	private ProgressDialog waitDialog;
	private Resources res;
	
	ARToolkit artoolkit;

	public MainActivity() {
		super(false);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNonARRenderer(new LightingRenderer());
		res=getResources();
		artoolkit = getArtoolkit();		
		getSurfaceView().getHolder().addCallback(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	super.surfaceCreated(holder);
    	//load the model
    	//this is done here, to assure the surface was already created, so that the preview can be started
    	//after loading the model
    	if(model == null) {
			waitDialog = ProgressDialog.show(this, "", 
	                "loading...", true);
			waitDialog.show();
			new ModelLoader().execute();
		}
    }
	
	private class ModelLoader extends AsyncTask<Void, Void, Void> {
		
		
    	@Override
    	protected Void doInBackground(Void... params) {
    		
			Intent intent = getIntent();
			Bundle data = intent.getExtras();
			String modelFileName = "android.obj";
			BaseFileUtil fileUtil= null;
			File modelFile=null;
			fileUtil = new AssetsFileUtil(getResources().getAssets());
			fileUtil.setBaseFolder("models/");
			
			//read the model file:						
			ObjParser parser = new ObjParser(fileUtil);
			try {
				if(fileUtil != null) {
					BufferedReader fileReader = fileUtil.getReaderFromName(modelFileName);
					if(fileReader != null) {
						model = parser.parse("Model", fileReader);
						model3d = new Model3D(model);
					}
				}
				if(Config.DEBUG)
					Debug.stopMethodTracing();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		return null;
    	}

    	@Override
    	protected void onPostExecute(Void result) {
    		super.onPostExecute(result);
    		waitDialog.dismiss();
    		
    		//register model
    		try {
    			if(model3d!=null)
    				artoolkit.registerARObject(model3d);
			} catch (AndARException e) {
				e.printStackTrace();
			}
			startPreview();
    	}
    }
	
}
