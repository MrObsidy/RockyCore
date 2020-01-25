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

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.MiscUtil;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.util.misc.helpers.BlockHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Base class for TileEntityConsumers. Extend this for custom Energy Consumers.
 * 
 * 
 * @author mrobsidy
 *
 */
public abstract class TileEntityConsumer extends TileEntity implements  ITickable {
	
	public final ArrayList<TileEntityGenerator> providingGenerators = new ArrayList<TileEntityGenerator>();
	
	public TileEntityConsumer() {
		
	}
	
	private String gridType;
	
	@Override
	/**
	 * If you override this, run super.onLoad();
	 * 
	 */
	public void onLoad(){
		RegistryRegistry.getGridManagerRegistry().addConsumer(this);
		RegistryRegistry.getGridManagerRegistry().relayPacket(this.gridType);
	}

	/**
	 * First, you need to call generator#requestPower(this, power);
	 * 
	 * 
	 * Then, this gets called by the generator with an "energy packet" with voltage and amperage that you can work with.
	 * 
	 * @param voltage
	 * @param amperage
	 * @return
	 */
	public abstract float receivePower(float voltage, float amperage);
	
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
	
	public final void processPacket(GridPacket packet) {
		Debug.getDebugger().debug("Received packet: " + packet.toString() + " from " + packet.getGenerator().toString(), EnumDebugType.DEBUG);
		this.providingGenerators.add(packet.getGenerator());
	}
}
