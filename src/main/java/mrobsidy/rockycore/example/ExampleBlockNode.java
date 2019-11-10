package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.IGridConsumer;
import mrobsidy.rockycore.gridnetworks.api.implementation.BlockNode;
import mrobsidy.rockycore.gridnetworks.internal.GridNode;
import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//the rest is done automatically 
public class ExampleBlockNode extends BlockNode {

	public ExampleBlockNode(Material materialIn) {
		super(materialIn);
	}

	@Override
	public float getResistance() {
		return 0f;
		//zero resistance wire is spectacular
	}

	@Override
	public String getGridType() {
		return "ELECTRIC";
	}

}
