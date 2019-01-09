package mrobsidy.rockycore.gridnetworks.api;

import java.lang.reflect.InvocationTargetException;

import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockNode extends Block implements IGridNode {

	public static Material initMaterial;
	
	private BlockPos pos;
	private int dim;
	private int distToMainNode;
	private boolean isMainNode;
	private int ID;
	private static Class gridClass;
	
	/**
	 * 
	 * Block constructor
	 * 
	 * @param materialIn
	 */
	private BlockNode(Material materialIn) {
		super(materialIn);
		initMaterial = materialIn;
	}
	
	public BlockNode(Material materialIn, Class gridClass){
		this(materialIn);
		this.gridClass = gridClass;
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

	public BlockNode(BlockPos pos, int dim){
		this(initMaterial);
		this.pos = pos;
		this.dim = dim;
	}
	
	public Class getGridClass(){
		return this.gridClass;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
		try {
			BlockNode block = this.getClass().getConstructor(BlockPos.class, int.class).newInstance(pos, entity.dimension);
			
			RegistryRegistry.getGridRegistry().getGridManagerForClass(this.gridClass).addNodeToNet(block);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
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
