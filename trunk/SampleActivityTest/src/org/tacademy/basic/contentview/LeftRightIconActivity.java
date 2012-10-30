package org.tacademy.basic.contentview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LeftRightIconActivity extends Activity {

	public final static int REQUEST_CODE_GET_CONTENT = 1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    requestWindowFeature(Window.FEATURE_LEFT_ICON);
	    requestWindowFeature(Window.FEATURE_RIGHT_ICON);
	    setContentView(R.layout.left_right_icon);
	    setFeatureDrawable(Window.FEATURE_LEFT_ICON, getResources().getDrawable(R.drawable.ic_launcher));
	    setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON, R.drawable.ic_launcher);
	    setFeatureDrawableAlpha(Window.FEATURE_LEFT_ICON, 0x80);
	    
	    Button btn = (Button)findViewById(R.id.drawableuri);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/jpeg");
				startActivityForResult(i,REQUEST_CODE_GET_CONTENT);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE_GET_CONTENT) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				setFeatureDrawableUri(Window.FEATURE_RIGHT_ICON, uri);
			}
		}
	}

}
