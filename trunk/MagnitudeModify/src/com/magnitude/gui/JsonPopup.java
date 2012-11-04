package com.magnitude.gui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.magnitude.app.R;
import com.magnitude.libs.Popup;
import com.magnitude.tools.JSONHelper;

public class JsonPopup extends Popup{
	private String url;
	private String imageURL;
	private boolean alreadyUpdate;
	final Handler uiThreadCallback;
	private LoadingPopup pd;
	
	public JsonPopup(Activity activity,View baseView,int layout,int height,int width) {
		super(activity,baseView,layout,height,width);
		alreadyUpdate = false;
		uiThreadCallback = new Handler();
	}	
	
	public void show()	{
		show(0,0);
	}
	
	public void show(int x, int y) {
		setX(x);
		setY(y);
		if(!alreadyUpdate) {
			popupUpdate();
		}
		else showPopUp();
	}
	
	private void showPopUp(){
		getPop().showAtLocation(getBaseView(), Gravity.LEFT | Gravity.TOP ,getX(), getY());
	}
	
	final Runnable runInUIThread = new Runnable() {
		public void run() {
			pd.dismiss();
			showPopUp();
		}
	};
	
	public void popupUpdate() {
		pd = new LoadingPopup((Activity)getCtx(),getBaseView(),R.layout.loading,getPop().getHeight(),getPop().getWidth());
		pd.show(getX(), getY());
		new Thread() {
			public void run() {
				getFromJsonUrl(url);
				uiThreadCallback.post(runInUIThread);
			}
		}.start();
	}
	
	public void getFromJsonUrl(String url) {
		JSONObject jo = JSONHelper.getJSONObject(url);
		if(jo != null) {
			try {
				if(jo.has("name")) setTitle(jo.getString("name"));
				if(jo.has("description")) setDescription(jo.getString("description"));
				if(jo.has("image")) setImageURL(jo.getString("image"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			alreadyUpdate = true;
		}
		else {
			setDescription("Error while downloading information.");
			setDistance("");
			alreadyUpdate = false;
		}
	}
	
	
	private void setTitle(String title) {
		TextView text = (TextView) getLayout().findViewById(R.id.title);
		text.setText(title);
	}
	
	public void setDistance(String distance) {
		TextView text = (TextView) getLayout().findViewById(R.id.distance);
		text.setText(distance);
	}
	
	private void setDescription(String description) {
		TextView text = (TextView) getLayout().findViewById(R.id.description);
		text.setText(description);
	}

	private void setImageURL(String string) {	
		this.imageURL = string;
		ImageView picture = (ImageView) getLayout().findViewById(R.id.image);
		picture.setImageBitmap(telechargerImage(imageURL));
	}
	
	private Bitmap telechargerImage(String url) {
		Bitmap bm = null;
        try{
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch(IOException e){
            Log.e("Erreur","Erreur IO");
        }
        return bm;
		
	}
	
	
	public void setUrl(String url) {
		this.url = url;
		this.alreadyUpdate = false;
	}
}