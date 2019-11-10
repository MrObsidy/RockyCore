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

package mrobsidy.rockycore.gridnetworks.api.implementation;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.api.IGridConsumer;
import mrobsidy.rockycore.gridnetworks.api.IGridGenerator;
import mrobsidy.rockycore.gridnetworks.internal.GridConnectionSegment;
import mrobsidy.rockycore.gridnetworks.internal.GridNode;
import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityGenerator extends TileEntity implements IGridGenerator, ITickable {
	
	
	public TileEntityGenerator(){

	}
	
	@Override
	public abstract String getGridType();
	
	public abstract float getVoltage();

	@Override
	public final void sendPacket() {
		if(this.getWorld().isRemote) return;

		ArrayList<GridConnectionSegment> segments = new ArrayList<GridConnectionSegment>();
		
		segments.add(new GridConnectionSegment(this.getVoltage(), 0f));
		
		Debug.debug("Generator @ " + this.getPos().toString() + " is sending packets...");
		ArrayList<GridNode> nodes = RegistryRegistry.getGridManagerRegistry().getSurroundingNodes(new GridNode(this.getWorld(), this.getPos(), this.getGridType(), -1, 0f, 0f));
		Debug.debug("Surronding nodes: " + nodes.size());
		for(GridNode node : nodes){
			Debug.debug("Forwarding packet...");
			node.processPacket(new GridPacket(new ArrayList<Integer>(), segments, this.getVoltage(), this));
		}
		
		ArrayList<IGridConsumer> consumers = RegistryRegistry.getGridManagerRegistry().getSurroundingConsumers(new GridNode(this.getWorld(), this.getPos(), this.getGridType(), -1, 0f, 0f));
		for(IGridConsumer consumer : consumers){
			Debug.debug("Forwarding packet...");
			consumer.processPacket(new GridPacket(new ArrayList<Integer>(), segments, this.getVoltage(), this));
		}
	}

	@Override
	public abstract void handleConnection(IGridConsumer consumer);
	
	@Override
	public void onLoad(){
		RegistryRegistry.getGridManagerRegistry().addGenerator(this);
	}
}