import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {

	ServerSocket mServerSocket;
	public static final int SERVER_PORT = 50001;
	
	public boolean mIsRunning = true;
	
	public void ServerMain() {
		try {
			mServerSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runServer() {
		try {
			while(mIsRunning) {
				final Socket s = mServerSocket.accept();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						new ReadWriteService(ServerMain.this, s).startService();
					}
				}).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerMain main = new ServerMain();
		main.runServer();
	}

}
