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


package mrobsidy.rockycore.misc;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

/**
 * 
 * A place where everything you will or won't need is registered.
 * 
 * @author mrobsidy
 *
 */
public class MiscRegistry {
	
	public EntityPlayer lastJoinedPlayer;
	
	private HashMap<String, String> gpStringStorage = new HashMap<String, String>();
	private HashMap<String, Integer> gpIntStorage = new HashMap<String, Integer>();
	private HashMap<String, Float> gpFloatStorage = new HashMap<String, Float>();
	private HashMap<String, Boolean> gpBooleanStorage = new HashMap<String, Boolean>();
	private HashMap<String, HashMap> gpHashMapStorage = new HashMap<String, HashMap>();
	
	public MiscRegistry(){
		//TODO: Make this useful and not a dummy constructor
	}
	
	public void registerString(String key, String value){
		gpStringStorage.put(key, value);
	}
	
	public void registerInt(String key, int value){
		gpIntStorage.put(key, new Integer(value));
	}
	
	public void registerFloat(String key, float value){
		gpFloatStorage.put(key, new Float(value));
	}
	
	public void registerBoolean(String key, boolean value){
		gpBooleanStorage.put(key, new Boolean(value));
	}
	
	public void registerHashMap(String key, HashMap value){
		gpHashMapStorage.put(key, value);
	}
	
	public String getString(String key){
		return gpStringStorage.get(key);
	}
	
	public int getInt(String key){
		return gpIntStorage.get(key).intValue();
	}
	
	public float getFloat(String key){
		return gpFloatStorage.get(key).floatValue();
	}
	
	public boolean getBoolean(String key){
		return gpBooleanStorage.get(key).booleanValue();
	}
	
	public HashMap getHashMap(String key){
		return gpHashMapStorage.get(key);
	}
	
}
