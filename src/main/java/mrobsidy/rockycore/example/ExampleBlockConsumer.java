package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.IGridConsumer;
import mrobsidy.rockycore.gridnetworks.api.implementation.BlockConsumer;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExampleBlockConsumer extends BlockConsumer {

	public ExampleBlockConsumer(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		ExampleTileConsumer consumer = new ExampleTileConsumer();
		consumer.setWorld(worldIn);
		
		Debug.debug("Created new TileConsumer");
		
		return consumer;
	}

	

}
