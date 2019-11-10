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

package mrobsidy.rockycore.gridnetworks.api.implementation;

import mrobsidy.rockycore.gridnetworks.internal.GridManagerRegistry;
import mrobsidy.rockycore.gridnetworks.internal.GridNode;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A normal cable. No transformation or anything, just cable.
 * 
 * @author alexa
 *
 */
public abstract class BlockNode extends Block {
	
	private final float resistance;
	private final String gridType;
	
	public BlockNode(Material materialIn) {
		super(materialIn);
		resistance = this.getResistance();
		gridType = this.getGridType();
	}
	
	/**
	 * 
	 * If you need to, override this with onBlockAdded(...) { super.onBlockPlaced(...); }
	 * 
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		if(worldIn.isRemote) return;
		
		Debug.debug("Adding a node at " + pos.toString());
		
		RegistryRegistry.getGridManagerRegistry().addNode(new GridNode(worldIn, pos, gridType, RegistryRegistry.getGridManagerRegistry().getNextID(this.gridType), resistance, 1f));
		RegistryRegistry.getGridManagerRegistry().relayPacket(this.gridType);
	}
	
	/**
	 * Most finnicky way in the world to initialize final variables in an abstract method
	 * 
	 * @return
	 */
	public abstract float getResistance();
	
	/**
	 * Most finnicky way in the world to initialize final variables in an abstract method
	 * 
	 * @return
	 */
	public abstract String getGridType();
	
	/**
	 * If you need to, override this with breakBlock(...) { super.breakBlock(...); }
	 * 
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {		
		GridNode nodeHere = RegistryRegistry.getGridManagerRegistry().getNodeAtPos(this.getGridType(), pos, worldIn);
				
		Debug.debug("Node at " + nodeHere.getPos().toString() + " is being removed");
		
		RegistryRegistry.getGridManagerRegistry().removeNode(nodeHere);
	}
}
