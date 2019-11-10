package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.IGridConsumer;
import mrobsidy.rockycore.gridnetworks.api.IGridGenerator;
import mrobsidy.rockycore.gridnetworks.api.implementation.BlockGenerator;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExampleBlockGenerator extends BlockGenerator {

	public ExampleBlockGenerator(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		ExampleTileGenerator generator = new ExampleTileGenerator();
		generator.setWorld(worldIn);
		RegistryRegistry.getGridManagerRegistry().relayPacket(generator.getGridType());
		
		Debug.debug("Created new TileGenerator");
		
		return generator;
	}
}
