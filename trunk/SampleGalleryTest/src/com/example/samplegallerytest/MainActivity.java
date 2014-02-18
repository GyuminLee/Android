package com.example.samplegallerytest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Gallery;

import com.example.samplegallerytest.DropTargetImageView.OnItemDropListener;

public class MainActivity extends Activity {

	Gallery gallery;
	MyGalleryAdapter mAdapter;
	MyData[] array = new MyData[] { new MyData(R.drawable.sample_0,"sample 0"),
			new MyData(R.drawable.sample_1,"sample 1"),
			new MyData(R.drawable.sample_2,"sample 2"),
			new MyData(R.drawable.sample_3,"sample 3"),
			new MyData(R.drawable.sample_4,"sample 4"),
			new MyData(R.drawable.sample_5,"sample 5"),
			new MyData(R.drawable.sample_6,"sample 6"),
			new MyData(R.drawable.sample_7,"sample 7")
	};
	
	DragController mController;
	DragSourceLayout mDragSource;
	DropTargetImageView targetView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mController = new DragController(this);
		mDragSource = (DragSourceLayout)findViewById(R.id.dragSource);
		targetView = (DropTargetImageView)findViewById(R.id.dropTarget);
		targetView.setOnItemDropListener(new OnItemDropListener() {
			
			@Override
			public void onItemDrop(MyData data) {
				targetView.setImageResource(data.imageId);
			}
		});
		mDragSource.setDragController(mController);
		mController.addDropTarget(targetView);
		gallery = (Gallery)findViewById(R.id.gallery1);
		gallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				mController.startDrag(view, mDragSource, mAdapter.getItem(position), DragController.DRAG_ACTION_COPY);
				return false;
			}
		});
		mAdapter = new MyGalleryAdapter(this, array);
		
		gallery.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
