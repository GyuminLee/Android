package org.tacademy.basic.resource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SampleResourceTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String format = getString(R.string.format_string);
				String message = String.format(format, "arg1","arg2","arg3");
				Toast.makeText(SampleResourceTestActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
			}
		});
        
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText et = (EditText)findViewById(R.id.editText1);
				int value = Integer.parseInt(et.getText().toString());
				RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup1);
				int select = group.getCheckedRadioButtonId();
				int unit = -1;
				switch (select) {
					case R.id.radio0 :
						unit = TypedValue.COMPLEX_UNIT_DIP;
						break;
					case R.id.radio1 :
						unit = TypedValue.COMPLEX_UNIT_SP;
						break;
					case R.id.radio2 :
						unit = TypedValue.COMPLEX_UNIT_PT;
						break;
					case R.id.radio3 :
						unit = TypedValue.COMPLEX_UNIT_MM;
						break;
					case R.id.radio4 :
						unit = TypedValue.COMPLEX_UNIT_IN;
						break;
					default :
						unit = -1;
						break;
				}
				float changedValue = TypedValue.applyDimension(unit, (float)value, getResources().getDisplayMetrics());
				Toast.makeText(SampleResourceTestActivity.this, "change value : " + changedValue, Toast.LENGTH_SHORT).show();
			}
		});
        
        btn = (Button)findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleResourceTestActivity.this, ShowFrameActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleResourceTestActivity.this, ShowLinearActivity.class);
				startActivity(i);
			}
		});
        
        btn = (Button)findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SampleResourceTestActivity.this, ShowLevelDrawable.class);
				startActivity(i);
			}
		});
    }
}