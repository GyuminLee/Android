package org.tacademy.basic.nfc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataObject implements Serializable {

	public int id;
	public String text;
	public byte[] buffer;
	
//	public void writeObject(ObjectOutputStream oos) throws IOException {
//		oos.write(id);
//		oos.writeObject(text);
//		oos.write(buffer.length);
//		oos.write(buffer);
//	}
//	
//	public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
//		id = ois.readInt();
//		text = (String)ois.readObject();
//		int length = ois.readInt();
//		buffer = new byte[length];
//		ois.read(buffer);
//	}
}
