package org.tacademy.network.rss.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.tacademy.network.rss.parser.InputStreamParser;
import org.tacademy.network.rss.parser.InputStreamParserException;

import android.os.Environment;

public class SaveParser extends InputStreamParser {

	UploadResult result = new UploadResult();
	
	@Override
	public void doParse(InputStream is) throws InputStreamParserException {
		result.result = 0;
		File file = new File(Environment.getExternalStorageDirectory(),"upfile.txt");
		try {
			FileOutputStream out = new FileOutputStream(file);
			byte[] data = new byte[2048];
			int readSize = 0;
			while (readSize != -1) {
				readSize = is.read(data);
				if (readSize > 0) {
					out.write(data,0,readSize);
				}
			}
			out.flush();
			out.close();
			result.result = 1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getResult() {
		return result;
	}

}
