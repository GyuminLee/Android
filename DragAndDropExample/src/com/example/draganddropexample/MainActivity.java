package com.example.draganddropexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.begentgroup.draganddrop.DragController;
import com.begentgroup.draganddrop.view.DragAndDropLayout;
import com.begentgroup.draganddrop.view.IconGridView;
import com.begentgroup.draganddrop.view.IconGridView.OnDragAndDropListener;
import com.begentgroup.draganddrop.view.IconGridView.OnItemClickListener;
import com.begentgroup.draganddrop.view.IconGridView.OnNotifyNextListener;

public class MainActivity extends Activity {

	ImageView imageView;
	IconGridView gridView;
	DragController mController;
	DragAndDropLayout source;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mController = new DragController(this);
		imageView = (ImageView)findViewById(R.id.imageView1);
		source = (DragAndDropLayout)findViewById(R.id.source);
		source.setDragController(mController);
		gridView = (IconGridView)findViewById(R.id.iconGridView);
		gridView.setDragController(mController);
		gridView.setDragSource(source);
		MyAdapter adapter = new MyAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(ViewGroup parent, View view, int position, long id) {
				
			}
		});
		gridView.setOnDragAndDropListener(new OnDragAndDropListener() {
			
			@Override
			public void onStartDrag(ViewGroup parent, View childView, int position,
					Object item) {
				// drag start....
				// item is adapter item
			}

			@Override
			public void onDropComplete(ViewGroup arg0, View arg1, int arg2,
					Object arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
		gridView.setOnNotifyNextListener(new OnNotifyNextListener() {
			
			@Override
			public void onNotifyNext(View v, boolean isNext) {
				if (isNext) {
					// ... next...
				} else {
					// ... prev...
				}
				
			}
		});
		imageView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				mController.startDrag(v, source, v, DragController.DRAG_ACTION_COPY);
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
