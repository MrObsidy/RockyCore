package mrobsidy.rockycore.util.misc.helpers;

import net.minecraft.util.math.BlockPos;

public class BlockPosHelper {
	public static boolean comparePositions(BlockPos pos1, BlockPos pos2, int dim1, int dim2) {
		boolean result = false;
		
		if (pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ() && dim1 == dim2) {
			result = true;
		}
		
		return result;
	}
}
