import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ReadWriteService {

	ServerMain mMain;
	Socket mRemote;
	public ReadWriteService(ServerMain main, Socket remote) {
		mMain = main;
		mRemote = remote;
	}
	
	public void startService() {
		try {
			InputStream is = mRemote.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mRemote.getOutputStream()));
			String line;
			while( (line = br.readLine()) != null) {
				if (line.equals("LOOP_END")) {
					break;
				} else if (line.equals("MAIN_END")) {
					mMain.mIsRunning = false;
					break;
				}
				bw.append("echo : " + line);
				bw.flush();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mRemote.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
