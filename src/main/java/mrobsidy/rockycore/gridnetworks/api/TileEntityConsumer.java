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

package mrobsidy.rockycore.gridnetworks.api;

import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public abstract class TileEntityConsumer extends TileEntity implements  ITickable {
	
	public TileEntityConsumer() {
		
	}
	
	private String gridType;
	
	public void register() {
		RegistryRegistry.getGridManagerRegistry().relayPacket(this.gridType);
	}
	
	@Override
	public void onLoad(){
		RegistryRegistry.getGridManagerRegistry().addConsumer(this);
	}

	public abstract float receivePower(float voltage, float f);

	public void setGridType(String parType) {
		this.gridType = parType;
	}
	
	public String getGridType() {
		return this.gridType;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		this.gridType = tag.getString("gridType");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		tag.setString("gridType", this.gridType);
		return tag;
	}	
	
	public abstract void processPacket(GridPacket gridPacket);
}
