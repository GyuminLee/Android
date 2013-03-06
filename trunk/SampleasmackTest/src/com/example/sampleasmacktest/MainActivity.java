package com.example.sampleasmacktest;

import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.sampleasmacktest.XMPPManager.OnLoginListener;
import com.example.sampleasmacktest.XMPPManager.OnMessageReceiveListener;
import com.example.sampleasmacktest.XMPPManager.OnRosterListener;

public class MainActivity extends Activity {

	private final static String USERNAME = "dongja94";
	private final static String PASSWORD = "ehdwk94";

//	private static final String DOMAIN = "talk.google.com";
//	private final static int PORT = 5222;
//	private static final String USERNAME = "dongja94@gmail.com";
//	private static final String PASSWORD = "ehdwk941";
	
	private XMPPConnection mXmppConnection;
	
	ListView mList;
	ArrayAdapter<User> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				XMPPManager.getInstance().login(USERNAME, PASSWORD, new OnLoginListener() {
					
					@Override
					public void onLoginSuccess(String username) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();
						XMPPManager.getInstance().getRoster(new OnRosterListener() {
							
							@Override
							public void onRoasterReceived(List<User> users) {
								// TODO Auto-generated method stub
								mAdapter = new ArrayAdapter<User>(MainActivity.this,android.R.layout.simple_list_item_1,users);
								mList.setAdapter(mAdapter);
							}
						});
					}
					
					@Override
					public void onLoginFail(String username) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
		mList = (ListView)findViewById(R.id.listView1);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				User user = mAdapter.getItem(position);
				Intent i = new Intent(MainActivity.this, MessageActivity.class);
				i.putExtra(MessageActivity.PARAM_USER, user.user.getUser());
				startActivity(i);
			}
		});
		XMPPManager.getInstance().addOnMessageReceiveListener(new OnMessageReceiveListener() {
			
			@Override
			public void onMessageReceived(Chat chat, Message message) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "message : " + message.getBody(), Toast.LENGTH_SHORT).show();
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
