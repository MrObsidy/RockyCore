package mrobsidy.rockycore.gridnetworks.api;

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
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
		
		if(worldIn.isRemote) return;
		
		Debug.INSTANCE.debug("Removing TileGenerator.. " + pos.toString(), EnumDebugType.DEBUG);
		TileEntityGenerator gen = (TileEntityGenerator) worldIn.getTileEntity(pos);
		RegistryRegistry.getGridManagerRegistry().removeGenerator(gen);
		worldIn.removeTileEntity(pos);
	}
}
