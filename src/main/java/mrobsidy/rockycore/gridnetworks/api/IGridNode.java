package mrobsidy.rockycore.gridnetworks.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IGridNode {
	
	
	/**
	 * 
	 * Gets the position of the node.
	 * 
	 * @return Position of the node
	 */
	public BlockPos getPosition();
	
	public void setPosition(BlockPos pos);
	
	/**
	 * 
	 * Internal use.
	 * 
	 * @param id
	 */
	public void setID(int id);
	public int getID();
	
	/**
	 * 
	 *  This method MUST be called from the constructor. This checks whether it
	 *  creates a new Instance of a Grid or if it adds itself to another grid.
	 *
	 */
	public void checkGrid();
	
	public int getDimension();
	
	public void tick();
	
	public boolean getConnectionDirections(EnumFacing key);
	
	public void setConnectingDirection(EnumFacing connection, boolean isConnected);
	
	public void setConnectingNode(boolean isConNode);
	
	public boolean getConnectingNode();
	
}
