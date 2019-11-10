/**
 * 
 *  RockyCore
 *  Copyright (C) 2018-2019 MrObsidy
 *  
 *  
 *  This file is part of RockyCore.
 *
 *  RockyCore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  RockyCore is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with RockyCore.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */


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
		
		returnables.add(RegistryRegistry.getGridManagerRegistry().getSaveData());
		
		return returnables;
	}
}
