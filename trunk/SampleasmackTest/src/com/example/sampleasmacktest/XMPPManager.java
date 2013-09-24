package com.example.sampleasmacktest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import android.os.Handler;
import android.util.Log;

public class XMPPManager {

	private static XMPPManager instance;
	Handler mHandler;
	
	public static XMPPManager getInstance() {
		if (instance == null) {
			instance = new XMPPManager();
		}
		return instance;
	}

	private final static String DOMAIN = "192.168.211.249";
	
//	private final static String DOMAIN = "talk.google.com";
	private final static int PORT = 5222;
	private final static String SERVICE = "gmail.com";
	private XMPPConnection mXmppConnection;
	private FileTransferManager mFileManager;
	
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
//		ConnectionConfiguration confing = new ConnectionConfiguration(DOMAIN, PORT, SERVICE);
		ConnectionConfiguration confing = new ConnectionConfiguration(DOMAIN, PORT);		
		mXmppConnection = new XMPPConnection(confing);
	}

	public void addFileTransfer() {
		mFileManager = new FileTransferManager(mXmppConnection);
		mFileManager.addFileTransferListener(new FileTransferListener() {
			
			@Override
			public void fileTransferRequest(FileTransferRequest request) {
				IncomingFileTransfer infile = request.accept();
				try {
					infile.recieveFile(new File("filename"));
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public void sendFileTransfor(String userId) throws XMPPException {
		OutgoingFileTransfer outfile = mFileManager.createOutgoingFileTransfer(userId);
		outfile.sendFile(new File("filename"), "file description");
	}
	
	public boolean addAccount(String username, String password, Map<String,String> attributes) {
		try {
			if (!mXmppConnection.isConnected()) {
					mXmppConnection.connect();
			}
			AccountManager am = mXmppConnection.getAccountManager();
			if (am.supportsAccountCreation()) {
				am.createAccount(username, password, attributes);
				return true;
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}

	public RosterEntry addRoster(String user, String name, String... groups) {
		Roster roster = mXmppConnection.getRoster();
		if (!roster.contains(user)) {
			for (String group : groups) {
				RosterGroup rg = roster.getGroup(group);
				if (rg == null) {
					roster.createGroup(group);
				}
			}
			try {
				roster.createEntry(user, name, groups);
				return roster.getEntry(user);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void login(final String username, final String password, final OnLoginListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (!mXmppConnection.isConnected()) {
					try {
						mXmppConnection.connect();
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (mXmppConnection.isConnected()) {
					try {
						mXmppConnection.login(username, password);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
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
					Presence p = new Presence(Presence.Type.available);
					p.setStatus("getmessage");
					mXmppConnection.sendPacket(p);
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
				roster.addRosterListener(new RosterListener() {
					
					@Override
					public void presenceChanged(Presence presence) {
						Log.i("Roster","From : " + presence.getFrom() + ", " + presence.getStatus());
					}
					
					@Override
					public void entriesUpdated(Collection<String> arg0) {
					}
					
					@Override
					public void entriesDeleted(Collection<String> arg0) {
					}
					
					@Override
					public void entriesAdded(Collection<String> arg0) {
					}
				});
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
