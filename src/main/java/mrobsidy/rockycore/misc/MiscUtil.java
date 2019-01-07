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
}
