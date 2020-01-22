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

import mrobsidy.rockycore.gridnetworks.internal.GridConnectionSegment;
import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityGenerator extends TileEntity implements ITickable {
	
	protected final ArrayList<TileEntityConsumer> connectedConsumers = new ArrayList<TileEntityConsumer>();
	
	private String gridType;
	
	public TileEntityGenerator(){
		
	}
	
	public void register() {
		RegistryRegistry.getGridManagerRegistry().relayPacket(this.gridType);
	}
	
	public String getGridType() {
		return this.gridType;
	}
	
	public void setGridType(String parType) {
		this.gridType = parType;
	}
	
	public abstract float getVoltage();

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
	
	public final void sendPacket() {
		if(this.getWorld().isRemote) return;

		ArrayList<GridConnectionSegment> segments = new ArrayList<GridConnectionSegment>();
		
		segments.add(new GridConnectionSegment(this.getVoltage(), 0f));
		
		Debug.INSTANCE.debug("Generator @ " + this.getPos().toString() + " is sending packets...", EnumDebugType.DEBUG);
		ArrayList<TileGridNode> nodes = RegistryRegistry.getGridManagerRegistry().getSurroundingNodes(this.getPos(), this.getWorld().provider.getDimension(), this.getGridType());
		Debug.INSTANCE.debug("Surronding nodes: " + nodes.size(), EnumDebugType.DEBUG);
		for(TileGridNode node : nodes){
			Debug.INSTANCE.debug("Forwarding packet...", EnumDebugType.DEBUG);
			node.processPacket(new GridPacket(new ArrayList<Integer>(), segments, this.getVoltage(), this));
		}
		
		ArrayList<TileEntityConsumer> consumers = RegistryRegistry.getGridManagerRegistry().getSurroundingConsumers(this.getPos(), this.getWorld().provider.getDimension(), this.getGridType());
		for(TileEntityConsumer consumer : consumers){
			Debug.INSTANCE.debug("Forwarding packet...", EnumDebugType.DEBUG);
			consumer.processPacket(new GridPacket(new ArrayList<Integer>(), segments, this.getVoltage(), this));
		}
	}

	public abstract void handleConnection(TileEntityConsumer consumer);
	
	@Override
	public void onLoad(){
		RegistryRegistry.getGridManagerRegistry().addGenerator(this);
	}
}