package mrobsidy.rockycore.test;

import mrobsidy.rockycore.gridnetworks.Grid;
import mrobsidy.rockycore.gridnetworks.IGridNode;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.util.math.BlockPos;

public class ReferenceImplIGridNode implements IGridNode {

	private int distanceToMainNode;
	private final BlockPos position;
	private int ID;
	private int dimension;
	private Grid grid;
	private boolean mn;
	
	public ReferenceImplIGridNode(BlockPos pos, int dim){ //USUALLY, this would be a block. From there, you can get your blockpos etc.
		this.position = pos;
		this.dimension = dim;
	}
	
	@Override
	public void setDistanceToMainNode(int dist) {
		this.distanceToMainNode = dist;
	}

	@Override
	public int getDistanceToMainNode() {
		return distanceToMainNode;
	}

	@Override
	public BlockPos getPosition() {
		return this.position;
	}

	@Override
	public void setID(int id) {
		this.ID = id;
	}

	@Override
	public int getID() {
		Debug.debug("ID of this Node: " + this.ID);
		return this.ID;
	}

	@Override
	public void checkGrid() {
		Debug.debug("Woo, magic dev method!");
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public void setGrid(Grid grid) {
		this.grid = grid;
		Debug.debug("Grid set for node " + ID + ": " + grid);
	}

	@Override
	public Grid getGrid() {
		return this.grid;
	}

	@Override
	public void tick() {
		// TODO Make this function
	}

	@Override
	public boolean isMainNode() {
		return this.mn;
	}

	@Override
	public void setMainNode() {
		this.mn = !this.mn;
	}
	
}
