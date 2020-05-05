package mrobsidy.rockycore.util.misc.helpers;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BlockHelper {
	private final Block block;
	private final BlockPos pos;
	private final TileEntity attatchedTileEntity;
	
	public BlockHelper(Block block, BlockPos pos, TileEntity entity){
		this.block = block;
		this.pos = pos;
		this.attatchedTileEntity = entity;
	}
	
	public Block getBlock(){
		return this.block;
	}
	
	public BlockPos getPos(){
		return this.pos;
	}
	
	public boolean hasTileEntity() {
		return attatchedTileEntity != null;
	}
	
	public TileEntity getTileEntity() {
		return this.attatchedTileEntity;
	}
}
