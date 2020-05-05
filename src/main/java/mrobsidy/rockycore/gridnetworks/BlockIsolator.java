package mrobsidy.rockycore.gridnetworks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockIsolator extends BlockContainer {
	
	protected BlockIsolator(Material materialIn) {
		super(materialIn);
	}
	
	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);
	
}
