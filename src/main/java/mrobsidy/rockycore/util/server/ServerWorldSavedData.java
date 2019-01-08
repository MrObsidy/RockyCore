package mrobsidy.rockycore.util.server;

import java.util.ArrayList;

import mrobsidy.rockycore.misc.Debug;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class ServerWorldSavedData extends WorldSavedData {
	
	public static final String NAME = "ROCKYCOREDATA";
	
	public ServerWorldSavedData(){
		this(NAME);
	}
	
	public ServerWorldSavedData(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	private ArrayList<NBTTagCompound> attachedData = new ArrayList<NBTTagCompound>();
	private NBTTagCompound savedData;
	
	public void debug(){
		Debug.debug("Attached data count: " + attachedData.size());
	}
	
	public void registerCustomSaveData(NBTTagCompound data){
		attachedData.add(data);
		this.markDirty();
	}
	
	public NBTTagCompound getSavedData(){
		return this.savedData;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		savedData = nbt;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(NBTTagCompound tag : attachedData){
			compound.setTag(tag.getString("rockycore_DATA"), tag);
			Debug.debug("Custom Data: " + tag.toString());
		}
		Debug.debug("Default data: " + compound.toString());
		
		attachedData.clear();
		
		return compound;
	}

}
