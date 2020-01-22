package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.BlockGenerator;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * Example implementation of a Generator
 * 
 * @author alexander
 *
 */
public class ExampleBlockGenerator extends BlockGenerator {

	public ExampleBlockGenerator(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		if(worldIn.isRemote) return null;
		
		ExampleTileGenerator generator = new ExampleTileGenerator();
		generator.setWorld(worldIn);
		generator.setGridType("ELECTRIC");
		
		Debug.INSTANCE.debug("Created new TileGenerator", EnumDebugType.INFO);
		
		return generator;
	}
}
