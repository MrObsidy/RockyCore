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

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.internal.GridRegistry;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

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
	
	/**
	 * 
	 * Probably won't work, I don't guarantee anything. 
	 * 
	 * @return
	 */
	public NBTTagCompound getSavedData(){
		return this.savedData;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		savedData = nbt;
		GridRegistry reg = RegistryRegistry.getGridRegistry();
		reg.reassembleGrids(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		for(NBTTagCompound tag : attachedData){
			compound.setTag(tag.getString("rockycore_DATA"), tag);
			Debug.debug("Custom Data: " + tag.toString());
		}
		Debug.debug("Default data: " + compound.toString());
		
		attachedData.clear();
	}

}
