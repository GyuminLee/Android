package com.example.testasmack;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testasmack.XMPPManager.OnChatActivateListener;
import com.example.testasmack.XMPPManager.OnLoginResultListener;
import com.example.testasmack.XMPPManager.OnUserListListener;

public class MainActivity extends Activity {

	EditText userView;
	EditText passwordView;
	ListView listView;
	ArrayAdapter<User> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SmackAndroid.init(this);
		setContentView(R.layout.activity_main);
		userView = (EditText) findViewById(R.id.userid);
		passwordView = (EditText) findViewById(R.id.password);
		listView = (ListView) findViewById(R.id.userList);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				User user = mAdapter.getItem(position);
				Intent i = new Intent(MainActivity.this, ChatListActivity.class);
				i.putExtra(ChatListActivity.PARAM_USER_ID, user.userid);
				startActivity(i);
			}
		});
		Button btn = (Button) findViewById(R.id.btnLogin);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String userid = userView.getText().toString();
				String password = passwordView.getText().toString();
				XMPPManager.getInstance().login(userid, password,
						new OnLoginResultListener() {

							@Override
							public void onSuccess(String userid) {
								Toast.makeText(MainActivity.this,
										"Login Success", Toast.LENGTH_SHORT)
										.show();
								XMPPManager.getInstance().getRoster(
										new OnUserListListener() {

											@Override
											public void onUserList(
													ArrayList<User> list) {
												mAdapter = new ArrayAdapter<User>(
														MainActivity.this,
														android.R.layout.simple_list_item_1,
														list);
												listView.setAdapter(mAdapter);

											}
										});
							}

							@Override
							public void onFail(String userid) {
								Toast.makeText(MainActivity.this, "Login Fail",
										Toast.LENGTH_SHORT).show();
							}
						});
			}
		});
		
		XMPPManager.getInstance().addOnChatActivateListener(new OnChatActivateListener() {
			
			@Override
			public void onChatActivated(Chat chat, Message message) {
				Toast.makeText(MainActivity.this, message.getFrom() + " : " + message.getBody(), Toast.LENGTH_SHORT).show();
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
