package mrobsidy.rockycore.gridnetworks.api;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
	private HashMap<EnumFacing, Boolean> conDirs = new HashMap<EnumFacing, Boolean>();
	private boolean isConnectingNode;
	
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
	
	/**
	 * 
	 * Use this when registering this BlockNode to the Minecraft Block Registry
	 * 
	 * @param materialIn
	 * @param gridClass
	 */
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
		this.conDirs.put(EnumFacing.UP, new Boolean(false));
		this.conDirs.put(EnumFacing.DOWN, new Boolean(false));
		this.conDirs.put(EnumFacing.NORTH, new Boolean(false));
		this.conDirs.put(EnumFacing.EAST, new Boolean(false));
		this.conDirs.put(EnumFacing.SOUTH, new Boolean(false));
		this.conDirs.put(EnumFacing.WEST, new Boolean(false));
		
	}
	
	public Class getGridClass(){
		return this.gridClass;
	}
	
	/**
	 * 
	 * u no override dis, this method adds this to a grid.
	 * 
	 */
	@Override
	public final void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
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
		//TODO
	}

	@Override
	public boolean getConnectionDirections(EnumFacing key) {
		return this.conDirs.get(key);
	}

	@Override
	public void setConnectingNode(boolean isConNode) {
		this.isConnectingNode = isConNode;
	}

	@Override
	public boolean getConnectingNode() {
		return this.isConnectingNode;
	}

	@Override
	public void setConnectingDirection(EnumFacing connection, boolean isConnected) {
		this.conDirs.put(connection, isConnected);
	}
	
}
