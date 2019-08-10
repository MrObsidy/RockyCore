package mrobsidy.rockycore.testing;

import mrobsidy.rockycore.gridnetworks.api.BlockNode;
import mrobsidy.rockycore.misc.Debug;
import mrobsidy.rockycore.misc.backport.BlockPos;
import net.minecraft.block.material.Material;

public class ExampleNode extends BlockNode {

	//Block constructor
	public ExampleNode(Material materialIn, Class gridClass) {
		super(materialIn, gridClass);
	}
	
	//Node constructor
	public ExampleNode(BlockPos pos, int dim){
		super(pos, dim);
	}
	
	@Override
	public float getResistance() {
		return 0;
	}

}
