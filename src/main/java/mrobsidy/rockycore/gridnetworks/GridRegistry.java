package mrobsidy.rockycore.gridnetworks;

import java.util.ArrayList;

import mrobsidy.rockycore.misc.Debug;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

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
		
		for(GridManager gridManager : gridManagers){
			NBTTagCompound gridManCompound = new NBTTagCompound();
			for(Grid grid : gridManager.getGrids()){
				NBTTagCompound gridCompound = new NBTTagCompound();
				for(IGridNode node : grid.getNodes()){
					NBTTagCompound nodeCompound = new NBTTagCompound();
					nodeCompound.setString("compoundType", "TYPE_NODE");
					
					nodeCompound.setInteger("nodeX", node.getPosition().getX());
					nodeCompound.setInteger("nodeY", node.getPosition().getY());
					nodeCompound.setInteger("nodeZ", node.getPosition().getZ());
					nodeCompound.setInteger("nodeDim", node.getDimension());
					
					nodeCompound.setInteger("nodeDistToMainNode", node.getDistanceToMainNode());
					nodeCompound.setInteger("nodeGridID", node.getGrid().ID);
					
					nodeCompound.setBoolean("nodeIsMainNode", node.isMainNode());
					
					gridCompound.setTag("node_" + node.getID(), nodeCompound);
				}
				for (IGridUser user : grid.getUsers()){
					NBTTagCompound userCompound = new NBTTagCompound();
					userCompound.setString("compoundType", "TYPE_USER");
					
					userCompound.setInteger("userX", user.getPos().getX());
					userCompound.setInteger("userY", user.getPos().getY());
					userCompound.setInteger("userZ", user.getPos().getZ());
					userCompound.setInteger("userDim", user.getDimension());
					
					userCompound.setInteger("userIOup", user.getIOfunctionForSide(EnumFacing.UP));
					userCompound.setInteger("userIOdown", user.getIOfunctionForSide(EnumFacing.DOWN));
					userCompound.setInteger("userIOnorth", user.getIOfunctionForSide(EnumFacing.NORTH));
					userCompound.setInteger("userIOeast", user.getIOfunctionForSide(EnumFacing.EAST));
					userCompound.setInteger("userIOsouth", user.getIOfunctionForSide(EnumFacing.SOUTH));
					userCompound.setInteger("userIOwest", user.getIOfunctionForSide(EnumFacing.WEST));
					
					userCompound.setInteger("userGridID", user.getGrid().ID);
					gridCompound.setTag("user_" + user.getID(), userCompound);
				}
				
				gridCompound.setInteger("gridID", grid.ID);
				gridManCompound.setTag("grid_" + grid.ID, gridCompound);
			}
			gridManCompound.setInteger("gridManID", gridManager.ID);
			saveCompound.setTag("gridManager_" + gridManager.ID, gridManCompound);
			saveCompound.setString("rockycore_DATA", "gridRegistrySaveData");
		}
		
		return saveCompound;
	}
	
	public void reassembleGrids(NBTTagCompound compound){
		do{
			
			
			
		} while (true);
	}
}
