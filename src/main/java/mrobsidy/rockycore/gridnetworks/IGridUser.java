package mrobsidy.rockycore.gridnetworks;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IGridUser {
	
	public static final int IO_INDIFFERENT = 0;
	public static final int IO_OUTPUT = 1;
	public static final int IO_INPUT = 2;
	
	/**
	 * 
	 * Other name for implementation reasons
	 * 
	 * @return the Position of the Block
	 */
	public BlockPos getPos();

	/**
	 * 
	 * Get the dimension this User is in
	 * 
	 * @return
	 */
	public int getDimension();
	
	/**
	 * 
	 * Get wheter this User is taking &ltT&gt from the grid, adding &ltT&gt to the
	 * grid or completely ignore &ltT&gt activity on the Grid.
	 * 
	 * @param direction
	 * @return 
	 */
	public int getIOfunctionForSide(EnumFacing direction);
	
	/**
	 * 
	 * Yes, this is public. But still, don't use it unless you *really*
	 * know what you're doing.
	 * 
	 * If you're still curios:
	 * 
	 * This sets whether this User is connected to a grid. Used to stop ticking
	 * madness
	 * 
	 */
	public void setOrphan(boolean isOrphan);
	
	public void setGrid(Grid grid);
	
	public Grid getGrid();
	
	/**
	 * 
	 * Tick the damn thing. I wouldn't recommend ticking this manually.
	 * Oh yeah, and use Server Ticks.
	 * 
	 */
	public void tick();
}
