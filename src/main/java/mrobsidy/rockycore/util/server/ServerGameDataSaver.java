package mrobsidy.rockycore.util.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * DON'T. USE. THIS.
 * 
 * Very broken plus serialization is a pain in the you-know-where.
 * 
 * 
 * @author alexa
 *
 */
public class ServerGameDataSaver {
	public static void saveObjectsToDisk(Object object, String location){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(location));
			oos.writeObject(object);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Object readObjectsFromDisk(String location){
		
		Object returner = null;
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(location));
			try {
				returner = ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ois.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return returner;
	}
	
	public static ArrayList<NBTTagCompound> relay(){
		ArrayList<NBTTagCompound> returnables = new ArrayList<NBTTagCompound>();
		
		returnables.add(RegistryRegistry.getGridRegistry().getSaveData());
		
		return returnables;
	}
}
