package org.tacademy.network.rss.board;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.DownloadThread;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.upload.UploadResult;
import org.tacademy.network.rss.util.DialogManager;
import org.tacademy.network.rss.util.ImageManager;
import org.tacademy.network.rss.util.ImageRequest;
import org.tacademy.network.rss.util.PropertyManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BoardShow extends ParentActivity implements ReplyDialog.OnReplyDialogButtonClickListener {

	/** Called when the activity is first created. */
	public static final String BOARD_ITEM_FIELD = "item";
	public static final String RESULT_VALUE = "updateResult";
	public static final String REPLY_ITEM_FIELD = "replyitem";
	public static final int REPLY_DIALOG_ID = DialogManager.USER_DIALOG_ID + 1;
	public static final int RESULT_RECORD_NOT_CHANGED = 1;
	public static final int RESULT_RECORD_CHANGED = 2;
	
	public static final int REQUEST_BOARD_MODIFY = 1;
	
	public static final String imageSizeParam = "&width=80&height=80";
	ImageView userImageView;
	TextView boardTitleView;
	ImageView boardImageView;
	TextView boardContentView;

	BoardItemData mItem;
	
	Button modifyButton;
	Button deleteButton;
	Button replyButton;
	ListView replyList;
	ReplyListAdapter mAdapter;
	
	Handler mHandler = new Handler();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.board_show);
	    userImageView = (ImageView)findViewById(R.id.userImage);
	    boardTitleView = (TextView)findViewById(R.id.boardTitle);
	    boardImageView = (ImageView)findViewById(R.id.boardImage);
	    boardContentView = (TextView)findViewById(R.id.boardcontent);
	    modifyButton = (Button)findViewById(R.id.modify);
	    deleteButton = (Button)findViewById(R.id.delete);
	    replyButton = (Button)findViewById(R.id.reply);
	    replyList = (ListView)findViewById(R.id.replylist);
	    mAdapter = new ReplyListAdapter(this);
	    mAdapter.setOnReplyItemModifyClickListener(new ReplyListAdapter.OnReplyItemModifyClickListener() {
			
			@Override
			public void onReplyItemModifyClick(ReplyItemData data) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putParcelable(REPLY_ITEM_FIELD, data);
				showDialog(REPLY_DIALOG_ID,b);
			}
			
			@Override
			public void onReplyItemDeleteClick(final ReplyItemData data) {
				// TODO Auto-generated method stub
				ReplyDeleteRequest request = new ReplyDeleteRequest(data);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						//DialogManager.getInstance().getLastDialog().dismiss();
						dismissProgress();
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							UploadResult ur = (UploadResult)request.getResult();
							if (ur.result == UploadResult.RESULT_SUCCESS) {
								mAdapter.delete(data);
							} else {
								Toast.makeText(BoardShow.this, "Delete Server Fail", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(BoardShow.this, "Delete Fail", Toast.LENGTH_SHORT).show();
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler,request);
				th.start();
				showProgress();
			}
		});
	    replyList.setAdapter(mAdapter);
	    
	    modifyButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BoardShow.this,BoardAdd.class);
				i.putExtra(BoardAdd.MODE_FIELD, BoardAdd.MODE_MODIFY);
				i.putExtra(BoardAdd.ITEM_FIELD, mItem);
				startActivityForResult(i,REQUEST_BOARD_MODIFY);
			}
		});
	    
	    deleteButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BoardDeleteRequest request = new BoardDeleteRequest(mItem);
				request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
					
					@Override
					public void onDownloadCompleted(int result, NetworkRequest request) {
						// TODO Auto-generated method stub
						//DialogManager.getInstance().getLastDialog().dismiss();
						dismissProgress();
						if (result == NetworkRequest.PROCESS_SUCCESS) {
							UploadResult ur = (UploadResult)request.getResult();
							if (ur.result == UploadResult.RESULT_SUCCESS) {
								Intent i = new Intent();
								i.putExtra(RESULT_VALUE, RESULT_RECORD_CHANGED);
								setResult(RESULT_OK,i);
								Toast.makeText(BoardShow.this, "Board Delete Ok", Toast.LENGTH_SHORT).show();
								finish();
							} else {
								Toast.makeText(BoardShow.this, "Board Delete Server Fail", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(BoardShow.this, "Board Delete Fail", Toast.LENGTH_SHORT).show();							
						}
					}
				});
				DownloadThread th = new DownloadThread(mHandler,request);
				th.start();
				showProgress();
			}
		});
	    
	    replyButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(REPLY_DIALOG_ID);
			}
		});
	    Intent i = getIntent();
	    
	    mItem = (BoardItemData)i.getParcelableExtra(BOARD_ITEM_FIELD);
	    
	    if (mItem == null || mItem.id == -1) {
	    	Toast.makeText(this, "Board Item is Null", Toast.LENGTH_SHORT).show();
	    }

	    setImage(mItem.userImageUrl,userImageView);
	    setImage(mItem.imageUrl,boardImageView);
	    
	    boardTitleView.setText(mItem.title);
	    boardContentView.setText(mItem.content);
	    
	    if (mItem.userid == PropertyManager.getInstance().getUserId()) {
	    	modifyButton.setVisibility(View.VISIBLE);
	    	deleteButton.setVisibility(View.VISIBLE);
	    	replyButton.setVisibility(View.VISIBLE);
	    } else {
	    	modifyButton.setVisibility(View.GONE);
	    	deleteButton.setVisibility(View.GONE);
	    	replyButton.setVisibility(View.VISIBLE);
	    }
	    
	    // TODO Auto-generated method stub
	    requestListData();
	}


	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
		// TODO Auto-generated method stub
		if (id == REPLY_DIALOG_ID) {
			ReplyDialog replyDialog = (ReplyDialog)dialog;
			if (bundle != null) {
				ReplyItemData data = (ReplyItemData)bundle.getParcelable(REPLY_ITEM_FIELD);
				replyDialog.setData(data);
			} else {
				replyDialog.setData(null);
			}
		}
		//super.onPrepareDialog(id, dialog, bundle);
	}


	@Override
	protected Dialog onCreateDialog(int id, Bundle b) {
		// TODO Auto-generated method stub
		if (id == REPLY_DIALOG_ID) {
			ReplyDialog dialog = new ReplyDialog(this);
			dialog.setOnReplyDialogButtonClickListener(this);
			if (b != null) {
				ReplyItemData data = (ReplyItemData)b.getParcelable(REPLY_ITEM_FIELD);
				dialog.setData(data);
			}
			return dialog; 
		}
		return super.onCreateDialog(id, b);
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == REPLY_DIALOG_ID) {
			ReplyDialog dialog = new ReplyDialog(this);
			dialog.setOnReplyDialogButtonClickListener(this);
			return dialog; 
		}
		return super.onCreateDialog(id);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_BOARD_MODIFY) {
			if (resultCode == RESULT_OK) {
				int code = data.getIntExtra(BoardAdd.RESULT_VALUE, BoardAdd.RESULT_UPDATE_FAIL);
				if (code == BoardAdd.RESULT_UPDATE_SUCCESS) {
					requestListData();
					Intent i = new Intent();
					i.putExtra(RESULT_VALUE, RESULT_RECORD_CHANGED);
					setResult(RESULT_OK,i);
				}				
			}
		}
	}


	public void setImage(String imageUrl,final ImageView imageView) {
		if (imageUrl != null && !imageUrl.equals("")) {
			ImageRequest request = new ImageRequest(imageUrl+imageSizeParam);
			request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				@Override
				public void onDownloadCompleted(int result, NetworkRequest request) {
					// TODO Auto-generated method stub
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						ImageRequest ir = (ImageRequest)request;
						Bitmap bm = ir.getBitmap();
						if (bm != null) {
							imageView.setImageBitmap(bm);
						}
					}
				}
			});
			ImageManager.getInstance().enqueue(request);
		}
	}
	
	private void requestListData() {
		// TODO Auto-generated method stub
		ReplyListRequest request = new ReplyListRequest(mItem);
		request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			@Override
			public void onDownloadCompleted(int result, NetworkRequest request) {
				// TODO Auto-generated method stub
				//DialogManager.getInstance().getLastDialog().dismiss();
				dismissProgress();
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					ReplyItems items = (ReplyItems)request.getResult();
					if (items.result.equalsIgnoreCase("Success")) {
						mItem.content = items.content;
						mItem.imageUrl = items.imageUrl;
						mItem.title = items.title;
						boardTitleView.setText(mItem.title);
						boardContentView.setText(mItem.content);
						setImage(mItem.imageUrl,boardImageView);
						mAdapter.clear();
						mAdapter.add(items.items);
					} else {
						Toast.makeText(BoardShow.this, "Request Server Fail", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(BoardShow.this, "Request Fail", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		DownloadThread th = new DownloadThread(mHandler,request);
		th.start();
		showProgress();
		
	}


	@Override
	public void onOkButtonClick(final ReplyItemData data) {
		// TODO Auto-generated method stub
		if (data.id == -1) {
			// reply add
			ReplyInsertRequest request = new ReplyInsertRequest(mItem.id, data);
			request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				@Override
				public void onDownloadCompleted(int result, NetworkRequest request) {
					// TODO Auto-generated method stub
					//DialogManager.getInstance().getLastDialog().dismiss();
					dismissProgress();
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						UploadResult ur = (UploadResult)request.getResult();
						if (ur.result == UploadResult.RESULT_SUCCESS) {
							data.id = ur.resultId;
							data.userId = mItem.userid;
							data.userImageUrl = mItem.userImageUrl;
							data.boardId = mItem.id;
							mAdapter.add(data);
						} else {
							Toast.makeText(BoardShow.this, "Insert Server Fail", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(BoardShow.this, "Insert Server Fail", Toast.LENGTH_SHORT).show();
					}
				}
			});
			DownloadThread th = new DownloadThread(mHandler,request);
			th.start();
			showProgress();
		} else {
			ReplyUpdateRequest request = new ReplyUpdateRequest(data);
			request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
				
				@Override
				public void onDownloadCompleted(int result, NetworkRequest request) {
					// TODO Auto-generated method stub
					//DialogManager.getInstance().getLastDialog().dismiss();
					dismissProgress();
					if (result == NetworkRequest.PROCESS_SUCCESS) {
						UploadResult ur = (UploadResult)request.getResult();
						if (ur.result == UploadResult.RESULT_SUCCESS) {
							mAdapter.set(data);
						} else {
							Toast.makeText(BoardShow.this, "Insert Server Fail", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(BoardShow.this, "Insert Fail", Toast.LENGTH_SHORT).show();
					}
				}
			});
			DownloadThread th = new DownloadThread(mHandler,request);
			th.start();
			showProgress();
			
		}
		
	}


	@Override
	public void onCancelButtonClick(ReplyItemData data) {
		// TODO Auto-generated method stub
		
	}

}
