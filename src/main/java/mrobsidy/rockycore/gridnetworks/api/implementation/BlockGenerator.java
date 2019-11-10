package mrobsidy.rockycore.gridnetworks.api.implementation;

import mrobsidy.rockycore.gridnetworks.api.IGridConsumer;
import mrobsidy.rockycore.gridnetworks.api.IGridGenerator;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockGenerator extends BlockContainer {

	protected BlockGenerator(Material materialIn) {
		super(materialIn);
	}

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		Debug.debug("Removing TileGenerator.. " + pos.toString());
		RegistryRegistry.getGridManagerRegistry().removeGenerator((IGridGenerator) worldIn.getTileEntity(pos));
	}
}
