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

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.api.Grid;
import mrobsidy.rockycore.gridnetworks.api.IGridNode;
import mrobsidy.rockycore.gridnetworks.api.IGridUser;
import mrobsidy.rockycore.misc.Debug;
import mrobsidy.rockycore.misc.MiscUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class GridRegistry {
	private ArrayList<GridManager> gridManagers = new ArrayList<GridManager>();
	
	//public int registerGridManager(Class<? extends Grid> parClass){
	//	GridManager manager = new GridManager(parClass);
	//	return gridManagers.size() - 1;
	//}
	
	public int registerGridManager(GridManager manager){
		gridManagers.add(manager);
		Debug.debug("Registered GridManager");
		return gridManagers.size() - 1;
	}
	
	public GridManager getGridManagerForClass(Class parClass){
		GridManager foundManager = null;
		
		Debug.debug("GridManager for class " + parClass.getName() + " is being searched");
		
		for(GridManager manager : gridManagers){
			if(manager.gridType == parClass){
				foundManager = manager;
			}
		}
		return foundManager;
	}
	
	public ArrayList getGridManagers(){
		return this.gridManagers;
	}
	
	public NBTTagCompound getSaveData(){
		
		NBTTagCompound saveCompound = new NBTTagCompound();
		
		int gm = 0;
		for(GridManager gridManager : gridManagers){
			int g = 0;
			NBTTagCompound gridManCompound = new NBTTagCompound();
			for(Grid grid : gridManager.getGrids()){
				int n = 0;
				int u = 0;
				
				NBTTagCompound gridCompound = new NBTTagCompound();
				for(IGridNode node : grid.getNodes()){
					NBTTagCompound nodeCompound = new NBTTagCompound();
					nodeCompound.setString("compoundType", "TYPE_NODE");
					
					nodeCompound.setInteger("nodeX", node.getPosition().getX());
					nodeCompound.setInteger("nodeY", node.getPosition().getY());
					nodeCompound.setInteger("nodeZ", node.getPosition().getZ());
					nodeCompound.setInteger("nodeDim", node.getDimension());
					
					//nodeCompound.setInteger("nodeDistToMainNode", node.getDistanceToMainNode());
					//nodeCompound.setInteger("nodeGridID", node.getGrid().ID);
					
					//nodeCompound.setBoolean("nodeIsMainNode", node.isMainNode());
					nodeCompound.setString("nodeClasspath", node.getClass().getName());
					
					gridCompound.setTag("node_" + n, nodeCompound);
					n++;
				}
				for (IGridUser user : grid.getUsers()){
					NBTTagCompound userCompound = new NBTTagCompound();
					userCompound.setString("compoundType", "TYPE_USER");
					
					userCompound.setInteger("userX", user.getPos().getX());
					userCompound.setInteger("userY", user.getPos().getY());
					userCompound.setInteger("userZ", user.getPos().getZ());
					userCompound.setInteger("userDim", user.getDim());
					
					userCompound.setInteger("userIOup", user.getIOfunctionForSide(EnumFacing.UP));
					userCompound.setInteger("userIOdown", user.getIOfunctionForSide(EnumFacing.DOWN));
					userCompound.setInteger("userIOnorth", user.getIOfunctionForSide(EnumFacing.NORTH));
					userCompound.setInteger("userIOeast", user.getIOfunctionForSide(EnumFacing.EAST));
					userCompound.setInteger("userIOsouth", user.getIOfunctionForSide(EnumFacing.SOUTH));
					userCompound.setInteger("userIOwest", user.getIOfunctionForSide(EnumFacing.WEST));
					userCompound.setString("userClasspath", user.getClass().getName());
					
					//userCompound.setInteger("userGridID", user.getGrid().ID);
					gridCompound.setTag("user_" + u, userCompound);
					u++;
				}
				gridCompound.setInteger("nodeCount", grid.getNodesSize());
				gridCompound.setInteger("userCount", grid.getUsersSize());
				
				gridCompound.setString("gridClass", grid.getClass().getName());
				
				gridManCompound.setTag("grid_" + g, gridCompound);
			}
			gridManCompound.setInteger("gridManID", gridManager.ID);
			saveCompound.setTag("gridManager_" + gridManager.ID, gridManCompound);
			gridManCompound.setInteger("gridCount", gridManager.getGrids().size());
		}
		saveCompound.setString("rockycore_DATA", "gridRegistrySaveData");
		saveCompound.setInteger("gridManagerCount", gridManagers.size());
		
		return saveCompound;
	}
	
	public void reassembleGrids(NBTTagCompound compound){
		try{
			
			for(int gM = 0; gM == compound.getInteger("gridManagerCount"); gM++){
				NBTTagCompound gridManCompound = compound.getCompoundTag("gridManager_" + gM);
				
				GridManager man = null;
				
				for(int g = 0; g == gridManCompound.getInteger("gridCount"); g++){
					NBTTagCompound gridCompound = gridManCompound.getCompoundTag("grid_" + g);
					
					if (man == null){
						man = new GridManager(MiscUtil.getClassForName(gridCompound.getString("gridClass")));
					}
					
					for(int n = 0; n == gridCompound.getInteger("nodeCount"); n++){
						NBTTagCompound nodeCompound = gridCompound.getCompoundTag("node_" + n);
						
						BlockPos pos = new BlockPos(nodeCompound.getInteger("nodeX"), nodeCompound.getInteger("nodeY"), nodeCompound.getInteger("nodeZ"));
						
						int dim = nodeCompound.getInteger("nodeDim");
						
						//int distToMainNode = nodeCompound.getInteger("distToMainNode");
						
						//boolean isMainNode = nodeCompound.getBoolean("isMainNode");
						
						Class iGridNodeClass = MiscUtil.getClassForName(nodeCompound.getString("nodeClasspath"));
						Constructor iGridNodeClassConstructor = iGridNodeClass.getConstructor(BlockPos.class, int.class);
						
						IGridNode node = (IGridNode) iGridNodeClassConstructor.newInstance(pos, dim);
						//node.setDistanceToMainNode(distToMainNode);
						
						man.addNodeToNet(node);
					}
					
					for(int u = 0; u == gridCompound.getInteger("userCount"); u++){
						NBTTagCompound userCompound = gridCompound.getCompoundTag("user_" + u);
					
						BlockPos pos = new BlockPos(userCompound.getInteger("userX"), userCompound.getInteger("userY"), userCompound.getInteger("userZ"));
						
						int dim = userCompound.getInteger("userDim");
						
						Class iGridUserClass = MiscUtil.getClassForName(userCompound.getString("userClasspath"));
						Constructor iGridUserClassConstructor = iGridUserClass.getConstructor(BlockPos.class, int.class);
						
						IGridUser user = (IGridUser) iGridUserClassConstructor.newInstance(pos, dim);
						
						user.setIOfunctionForSide(EnumFacing.UP, userCompound.getInteger("userIOup"));
						user.setIOfunctionForSide(EnumFacing.DOWN, userCompound.getInteger("userIOdown"));
						user.setIOfunctionForSide(EnumFacing.NORTH, userCompound.getInteger("userIOnorth"));
						user.setIOfunctionForSide(EnumFacing.EAST, userCompound.getInteger("userIOeast"));
						user.setIOfunctionForSide(EnumFacing.SOUTH, userCompound.getInteger("userIOsouth"));
						user.setIOfunctionForSide(EnumFacing.WEST, userCompound.getInteger("userIOwest"));
						
						man.addGridUserToNet(user);
					
					}	
				}
				
				registerGridManager(man);
				
			}
			
		} catch (Exception e){
			Debug.debug("If this is the first time you load this world or if it's after an update that changed anything regarding nodes, ignore this error.");
			e.printStackTrace();
		}
	}
}
