package mrobsidy.rockycore.gridnetworks.api;

import mrobsidy.rockycore.misc.backport.BlockPos;
import net.minecraft.util.EnumFacing;

public interface IGridGenerator {
	
	public static final int IO_INDIFFERENT = 0;
	public static final int IO_OUTPUT = 1;
	
	/**
	 * 
	 * Gets the position. Different method names so that one can implement
	 * user and generator at the same time (aka Storage etc.)
	 * 
	 * @return
	 */
	public BlockPos getPosit();
	
	/**
	 * 
	 * Gets the dimension. Different method names so that one can implement
	 * user and generator at the same time (aka Storage etc.)
	 * 
	 * @return
	 */
	public int getDimen();
	
	/**
	 * 
	 * Gets whether this side outputs or does nothing.
	 * 
	 * @param direction
	 * @return
	 */
	public int getOutputFunctionForSide(EnumFacing direction);
	
	/**
	 * 
	 * Sets whether this side outputs or does nothing.
	 * 
	 * @param direction
	 * @param function
	 */
	public void setOutputFunctionForSide(EnumFacing direction, int function);
	
	/**
	 * 
	 * Gets the voltage this thing outputs.
	 * 
	 * @return
	 */
	public int getOutputVoltage();
	
	/**
	 * 
	 * Gets the amperage this thing outputs.
	 * 
	 * @return
	 */
	public int getOutputAmperage();
	
	
}
