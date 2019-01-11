/**
 * 
 *  RockyCore
 *  Copyright (C) 2018-2019 MrObsidy
 *  
 *  
 *  This file is part of RockyCore.
 *
 *  RockyCore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  RockyCore is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with RockyCore.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

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
	 *
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
	
	/**
	 * 
	 * Gets the type of grid this Node is registered to [FINAL]
	 * 
	 * @return the Class
	 */
	public final Class getGridClass(){
		return this.gridClass;
	}
	
	/**
	 * 
	 * u no override dis, this method adds this to a grid. [FINAL]
	 * 
	 */
	@Override
	public final void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
		
		if(world.isRemote) return;
		
		if(entity.world.isRemote) return;
		//ALWAYS DOUBLE CHECK AND TRIPLE CHICK.. [cut] CHECK!
		
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

	/**
	 * 
	 * Getter for the position [FINAL]
	 * 
	 */
	@Override
	public final BlockPos getPosition() {
		return this.pos;
	}

	/**
	 * 
	 * Setter for the position
	 * 
	 */
	@Override
	public void setPosition(BlockPos pos) {
		this.pos = pos;
	}


	/**
	 * 
	 * Not used, probably deleted in a future version
	 * 
	 */
	@Deprecated
	@Override
	public void checkGrid() {
		//never used dev method
	}

	/**
	 * 
	 * Getter for the dimension
	 * 
	 */
	@Override
	public int getDimension() {
		return this.dim;
	}

	/**
	 * 
	 * Tick method, default is empty. If you need this, override it.
	 * 
	 */
	@Override
	public void tick() {
		//TODO
	}

	/**
	 * 
	 * Gets whether the side key is connected to anything. [FINAL]
	 * 
	 */
	@Override
	public final boolean getConnectionDirections(EnumFacing key) {
		return this.conDirs.get(key);
	}

	/**
	 * 
	 * This method sets whether this node is a connecting node. [FINAL]
	 * 
	 */
	@Override
	public final void setConnectingNode(boolean isConNode) {
		this.isConnectingNode = isConNode;
	}

	/**
	 * 
	 * Gets whether this node is connecting to others or if it is just a regular cable. [FINAL]
	 * 
	 */
	@Override
	public final boolean getConnectingNode() {
		return this.isConnectingNode;
	}

	/**
	 * 
	 * Sets the connecting status of the Side connecting. [FINAL]
	 * 
	 */
	@Override
	public final void setConnectingDirection(EnumFacing connection, boolean isConnected) {
		this.conDirs.put(connection, isConnected);
	}
	
}
