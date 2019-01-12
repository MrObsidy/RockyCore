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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * 
 * Interface for nodes, implement it and register it in a grid manager
 * 
 * @author MrObsidy
 *
 */
public interface IGridNode {
	
	public float getResistance();
	
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
