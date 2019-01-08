package mrobsidy.rockycore.gridnetworks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;

public abstract class BlockNode extends Block implements IGridNode {

	public static Material initMaterial;
	
	private BlockPos pos;
	private int dim;
	private int distToMainNode;
	private boolean isMainNode;
	private int ID;
	private Grid grid;
	
	/**
	 * 
	 * Block constructor
	 * 
	 * @param materialIn
	 */
	public BlockNode(Material materialIn) {
		super(materialIn);
		initMaterial = materialIn;
	}
	
	/**
	 * 
	 * IGridNode constructor. ALL IGRIDNODE CONSTRUCTORS MUST HAVE THIS!
	 * 
	 * @param pos - the position of the node
	 * @param dim - the dimension of the node
	 * @param distToMainNode - distance to the main node of the grid
	 * @param isMainNode - whether this is the main node
	 */

	public BlockNode(BlockPos pos, int dim, int distToMainNode, boolean isMainNode){
		super(initMaterial);
		this.pos = pos;
		this.dim = dim;
		this.distToMainNode = distToMainNode;
		this.isMainNode = isMainNode;
	}
	
	@Override
	public void setDistanceToMainNode(int dist) {
		this.distToMainNode = dist;
	}

	@Override
	public int getDistanceToMainNode() {
		return this.distToMainNode;
	}

	@Override
	public BlockPos getPosition() {
		return this.pos;
	}

	@Override
	public void setPosition(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public void setID(int id) {
		this.ID = id;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void checkGrid() {
		//never used dev method
	}

	@Override
	public int getDimension() {
		return this.dim;
	}

	@Override
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@Override
	public Grid getGrid() {
		return this.grid;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public boolean isMainNode() {
		return this.isMainNode;
	}

	@Override
	public void setMainNode() {
		this.isMainNode = true;
	}
	
}
