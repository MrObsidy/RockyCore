package mrobsidy.rockycore.util.misc.helpers;

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.sides.EnumBlockSide;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class BlockUtil {
	public static boolean comparePositions(BlockPos pos1, BlockPos pos2, int dim1, int dim2) {
		boolean result = false;
		
		if (pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ() && dim1 == dim2) {
			result = true;
		}
		
		return result;
	}
	
	public static BlockHelper getBlockHelperAt(BlockPos pos, WorldServer world) {
		Block blockAt = world.getBlockState(pos).getBlock();
		TileEntity ent = world.getTileEntity(pos);
		return new BlockHelper(blockAt, pos, ent);
	}
	
	public static BlockHelper[] getSurroundingBlocks(BlockPos position, WorldServer world) {
		BlockHelper[] list = new BlockHelper[6];		
		
		list[0] = getBlockAboveOf(position, world);
		list[1] = getBlockBelowOf(position, world);
		list[2] = getBlockWestOf(position, world);
		list[3] = getBlockEastOf(position, world);
		list[4] = getBlockNorthOf(position, world);
		list[5] = getBlockSouthOf(position, world);
		
		return list;
	}
	
	public static BlockHelper getBlockAboveOf(BlockPos pos, WorldServer world) {
		return getBlockHelperAt(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), world);
	}
	
	public static BlockHelper getBlockBelowOf(BlockPos pos, WorldServer world) {
		return getBlockHelperAt(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), world);
	}
	
	public static BlockHelper getBlockWestOf(BlockPos pos, WorldServer world) {
		return getBlockHelperAt(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()), world);
	}
	
	public static BlockHelper getBlockEastOf(BlockPos pos, WorldServer world) {
		return getBlockHelperAt(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()), world);
	}
	
	public static BlockHelper getBlockNorthOf(BlockPos pos, WorldServer world) {
		return getBlockHelperAt(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1), world);
	}
	
	public static BlockHelper getBlockSouthOf(BlockPos pos, WorldServer world) {
		return getBlockHelperAt(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1), world);
	}
	
	
	/**
	 * Returns the blockside of the Block at facing which faces facer. (Or through which side of the block you would enter
	 * if you were to enter from facer to facing in a straight line). This method returns undefined behaviour if facer and
	 * facing are not directly bordering!
	 * 
	 * @param facer
	 * @param facing
	 * @return
	 */
	public static EnumBlockSide getBlockSideFromTwoPositions(BlockPos facer, BlockPos facing) {
		EnumBlockSide blockSide = null;
		
		if(!(facer.getX() == facing.getX())) {
			if(facer.getX() < facing.getX()) {
				blockSide = EnumBlockSide.WEST;
			} else {
				blockSide = EnumBlockSide.EAST;
			}
		} else if (!(facer.getY() == facing.getY())) {
			if(facer.getY() < facing.getY()) {
				blockSide = EnumBlockSide.DOWN;
			} else {
				blockSide = EnumBlockSide.UP;
			}
		} else if (!(facer.getZ() == facing.getZ())) {
			if(facer.getZ() < facing.getZ()) {
				blockSide = EnumBlockSide.NORTH;
			} else {
				blockSide = EnumBlockSide.SOUTH;
			}
		}
		
		return blockSide;
	}
}