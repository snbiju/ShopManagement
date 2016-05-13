package retailshop.shop.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOService {

	final String persistObjectFile = "persist.ser";

	public Object deserialize(){

		Object obj = null;
		try{
			FileInputStream fis = new FileInputStream(persistObjectFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
		}catch(IOException e){
			System.out.println("File not found : "+e);
		}catch(ClassNotFoundException e){
			System.out.println("Class not found : "+e);
		}
		return obj;
	}

	public void serialize(Object obj){
		
		try{
			FileOutputStream fos = new FileOutputStream(persistObjectFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.close();
		}catch(IOException e){
			System.out.println("File not found : "+e);
		}
	}
	
}
