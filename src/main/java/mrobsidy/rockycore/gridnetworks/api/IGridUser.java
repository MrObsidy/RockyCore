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


package mrobsidy.rockycore.gridnetworks.api;

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
	public int getDim();
	
	/**
	 * 
	 * Internal use.
	 * 
	 * @param id
	 */
	
	/**
	 * 
	 * Get wheter this User is taking &ltT&gt from the grid, adding &ltT&gt to the
	 * grid or completely ignore &ltT&gt activity on the Grid.
	 * 
	 * @param direction
	 * @return 
	 */
	public int getIOfunctionForSide(EnumFacing direction);
	public void setIOfunctionForSide(EnumFacing direction, int function);
	
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
	
	/**
	 * 
	 * Tick the damn thing. I wouldn't recommend ticking this manually.
	 * Oh yeah, and use Server Ticks.
	 * 
	 */
	public void tick();
}
