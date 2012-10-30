package org.tacademy.network.rss.board;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.DialogManager;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BoardList extends ParentActivity {

	ListView list;
	TextView text;
	ImageView bigImageView;
	public static final int BOARD_ADD_REQUEST = 1;
	public static final int BOARD_SHOW_REQUEST =2;
	public static final String BOARD_ITEM_FIELD = "boarditem";
	private BoardListAdapter mAdapter;
	private static final int DATA_SHOW_COUNT = 10;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.board_list);
	    
	    text = (TextView)findViewById(R.id.title);
	    list = (ListView)findViewById(R.id.boardlist);
	    bigImageView = (ImageView)findViewById(R.id.bigimage);
	    bigImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bigImageView.setVisibility(View.GONE);
			}
		});
	    Button btn = (Button)findViewById(R.id.add);
	
	    btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(BoardList.this,BoardAdd.class);
				startActivityForResult(i,BOARD_ADD_REQUEST);
			}
		});
	    
	    btn = (Button)findViewById(R.id.qrmake);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),MakeQRCodeActivity.class);
				startActivity(i);				
			}
		});
	    
	    btn = (Button)findViewById(R.id.qrcode);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ShowQRActivity.class);
				startActivity(i);
			}
		});
	    mAdapter = new BoardListAdapter(this);
	    mAdapter.setOnBoardItemImageClickListener(new BoardListAdapter.OnBoardItemImageClickListener() {
			
			@Override
			public void onBoardItemImageClick(String imageUrl) {
				// TODO Auto-generated method stub
				showImage(imageUrl);
			}
		});
	    list.setAdapter(mAdapter);
	    // TODO Auto-generated method stub
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				BoardItemData item = (BoardItemData)mAdapter.getItem(position);
				Intent i = new Intent(BoardList.this,BoardShow.class);
				i.putExtra(BoardShow.BOARD_ITEM_FIELD, item);
				startActivityForResult(i,BOARD_SHOW_REQUEST);
			}
		});
	    requestListData();
	}

	protected void showImage(String imageUrl) {
		// TODO Auto-generated method stub
		if (imageUrl != null) {
			ImageRequest request = new ImageRequest(imageUrl+"&width=320&height=480");
			request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				@Override
				public void onDownloadCompleted(int result, NetworkRequest request) {
					// TODO Auto-generated method stub
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						ImageRequest ir = (ImageRequest)request;
						Bitmap bm = ir.getBitmap();
						if (bm != null) {
							bigImageView.setImageBitmap(bm);
							bigImageView.setVisibility(View.VISIBLE);
						}
					}
				}
			});
			ImageManager.getInstance().enqueue(request);
		}
	}

	public void requestListData() {
		mAdapter.clearAll();
		requestListData(0,DATA_SHOW_COUNT);
	}
	public void requestListData(int start,int count) {
	    BoardListRequest request = new BoardListRequest(start,count);
	    request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			public void onDownloadCompleted(int result, NetworkRequest request) {
				//DialogManager.getInstance().getLastDialog().dismiss();
				dismissProgress();
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					BoardItems items = (BoardItems)request.getResult();
					if (items.result.equalsIgnoreCase("Success")) {
						mAdapter.addItem(items.items);
						mAdapter.setTotalCount(items.total);
						Toast.makeText(BoardList.this, "Board List Ok", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(BoardList.this, "Board List Server Error", Toast.LENGTH_SHORT).show();
					}
				} else {
					// fail Ã³¸®
					Toast.makeText(BoardList.this, "Board List Error", Toast.LENGTH_SHORT).show();
				}
			}
		});
	    
	    DownloadThread th = new DownloadThread(new Handler(),request);
	    th.start();
	    showProgress();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == BOARD_ADD_REQUEST) {
			if (resultCode == RESULT_OK) {
				int code = data.getIntExtra(BoardAdd.RESULT_VALUE, BoardAdd.RESULT_UPDATE_FAIL);
				if (code == BoardAdd.RESULT_UPDATE_SUCCESS) {
					requestListData();
				}
			}
		} else if (requestCode == BOARD_SHOW_REQUEST) {
			if (resultCode == RESULT_OK) {
				BoardItemData item = data.getParcelableExtra(BOARD_ITEM_FIELD);
				mAdapter.setItem(item);
			}
		}
	}

	
}
