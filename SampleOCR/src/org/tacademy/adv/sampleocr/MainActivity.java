package org.tacademy.adv.sampleocr;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends Activity {

	TextView tv;
	ImageView iv;
	TessBaseAPI baseApi;
	private static final String mDataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SampleOCR/";
	private static final String mLanguage = "kor";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textView1);
        iv = (ImageView)findViewById(R.id.imageView1);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				startActivityForResult(i,0);
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BitmapDrawable bmDrawable = (BitmapDrawable)iv.getDrawable();
				Bitmap bm = bmDrawable.getBitmap();
				baseApi.setImage(bm);
				String str = baseApi.getUTF8Text();
				tv.setText(str);
			}
		});
        baseApi = new TessBaseAPI();
        baseApi.init(mDataPath, mLanguage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == 0) {
    		if (resultCode == RESULT_OK) {
    			Uri uri = data.getData();
    			try {
					Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
					Bitmap bm1 = Bitmap.createScaledBitmap(bm, 480, 320, false);
					bm.recycle();
					iv.setImageBitmap(bm1);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
