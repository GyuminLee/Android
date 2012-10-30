package org.tacademy.basic.graphics.region;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SampleRegionOpActivity extends Activity implements 
	View.OnLongClickListener, 
	DragNDropLayout.OnDragAndDropEventListener,
	View.OnClickListener {
    /** Called when the activity is first created. */
	DragNDropLayout dndLayout;
	ImageView imageView1;
	ImageView imageView2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dndLayout = (DragNDropLayout)findViewById(R.id.dndlayout);
        dndLayout.setOnDragAndDropEventListener(this);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView1.setOnLongClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView2.setOnLongClickListener(this);
        imageView2.setOnClickListener(this);
        
        
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dndLayout.addView(makeItemView(), getLayoutParams());
			}
		});
        
        btn = (Button)findViewById(R.id.zoomin);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dndLayout.setScale(1.2f);
			}
		});
        
        btn = (Button)findViewById(R.id.zoomout);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dndLayout.setScale(0.8f);
			}
		});
        
        btn = (Button)findViewById(R.id.resetItemScale);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dndLayout.resetScaleItem();
			}
		});
    }
    
    @Override
    public void onAttachedToWindow() {
    	// TODO Auto-generated method stub
    	super.onAttachedToWindow();
    }
    
    public ImageView makeImageView() {
    	ImageView imageView = null;
    	imageView = (ImageView)LayoutInflater.from(this).inflate(R.layout.image_view, null);
    	imageView.setOnLongClickListener(this);
    	return imageView;
    }
    
    public MyItemView makeItemView() {
    	MyItemView itemview = new MyItemView(this);
    	itemview.setOnButtonClickListener(new MyItemView.OnButtonClickListener() {
			
			public void onModifyClick(View view, String text) {
				// TODO Auto-generated method stub
				
				
			}
			
			public void onDeleteClick(View view, String text) {
				// TODO Auto-generated method stub
				dndLayout.removeView(view);
			}
		});
    	
    	itemview.setData("testtest");
    	itemview.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyItemView itemView = (MyItemView)v;
				itemView.setLayoutVisibility(View.VISIBLE);
			}
		});
    	
    	itemview.setOnLongClickListener(this);
    	return itemview;
    }
    
    public DragNDropLayout.LayoutParams getLayoutParams() {
    	int x = 0;
    	int y = 0;
    	return new DragNDropLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,x,y);
    }

	public boolean onLongClick(View view) {
		// TODO Auto-generated method stub
		dndLayout.startDrag(view, null);
		return true;
	}

	public void onDragStart(View v, Object info) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "drag start", Toast.LENGTH_SHORT).show();
		
	}

	public void onDragEnd(View v, Object info) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "drag end", Toast.LENGTH_SHORT).show();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "click image",Toast.LENGTH_SHORT).show();
		if (!dndLayout.isItemScaleMode()) {
			dndLayout.setScaleItem(v);
		} else {
			dndLayout.resetScaleItem();
		}
	}
	
}