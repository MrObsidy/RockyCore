package mrobsidy.rockycore.misc;

import mrobsidy.rockycore.misc.backport.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWrapper{
	
	private final Block block;
	private final BlockPos pos;
	
	public BlockWrapper(Block block, BlockPos pos){
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
