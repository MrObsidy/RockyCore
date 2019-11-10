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

package mrobsidy.rockycore.gridnetworks.internal;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.api.IGridConsumer;
import mrobsidy.rockycore.gridnetworks.api.IGridGenerator;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import mrobsidy.rockycore.misc.MiscUtil;
import mrobsidy.rockycore.util.misc.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class GridNode {
	
	private final World world;
	private final BlockPos pos;
	private final String gridType;
	private final float resistance;
	private final float transformationFactor;
	
	private final int ID;
	
	public GridNode(World worldIn, BlockPos pos, String gridType, int id, float resistance, float transf){
		this.world = worldIn;
		this.pos = pos;
		this.gridType = gridType;
		this.resistance = resistance;
		this.ID = id;
		this.transformationFactor = transf;
	}
	
	public void processPacket(GridPacket packet){
		Debug.debug("Processing a packet: " + packet.toString() + " from " + packet.getGenerator().toString());
		ArrayList<Integer> visitedNodes = packet.getVisitedNodes();
		ArrayList<GridConnectionSegment> segments = packet.getSegments();
		
		IGridGenerator offerer = packet.getGenerator();
		
		packet = null;
		
		for(int id : visitedNodes){
			Debug.debug("Visited:" + id);
		}
			
		//if that packet already contains this node, return
		//since we dont want to loop endlessly
		if(visitedNodes.contains(this.ID)){
			Debug.debug("Aborting, already containing: " + this.ID);
			return;
		}
		
		int newIndex = segments.size();
		
		GridConnectionSegment lastSegment = segments.get(segments.size() - 1);
		
		GridConnectionSegment thisSegment = new GridConnectionSegment(lastSegment.getVoltage() * this.transformationFactor, this.resistance);
		segments.add(thisSegment);
		
		visitedNodes.add(this.ID);
		
		Debug.debug("Passing on packet");
		ArrayList<GridNode> nodes = RegistryRegistry.getGridManagerRegistry().getSurroundingNodes(this);
		for(GridNode node : nodes){
			Debug.debug("Passed on packet to " + node.toString());
			node.processPacket(new GridPacket(visitedNodes, segments, this.transformationFactor, offerer));
		}
		
		ArrayList<IGridConsumer> consumers = RegistryRegistry.getGridManagerRegistry().getSurroundingConsumers(this);
		for(IGridConsumer consumer : consumers){
			consumer.processPacket(new GridPacket(visitedNodes, segments, this.transformationFactor, offerer));
		}
	}
	
	public World getWorld(){
		return this.world;
	}
	
	public BlockPos getPos(){
		return this.pos;
	}
	
	public String getGridType() {
		return this.gridType;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public float getResistance(){
		return this.resistance;
	}
	
	public float getTransformationFactor(){
		return this.transformationFactor;
	}
}
