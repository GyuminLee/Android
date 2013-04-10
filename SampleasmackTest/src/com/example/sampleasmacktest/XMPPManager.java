package com.example.sampleasmacktest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.os.Handler;

public class XMPPManager {

	private static XMPPManager instance;
	Handler mHandler;
	
	public static XMPPManager getInstance() {
		if (instance == null) {
			instance = new XMPPManager();
		}
		return instance;
	}

//	private final static String DOMAIN = "192.168.4.37";
	private final static String DOMAIN = "talk.google.com";
	private final static int PORT = 5222;
	private final static String SERVICE = "gmail.com";
	private XMPPConnection mXmppConnection;
	
	public interface OnLoginListener {
		public void onLoginSuccess(String username);
		public void onLoginFail(String username);
	}

	public interface OnMessageReceiveListener {
		public void onMessageReceived(Chat chat,Message message);
	}
	
	ArrayList<OnMessageReceiveListener> mMessageReceiverListeners = new ArrayList<OnMessageReceiveListener>();
	
	public void addOnMessageReceiveListener(OnMessageReceiveListener listener) {
		mMessageReceiverListeners.add(listener);
	}
	
	public void removeOnMessageReceiveListener(OnMessageReceiveListener listener) {
		mMessageReceiverListeners.remove(listener);
	}
	
	public interface OnRosterListener {
		public void onRoasterReceived(List<User> users);
	}
	
	public interface OnChatListener {
		public void onChatMessageReceived(Chat chat, Message message);
	}
	
	public interface OnMessageSendListener {
		public void onMessageSendSuccess(Chat chat, String message);
		public void onMessageSendFail(Chat chat, String message);
	}
	
	private XMPPManager() {
		mHandler = new Handler();
		ConnectionConfiguration confing = new ConnectionConfiguration(DOMAIN, PORT, SERVICE);
		mXmppConnection = new XMPPConnection(confing);
	}
	
	public void login(final String username, final String password, final OnLoginListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (!mXmppConnection.isConnected()) {
					try {
						mXmppConnection.connect();
						mXmppConnection.login(username, password);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
					if (mXmppConnection.isConnected()) {
						mXmppConnection.getChatManager().addChatListener(new ChatManagerListener() {
							
							@Override
							public void chatCreated(Chat chat, boolean createdLocally) {
								// TODO Auto-generated method stub
								if (!createdLocally) {
									chat.addMessageListener(new MessageListener() {
										
										@Override
										public void processMessage(final Chat chat, final Message message) {
											// TODO Auto-generated method stub
											mHandler.post(new Runnable() {
												
												@Override
												public void run() {
													// TODO Auto-generated method stub
													for (OnMessageReceiveListener listener : mMessageReceiverListeners) {
														listener.onMessageReceived(chat, message);
													}
												}
											});
										}
									});
								}
							}
						});
					}
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (mXmppConnection.isConnected()) {
								listener.onLoginSuccess(username);
							} else {
								listener.onLoginFail(username);
							}
						}
					});
				}
			}
		}).start();
	}
	
	public void getRoster(final OnRosterListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Roster roster = mXmppConnection.getRoster();
				Collection<RosterEntry> entries = roster.getEntries();
				final ArrayList<User> users = new ArrayList<User>();
				for (RosterEntry entry : entries) {
					User user = new User();
					user.user = entry;
					user.presence = roster.getPresence(entry.getUser());
					users.add(user);
				}
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onRoasterReceived(users);
					}
				});
			}
		}).start();
	}
	
	public void sendMessage(final Chat chat,final String message,final OnMessageSendListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					chat.sendMessage(message);
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							listener.onMessageSendSuccess(chat, message);
						}
					});
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							listener.onMessageSendFail(chat, message);
						}
					});
				}
			}
		}).start();
	}
	
	public Chat createChat(String user, final OnChatListener listener ) {
		return mXmppConnection.getChatManager().createChat(user, new MessageListener() {
			
			@Override
			public void processMessage(final Chat chat, final Message message) {
				// TODO Auto-generated method stub
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onChatMessageReceived(chat, message);
					}
				});
			}
		});
	}
}
