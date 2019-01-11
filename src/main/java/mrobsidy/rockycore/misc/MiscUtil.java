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


package mrobsidy.rockycore.misc;

import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiscUtil {
	public static World getServerWorld(int dim){
		return RegistryRegistry.getServerRegistry().getServer().getWorld(dim);
	}
	
	public static Block getBlockAtPos(BlockPos pos, int dim){
		return getServerWorld(dim).getBlockState(pos).getBlock();
	}
	
	public static Block[] getSurroundingBlocks(BlockPos blockPos, int dim){
		Block[] surroundingBlocks = new Block[6];
		
		surroundingBlocks[0] = getBlockAtPos(new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ()), dim);
		surroundingBlocks[1] = getBlockAtPos(new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ()), dim);
		surroundingBlocks[2] = getBlockAtPos(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()), dim);
		surroundingBlocks[3] = getBlockAtPos(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ()), dim);
		surroundingBlocks[4] = getBlockAtPos(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1), dim);
		surroundingBlocks[5] = getBlockAtPos(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1), dim);

		return surroundingBlocks;
	}
	
	public static Class getClassForName(String name) throws ClassNotFoundException{
		return Class.forName(name);
	}
}
