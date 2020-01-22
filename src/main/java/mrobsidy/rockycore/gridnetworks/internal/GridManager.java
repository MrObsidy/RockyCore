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

import mrobsidy.rockycore.gridnetworks.api.TileEntityConsumer;
import mrobsidy.rockycore.gridnetworks.api.TileGridNode;
import mrobsidy.rockycore.gridnetworks.api.TileEntityGenerator;
import mrobsidy.rockycore.misc.MiscUtil;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.util.misc.helpers.BlockHelper;
import mrobsidy.rockycore.util.server.ServerChatMessages;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;


public class GridManager {
	
	private final String gridType;
	
	private final ArrayList<TileGridNode> nodes;
	private final ArrayList<TileEntityGenerator> generators;
	private final ArrayList<TileEntityConsumer> consumers;
	
	
	public GridManager(String type){
		
		Debug.INSTANCE.debug("Created new GridManager", EnumDebugType.INFO);
		
		this.gridType = type;
		this.nodes = new ArrayList<TileGridNode>();
		this.generators = new ArrayList<TileEntityGenerator>();
		this.consumers = new ArrayList<TileEntityConsumer>();
	}
	
	public void triggerPacket(){
		for(TileEntityGenerator generator : generators){
			Debug.INSTANCE. debug("Sending packets for type " + this.gridType, EnumDebugType.INFO);
			generator.sendPacket();
		}
	}
	
	public void dump() {
		for(TileGridNode node : this.nodes) {
			System.out.println("Node at position " + node.getPos().toString() + " in dimension " + node.getWorld().provider.getDimension());
		}
		for(TileEntityConsumer con : this.consumers) {
			System.out.println("Consumer at position " + con.getPos().toString() + " in dimension " + con.getWorld().provider.getDimension());
		}
		for(TileEntityGenerator gen : this.generators) {
			System.out.println("Generator at position " + gen.getPos().toString() + " in dimension " + gen.getWorld().provider.getDimension());
		}
	}
	
	public ArrayList<TileGridNode> getSurroundingNodes(BlockPos position, int dimension){
		ArrayList<TileGridNode> nodes = new ArrayList<TileGridNode>();
		
		BlockHelper[] helpers = MiscUtil.getSurroundingBlocks(position, dimension);
		for(TileGridNode pNode : this.nodes){
			for(BlockHelper helper : helpers){
				Debug.INSTANCE.debug("Checking node @ " + pNode.getPos().toString() + " of " + this.nodes.size(), EnumDebugType.DEBUG);
				if(pNode.getPos().getX() == helper.getPos().getX() && pNode.getPos().getY() == helper.getPos().getY() && pNode.getPos().getZ() == helper.getPos().getZ() && dimension == pNode.getWorld().provider.getDimension()){
					Debug.INSTANCE.debug("Found node.", EnumDebugType.DEBUG);
					nodes.add(pNode);
				}
			}
		}
		
		return nodes;
	}
	
	public ArrayList<TileEntityConsumer> getSurroundingConsumers(BlockPos position, int dimension){
		ArrayList<TileEntityConsumer> consumers = new ArrayList<TileEntityConsumer>();
		
		BlockHelper[] helpers = MiscUtil.getSurroundingBlocks(position, dimension);
		for(TileEntityConsumer consumer : this.consumers){
			Debug.INSTANCE.debug("Checking consumer @ " + consumer.getPos().toString() + " of " + this.consumers.size(), EnumDebugType.DEBUG);
			for(BlockHelper helper : helpers){
				if(consumer.getPos().getX() == helper.getPos().getX() && consumer.getPos().getY() == helper.getPos().getY() && consumer.getPos().getZ() == helper.getPos().getZ() && dimension == consumer.getWorld().provider.getDimension()){
					Debug.INSTANCE.debug("Found consumer.", EnumDebugType.DEBUG);
					consumers.add(consumer);
				}
			}
		}
		
		return consumers;
	}
	
	public String getGridType(){
		return this.gridType;
	}
	
	public void addNode(TileGridNode node){
		node.setID(nodes.size());
		nodes.add(node);
	}
	
	public void removeNode(TileGridNode node){
		boolean success = false;
		
		for(TileGridNode n : nodes) {
			if(n.getPos().getX() == node.getPos().getX() && n.getPos().getY() == node.getPos().getY() && n.getPos().getZ() == node.getPos().getZ() && node.getWorld().provider.getDimension() == n.getWorld().provider.getDimension()) {
				Debug.INSTANCE.debug("Found node to remove", EnumDebugType.INFO);
				success = nodes.remove(n);
				break;
			}
		}
		

		if(!success) Debug.INSTANCE.debug("Could not remove node @ " + node.getPos().toString(), EnumDebugType.ERROR);
	}
	
	public void addGenerator(TileEntityGenerator generator){
		generators.add(generator);
	}
	
	public void removeGenerator(TileEntityGenerator node){
		boolean success = false;
		
		for(TileEntityGenerator n : generators) {
			if(n.getPos().getX() == node.getPos().getX() && n.getPos().getY() == node.getPos().getY() && n.getPos().getZ() == node.getPos().getZ() && node.getWorld().provider.getDimension() == n.getWorld().provider.getDimension()) {
				Debug.INSTANCE.debug("Found generator to remove.", EnumDebugType.DEBUG);
				success = generators.remove(n);
				break;
			}
		}
		

		if(!success) Debug.INSTANCE.debug("Could not remove generator @ " + node.getPos().toString(), (EnumDebugType.ERROR));
	}
	
	public void addConsumer(TileEntityConsumer consumer){
		consumers.add(consumer);
	}
	
	public void removeConsumer(TileEntityConsumer node){
		boolean success = false;
		
		for(TileEntityConsumer n : consumers) {
			if(n.getPos().getX() == node.getPos().getX() && n.getPos().getY() == node.getPos().getY() && n.getPos().getZ() == node.getPos().getZ() && node.getWorld().provider.getDimension() == n.getWorld().provider.getDimension()) {
				Debug.INSTANCE.debug("Found consumer to remove", EnumDebugType.DEBUG);
				success = consumers.remove(n);
				break;
			}
		}
		
		if(!success) Debug.INSTANCE.debug("Could not remove consumer @ " + node.getPos().toString(), EnumDebugType.ERROR);
	}
	
	public ArrayList<TileGridNode> getNodes() {
		return nodes;
	}
	
	public ArrayList<TileEntityGenerator> getGenerators() {
		return generators;
	}
	
	public ArrayList<TileEntityConsumer> getConsumers(){
		return consumers;
	}
	
	public void flush() {
		this.nodes.clear();
		this.consumers.clear();
		this.generators.clear();
	}
	
	public TileGridNode getNodeAtPos(BlockPos pos, World world){
		TileGridNode returnNode = null;
		
		for(TileGridNode node : nodes){
			
			Debug.INSTANCE.debug("Pos = pos: " + (node.getPos().getX() == pos.getX() && node.getPos().getY() == pos.getY() && node.getPos().getZ() == pos.getZ()), EnumDebugType.DEBUG);
			Debug.INSTANCE.debug("Dimension = dimension: " + (node.getWorld().provider.getDimension() == world.provider.getDimension()), EnumDebugType.DEBUG);
			
			if(node.getPos().getX() == pos.getX() && node.getPos().getY() == pos.getY() && node.getPos().getZ() == pos.getZ() && node.getWorld().provider.getDimension() == world.provider.getDimension()){
				returnNode = node;
				break;
			}
		}
		
		return returnNode;
	}

	
}
