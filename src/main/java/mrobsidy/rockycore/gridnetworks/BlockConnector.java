package mrobsidy.rockycore.gridnetworks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockConnector extends BlockContainer {
	
	protected BlockConnector(Material materialIn) {
		super(materialIn);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

}
