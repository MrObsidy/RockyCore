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
import mrobsidy.rockycore.misc.Debug;
import mrobsidy.rockycore.misc.MiscUtil;
import mrobsidy.rockycore.util.misc.helpers.BlockHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class GridManager {
	
	private final String gridType;
	
	private final ArrayList<GridNode> nodes;
	private final ArrayList<IGridGenerator> generators;
	private final ArrayList<IGridConsumer> consumers;
	
	
	public GridManager(String type){
		
		Debug.debug("Created new GridManager");
		
		this.gridType = type;
		this.nodes = new ArrayList<GridNode>();
		this.generators = new ArrayList<IGridGenerator>();
		this.consumers = new ArrayList<IGridConsumer>();
	}
	
	public void triggerPacket(){
		for(IGridGenerator generator : generators){
			Debug.debug("Sending packets");
			generator.sendPacket();
		}
	}
	
	public ArrayList<GridNode> getSurroundingNodes(GridNode node){
		ArrayList<GridNode> nodes = new ArrayList<GridNode>();
		
		BlockPos thisPos = node.getPos();
		
		int nodeDim = node.getWorld().provider.getDimension();
		
		Debug.debug("Node position: " + thisPos.toString() + ", node dimension: " + nodeDim);
		
		BlockHelper[] helpers = MiscUtil.getSurroundingBlocks(node.getPos(), nodeDim);
		
		
		for(GridNode pNode : this.nodes){
			for(BlockHelper helper : helpers){
				Debug.debug("Checking node @ " + pNode.getPos().toString() + " of " + this.nodes.size());
				if(pNode.getPos().getX() == helper.getPos().getX() && pNode.getPos().getY() == helper.getPos().getY() && pNode.getPos().getZ() == helper.getPos().getZ()){
					Debug.debug("Found node: " + pNode.toString());
					nodes.add(pNode);
				}
			}
		}
		
		return nodes;
	}
	
	public ArrayList<IGridConsumer> getSurroundingConsumers(GridNode node){
		ArrayList<IGridConsumer> consumers = new ArrayList<IGridConsumer>();
		
		BlockPos thisPos = node.getPos();
		BlockHelper[] helpers = MiscUtil.getSurroundingBlocks(node.getPos(), node.getWorld().provider.getDimension());
		
		for(BlockHelper helper : helpers){
			for(IGridConsumer consumer : this.consumers){
				if(consumer.getPos() == helper.getPos()){
					consumers.add(consumer);
					break;
				}
			}
		}
		
		return consumers;
	}
	
	public String getGridType(){
		return this.gridType;
	}
	
	public void addNode(GridNode node){
		getNodes().add(node);
	}
	
	public void removeNode(GridNode node){
		getNodes().remove(node);
	}
	
	public void addGenerator(IGridGenerator generator){
		generators.add(generator);
	}
	
	public void removeGenerator(IGridGenerator generator){
		generators.remove(generator);
	}
	
	public void addConsumer(IGridConsumer consumer){
		consumers.add(consumer);
	}
	
	public void removeConsumer(IGridConsumer consumer){
		consumers.remove(consumer);
	}
	
	public int getNextID(){
		return this.getNodes().size();
	}

	public ArrayList<GridNode> getNodes() {
		return nodes;
	}
	
	public GridNode getNodeAtPos(BlockPos pos, World world){
		GridNode returnNode = null;
		
		for(GridNode node : nodes){
			
			Debug.debug("Pos = pos: " + (node.getPos().getX() == pos.getX() && node.getPos().getY() == pos.getY() && node.getPos().getZ() == pos.getZ()));
			Debug.debug("Dimension = dimension: " + (node.getWorld().provider.getDimension() == world.provider.getDimension()));
			
			if(node.getPos().getX() == pos.getX() && node.getPos().getY() == pos.getY() && node.getPos().getZ() == pos.getZ() && node.getWorld().provider.getDimension() == world.provider.getDimension()){
				returnNode = node;
				break;
			}
		}
		
		return returnNode;
	}
}
