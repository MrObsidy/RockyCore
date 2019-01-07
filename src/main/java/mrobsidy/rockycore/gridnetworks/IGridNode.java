package mrobsidy.rockycore.gridnetworks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public interface IGridNode {
	/**
	 * 
	 * Set the distance to main node of the network
	 * 
	 * @param dist
	 */
	public void setDistanceToMainNode(int dist);
	
	/**
	 * 
	 * Get the distance to the main node of the network
	 * 
	 * @return
	 */
	public int getDistanceToMainNode();
	
	
	/**
	 * 
	 * Gets the position of the node.
	 * 
	 * @return Position of the node
	 */
	public BlockPos getPosition();
	
	
	/**
	 * 
	 *  This method MUST be called from the constructor. This checks whether it
	 *  creates a new Instance of a Grid or if it adds itself to another grid.
	 *
	 */
	public void checkGrid();
	
	public int getDimension();
	
	public void setGrid(Grid grid);
	
	public Grid getGrid();
	
	public void tick();
	
	public boolean isMainNode();

	
	/**
	 * 
	 * INTERNAL USE ONLY! If you mess with this, you'll mess everything up.
	 * @deprecated
	 * 
	 */
	public void setMainNode();
}
