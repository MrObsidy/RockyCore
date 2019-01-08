package mrobsidy.rockycore.gridnetworks;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import mrobsidy.rockycore.misc.MiscUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class GridManager{
	private ArrayList<Grid> networks = new ArrayList<Grid>();
	
	public Class gridType;
	
	public final int ID;
	
	public GridManager(Class<? extends Grid> gridType){
		this.gridType = gridType;
		this.ID = RegistryRegistry.getGridRegistry().registerGridManager(this);
	}
	
	/**
	 * 
	 * Keep in mind that it's your job to reassemble the data on your own.
	 * 
	 * @param networks
	 * @return a new GridManager with everything added into it.
	 */
	public static GridManager reconstruct(ArrayList<Grid> networks, ArrayList<IGridNode> nodes, ArrayList<IGridUser> users){
		GridManager thisGM = new GridManager(networks.get(0).getClass());
		thisGM.rebuild(networks);
		return thisGM;
	}
	
	public Class getManagerClass(){
		return this.gridType;
	}
	
	public ArrayList<Grid> getGrids(){
		return this.networks;
	}
	
	private void rebuild(ArrayList<Grid> networks){
		this.networks = networks;
	}
	
	public int register(Grid grid){
		networks.add(grid);
		return networks.size() - 1;
	}
	
	public void addNodeToNet(IGridNode node){
		BlockPos blockPos = node.getPosition();	
		int dim = node.getDimension();
		
		Debug.debug("Initiating node adding method");
		
		Block[] surBl = MiscUtil.getSurroundingBlocks(blockPos, dim);
		
		ArrayList<Grid> grids = new ArrayList<Grid>();
		
		boolean gridWasFound = false;
		
		for(Block block : surBl){
			if(block instanceof IGridNode){
				grids.add(((IGridNode) block).getGrid());
				gridWasFound = true;
			}
		}
		
		Debug.debug("Grid was found: " + gridWasFound);
		
		if(gridWasFound && grids.size() == 1){
			Grid biggestGrid = null;
			int biggestGridSize = 0;
				
			for(Grid grid : grids){
				if(grid.getSize() > biggestGridSize){
					biggestGrid = grid;
					biggestGridSize = grid.getSize();
				}
			}
			biggestGrid.addNode(node);
			Debug.debug("Added node " + node.getID() + " to Grid " + biggestGrid.ID);
		} else if (gridWasFound && grids.size() < 1){
			//TODO make this function
		} else {
			try {
				Constructor constr = this.gridType.getConstructor(IGridNode.class);
				try {
					Grid newGrid = (Grid) constr.newInstance(node);
					networks.add(newGrid);
					Debug.debug("Created a new network " + networks.get(networks.size() - 1));
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			//networks.add();
		}
	}
	
	public void addGridUserToNet(IGridUser user){
		user.setOrphan(false);
		
		BlockPos blockPos = user.getPos();	
		int dim = user.getDimension();
		
		Block[] surBl = MiscUtil.getSurroundingBlocks(blockPos, dim);
		
		ArrayList<Grid> grids = new ArrayList<Grid>();
		
		boolean gridWasFound = false;
		
		for(Block block : surBl){
			if(block instanceof IGridNode){
				grids.add(((IGridNode) block).getGrid());
				gridWasFound = true;
			}
		}
		
		if(gridWasFound){
			Grid biggestGrid = null;
			int biggestGridSize = 0;
				
			for(Grid grid : grids){
				if(grid.getSize() > biggestGridSize){
					biggestGrid = grid;
					biggestGridSize = grid.getSize();
				}
			}
			biggestGrid.addUser(user);
		} else {
			user.setOrphan(true);
		}
	}
	
	/*public NBTTagCompound SaveToDisk(){
		
	} */
}