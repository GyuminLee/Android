package org.tacademy.network.rss.c2dm;

import org.tacademy.network.rss.ParentActivity;
import org.tacademy.network.rss.R;
import org.tacademy.network.rss.Nework.NetworkRequest;
import org.tacademy.network.rss.util.DialogManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FriendListActivity extends ParentActivity {

	UserListAdapter mAdapter;
	ListView list;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.user_list);
	    
	    list = (ListView)findViewById(R.id.userList);
	    
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FriendListActivity.this,ChatMessageList.class);
				User user = (User)mAdapter.getItem(position);
				i.putExtra("user",user);
				startActivity(i);
			}
		});
	    
	    UserListRequest request = new UserListRequest();
	    request.setOnDownloadCompletedListener(new NetworkRequest.OnDownloadCompletedListener() {
			
			@Override
			public void onDownloadCompleted(int result, NetworkRequest request) {
				dismissDialog(DialogManager.WAIT_PROGRESS);
				// TODO Auto-generated method stub
				if (result == NetworkRequest.PROCESS_SUCCESS) {
					Users users = (Users)request.getResult();
					mAdapter = new UserListAdapter(FriendListActivity.this,users.items);
					list.setAdapter(mAdapter);
				}
			}
		});
	    showDialog(DialogManager.WAIT_PROGRESS);
	    
	    // TODO Auto-generated method stub
	}

}
