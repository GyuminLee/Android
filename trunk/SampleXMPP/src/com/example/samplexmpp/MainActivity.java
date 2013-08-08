package com.example.samplexmpp;

import java.util.ArrayList;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText userIdView;
	EditText passwordView;
	EditText messageView;
	ListView listView;
	ArrayAdapter<String> mAdapter;
	ArrayList<String> mMessageList = new ArrayList<String>();
	Chat mChat;
	
	XMPPConnection mConn;
	
	public static final String DOMAIN = "192.168.206.132";
	public static final int PORT = 5222;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userIdView = (EditText)findViewById(R.id.userid);
		passwordView = (EditText)findViewById(R.id.password);
		messageView = (EditText)findViewById(R.id.message);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMessageList);
		listView.setAdapter(mAdapter);
		createConnection();
		
		Button btn = (Button)findViewById(R.id.btnLogin);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String userid = userIdView.getText().toString();
						String password = passwordView.getText().toString();
						login(userid,password);
					}
				}).start();
			}
		});
		
		btn = (Button)findViewById(R.id.btnSend);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String message = messageView.getText().toString();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						sendMessage(message);
					}
				}).start();
			}
		});
		
		btn = (Button)findViewById(R.id.btnUserList);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						getRoster();
					}
				}).start();
			}
		});
	}
	
	
	private void addFriend(String userid, String name, String... groups) {
		Roster roster = mConn.getRoster();
		try {
			roster.createEntry(userid, name, groups);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	String[] mList;
	
	private void getRoster() {
		if (mConn.isConnected()) {
			Roster roster = mConn.getRoster();
			final ArrayList<String> userlist = new ArrayList<String>();
			for (RosterEntry entry : roster.getEntries() ) {
				userlist.add(entry.getUser());
			}
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Friend List");
					String[] list = new String[userlist.size()];
					for (int i = 0; i < userlist.size(); i++) {
						list[i] = userlist.get(i);
					}
					mList = list;
					builder.setItems(list, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mChat = mConn.getChatManager().createChat(mList[which], new MessageListener() {
								
								@Override
								public void processMessage(Chat chat, Message message) {
									final String msg = message.getBody();
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											mAdapter.add("remote : " + msg);
										}
									});
								}
							});
							
//							String status = mConn.getRoster().getPresence(mList[which]).toString();
						}
					});
					
					builder.create().show();
				}
			});
		}
	}
	
	private void sendMessage(final String message) {
		if (mChat != null) {
			try {
				mChat.sendMessage(message);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mAdapter.add("local : " + message);
					}
				});
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		
	}
	private void createConnection() {
		ConnectionConfiguration config = new ConnectionConfiguration(DOMAIN, PORT);
		mConn = new XMPPConnection(config);
	}
	
	
	private void addAccount(String userid, String password) {
		try {
			if (!mConn.isConnected()) {
				mConn.connect();
			}
			AccountManager am = mConn.getAccountManager();
			if (am.supportsAccountCreation()) {
				am.createAccount(userid, password);
				showMessage("success account create");
			} else {
				showMessage("server not allow account creation");
			}
		} catch( XMPPException e) {
			e.printStackTrace();
		}
	}
	
	private void login(String userid, String password) {
		try {
			if (!mConn.isConnected()) {
				mConn.connect();
			}
			mConn.login(userid, password);
			showMessage("Login Success!!");
			
			mConn.getChatManager().addChatListener(new ChatManagerListener() {
				
				@Override
				public void chatCreated(Chat chat, boolean local) {
					
					if (local) return;
					
					mChat = chat;
					
					mChat.addMessageListener(new MessageListener() {
						
						@Override
						public void processMessage(Chat chat, Message message) {
							final String msg = message.getBody();
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									mAdapter.add("remote : " + msg);
								}
							});
						}
					});
				}
			});
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void showMessage(final String message) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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
