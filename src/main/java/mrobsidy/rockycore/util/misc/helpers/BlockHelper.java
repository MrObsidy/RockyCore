package mrobsidy.rockycore.util.misc.helpers;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockHelper {
	private final Block block;
	private final BlockPos pos;
	
	public BlockHelper(Block block, BlockPos pos){
		this.block = block;
		this.pos = pos;
	}
	
	public Block getBlock(){
		return this.block;
	}
	
	public BlockPos getPos(){
		return this.pos;
	}
}
