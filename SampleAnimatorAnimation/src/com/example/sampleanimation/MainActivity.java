package com.example.sampleanimation;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	MyData[] mData = {
			new MyData("ValueAnimator", ValueAnimatorSample.class),
			new MyData("ObjectAnimator", ObjectAnimatorSample.class),
			new MyData("Background Thread Animator", BackgroundThreadSample.class),
			new MyData("CustomObject Animation", CustomObjectAnimationSample.class),
			new MyData("PropertyValuesHolder Animator",PropertyValuesHolderSample.class),
			new MyData("Keyframe Animator",KeyframeSample.class),
			new MyData("AnimatorSet",AnimatorSetSample.class),
			new MyData("Custom Evaluator", CustomEvaluatorSample.class),
			new MyData("Animator Loading", AnimatorLoadingSample.class),
			new MyData("LayoutTransition", LayoutTransitionSample.class),
			new MyData("BouncingBall Sample(Apis Demo)",BouncingBalls.class)
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView)findViewById(R.id.listView1);
        list.setAdapter(new ArrayAdapter<MyData>(
        		this,android.R.layout.simple_list_item_1,mData));
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent i = new Intent(MainActivity.this,mData[position].activity);
				startActivity(i);
			}
		});
        
        // default 10ms
        // ValueAnimator FrameDelay is static value
        // all animator apply this value
        ValueAnimator.setFrameDelay(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public class MyData {
    	String title;
    	Class activity;
    	public MyData(String title,Class activity) {
    		this.title = title;
    		this.activity = activity;
    	}
    	
    	@Override
    	public String toString() {
    		// TODO Auto-generated method stub
    		return title;
    	}
    }
}
