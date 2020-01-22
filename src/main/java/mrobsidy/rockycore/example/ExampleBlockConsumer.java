package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.BlockConsumer;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Example implementation of a consumer
 * 
 * @author alexander
 *
 */
public class ExampleBlockConsumer extends BlockConsumer {

	public ExampleBlockConsumer(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		if(worldIn.isRemote) return null;
		
		ExampleTileConsumer consumer = new ExampleTileConsumer();
		consumer.setWorld(worldIn);
		consumer.setGridType("ELECTRIC");
		
		Debug.INSTANCE.debug("Created new TileConsumer", EnumDebugType.INFO);
		
		return consumer;
	}

	

}
