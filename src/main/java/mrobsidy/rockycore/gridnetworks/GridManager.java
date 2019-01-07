package mrobsidy.rockycore.gridnetworks;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import mrobsidy.rockycore.init.RegistryRegistry;
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
			biggestGrid.addNode(node);
		} else {
			try {
				Constructor constr = this.gridType.getConstructor(IGridNode.class, GridManager.class);
				try {
					networks.add((Grid) constr.newInstance(node, this));
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
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