package mrobsidy.rockycore.gridnetworks;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class GridRegistry {
	private ArrayList<GridManager> gridManagers = new ArrayList<GridManager>();
	
	public int registerGridManager(Class<? extends Grid> parClass){
		gridManagers.add(new GridManager(parClass));
		return gridManagers.size() - 1;
	}
	
	public int registerGridManager(GridManager manager){
		gridManagers.add(manager);
		return gridManagers.size() - 1;
	}
	
	public ArrayList getGridManagers(){
		return this.gridManagers;
	}
	
	public ArrayList<NBTTagCompound> getSaveData(){
		
		ArrayList<NBTTagCompound> saveCompound = new ArrayList<NBTTagCompound>();
		
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
					
					saveCompound.add(nodeCompound);
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
					saveCompound.add(userCompound);
				}
				
				gridCompound.setInteger("gridID", grid.ID);
				saveCompound.add(gridCompound);
			}
			gridManCompound.setInteger("gridManID", gridManager.ID);
			saveCompound.add(gridManCompound);
		}
		
		return saveCompound;
	}
	
	public void reassembleGrids(NBTTagCompound compound){
		
	}
}
