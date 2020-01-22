package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.BlockNode;
import mrobsidy.rockycore.gridnetworks.api.TileGridNode;
import mrobsidy.rockycore.misc.debug.Debug;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Example implementation of a node
 * 
 * @author alexander
 *
 */
public class ExampleBlockNode extends BlockNode {

	public ExampleBlockNode(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
	
		if(worldIn.isRemote) return null;
		
		TileGridNode node = new TileGridNode();
		node.setGridType("ELECTRIC");
		node.setWorld(worldIn);
		return node;
	}

}
