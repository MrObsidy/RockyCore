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

/**
 * 
 * The base class for all Consumer block containers. Extend this for custom generating blocks.
 * 
 * 
 * All consumers that use the GridManager system must extend this, otherwise, they are not registered properly.
 * 
 * @author mrobsidy
 *
 */
public abstract class BlockConsumer extends BlockContainer {
	
	/**
	 * Internal use only
	 * 
	 * @deprecated
	 * @param materialIn
	 */
	protected BlockConsumer(Material materialIn) {
		super(materialIn);
	}

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);
	
	@Override
	/**
	 * If you override this, make sure to call super.breakBlock();
	 * 
	 * 
	 */
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		if(worldIn.isRemote) return;
		
		Debug.getDebugger().debug("Removing TileConsumer.. " + pos.toString(), EnumDebugType.INFO);
		TileEntityConsumer con = (TileEntityConsumer) worldIn.getTileEntity(pos);
		RegistryRegistry.getGridManagerRegistry().removeConsumer(con);
		worldIn.removeTileEntity(pos);
	}

}
