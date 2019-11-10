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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;


public class GridManagerRegistry {
	
	private final ArrayList<GridManager> GRID_MANAGERS = new ArrayList<>();

	public GridManagerRegistry(){}
	
	public void addNode(GridNode node){
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if (currMan.getGridType() == node.getGridType()){
				manager = currMan;
				break;
			}
		}
		
		if (manager == null){
			manager = new GridManager(node.getGridType());
			GRID_MANAGERS.add(manager);
		}
		
		manager.addNode(node);
	}
	
	public void removeNode(GridNode node){
		
		Debug.debug("Node: " + node.toString());
		
		GridManager manager = null;
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType() == node.getGridType()){
				manager = man;
				break;
			}
		}
		manager.removeNode(node);
		
	}
	
	public void addGenerator(IGridGenerator generator){
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if (currMan.getGridType() == generator.getGridType()){
				manager = currMan;
				break;
			}
		}
		
		if (manager == null){
			manager = new GridManager(generator.getGridType());
			GRID_MANAGERS.add(manager);
		}
		
		manager.addGenerator(generator);
	}
	
	public void removeGenerator(IGridGenerator node){
		GridManager manager = null;
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType() == node.getGridType()){
				manager = man;
				manager.removeGenerator(node);
				break;
			}
		}
	}
	
	public void addConsumer(IGridConsumer consumer){
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if (currMan.getGridType() == consumer.getGridType()){
				manager = currMan;
				break;
			}
		}
		
		if (manager == null){
			manager = new GridManager(consumer.getGridType());
			GRID_MANAGERS.add(manager);
		}
		
		
		manager.addConsumer(consumer);
	}

	public void removeConsumer(IGridConsumer node){
		GridManager manager = null;
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType() == node.getGridType()){
				manager = man;
				manager.removeConsumer(node);
				break;
			}
		}
	}
	
	public GridNode getNodeAtPos(String gridType, BlockPos pos, World world){
		GridNode returnNode = null;
		GridManager nMan = null;
		
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType() == gridType){
				nMan = man;
				break;
			}
		}
		
		if(nMan != null){
			Debug.debug("found manager, retrieving node...");
			returnNode = nMan.getNodeAtPos(pos, world);
		} else {
			returnNode = null;
		}
			
		return returnNode;
	}
	
	public ArrayList<IGridConsumer> getSurroundingConsumers(GridNode node) {
		ArrayList<IGridConsumer> consumers = new ArrayList<IGridConsumer>();
		for(GridManager manager : GRID_MANAGERS){
			if(manager.getGridType() == node.getGridType()){
				consumers = manager.getSurroundingConsumers(node);
				break;
			}
		}
		
		return consumers;
	}
	
	public int getNextID(String name){
		
		Debug.debug("Fetch next ID");
		
		GridManager man = null;
		for(GridManager manager : GRID_MANAGERS){
			if(manager.getGridType() == name){
				man = manager;
				break;
			}
		}
		
		if(man == null){
			man = new GridManager(name);
		}
		
		return man.getNextID();
	}
	
	public void relayPacket(String gridType){
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType() == gridType){
				man.triggerPacket();
				break;
			}
		}
	}
	
	public ArrayList<GridNode> getSurroundingNodes(GridNode node){
		ArrayList<GridNode> nodes = new ArrayList<GridNode>();
		for(GridManager manager : GRID_MANAGERS){
			if(manager.getGridType() == node.getGridType()){
				nodes = manager.getSurroundingNodes(node);
				
				Debug.debug("Found manager: " + manager.getGridType());
				break;
			}
		}
		
		return nodes;
	}
	
	public void reconstruct(NBTTagCompound saveCompound){		
		System.out.println("Reconstructing Grids...");
		
		NBTTagCompound gridsCompound = saveCompound.getCompoundTag("GRIDDATA");
		
		System.out.println(gridsCompound.toString());
		
		int count = gridsCompound.getInteger("gridManCount");
		
		int i = 0;
		while(i < count){
			System.out.println("Reconstructing gridManager " + i);
			
			NBTTagCompound gridManCompound = gridsCompound.getCompoundTag("GRIDMAN_" + i);
			
			System.out.println("Count of nodes to reconstruct: " + gridManCompound.getInteger("nodeCount"));
			int g = 0;
			while(g < gridManCompound.getInteger("nodeCount")){
				System.out.println("Reconstructing node " + g);
				
				NBTTagCompound nodeCompound = gridManCompound.getCompoundTag("NODE_" + g);
				
				String gridType = nodeCompound.getString("nodeGridType");
				World world = DimensionManager.getWorld(nodeCompound.getInteger("nodeDim"));
				BlockPos pos = new BlockPos(nodeCompound.getInteger("nodeX"), nodeCompound.getInteger("nodeY"), nodeCompound.getInteger("nodeZ"));
				
				float resistance = nodeCompound.getFloat("nodeResistance");
				
				float transformationFactor = nodeCompound.getFloat("nodeTransformationFactor");
				
				int id = nodeCompound.getInteger("nodeID");
				
				GridNode node = new GridNode(world, pos, gridType, id, resistance, transformationFactor);
				System.out.println("Reconstructed node: " + node.toString());
				this.addNode(node);
				g++;
			}
			i++;
		}
	}
	
	public NBTTagCompound getSaveData(){
		NBTTagCompound returnCompound = new NBTTagCompound();
		
		int gm = 0;
		for(GridManager manager : GRID_MANAGERS){
			NBTTagCompound innerCompound = new NBTTagCompound();
			int in = 0;
			for(GridNode node : manager.getNodes()){
				NBTTagCompound nodeCompound = new NBTTagCompound();
				BlockPos pos = node.getPos();
				int dim = node.getWorld().provider.getDimension();
				
				nodeCompound.setString("nodeGridType", node.getGridType());
				
				nodeCompound.setInteger("nodeID", node.getID());
				
				nodeCompound.setInteger("nodeDim", dim);
				
				nodeCompound.setInteger("nodeX", pos.getX());
				nodeCompound.setInteger("nodeY", pos.getY());
				nodeCompound.setInteger("nodeZ", pos.getZ());
				
				nodeCompound.setFloat("nodeTransformationFactor", node.getTransformationFactor());
				
				nodeCompound.setFloat("nodeResistance", node.getResistance());
				
				innerCompound.setTag("NODE_" + in, nodeCompound);
				in++;
			}
			innerCompound.setInteger("nodeCount", in);
			returnCompound.setTag("GRIDMAN_" + gm, innerCompound);
			gm++;
		}
		returnCompound.setString("rockycore_DATA", "GRIDDATA");
		returnCompound.setInteger("gridManCount", gm);
		
		return returnCompound;
	}
}
