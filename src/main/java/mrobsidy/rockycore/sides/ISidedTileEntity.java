package mrobsidy.rockycore.sides;

public interface ISidedTileEntity {
	public BlockSide getSideFunctions(EnumBlockSide side);
	
	public void setSideFunctions(BlockSide side);
}