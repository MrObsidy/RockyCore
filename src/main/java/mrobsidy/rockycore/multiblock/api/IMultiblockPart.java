package mrobsidy.rockycore.multiblock.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblockPart {
	public void setOrphan(boolean state);
	
	public boolean getOrphan();
	
	public BlockPos getPos();
	
	public World getWorld();
	
	public boolean getRemoved();
	
	/**
	 * this is a one-way road
	 */
	public void setRemoved();
}
