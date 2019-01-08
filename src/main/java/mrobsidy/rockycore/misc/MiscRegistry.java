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
	
}
