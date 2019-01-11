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

public abstract class BlockUser extends Block implements IGridUser {

	private static Material thisMaterial;
	private static Class gridClass;
	private BlockPos pos;
	private int dim;
	
	private HashMap<EnumFacing, Integer> IOFunctionForSide = new HashMap<EnumFacing, Integer>();
	private boolean setOrphan;
	
	/**
	 * 
	 * Constructor for Minecraft, this is to construct the Block object
	 * 
	 * @param materialIn
	 */
	private BlockUser(Material materialIn) {
		super(materialIn);
		this.thisMaterial = materialIn;
	}
	
	/**
	 * 
	 * Constructor for IGridUser. This will be called everytime 
	 * 
	 * @param pos
	 * @param dim
	 */
	
	public BlockUser(BlockPos pos, int dim){
		this(thisMaterial);
		this.pos = pos;
		this.dim = dim;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
		BlockUser block;
		try {
			block = this.getClass().getConstructor(BlockPos.class, int.class).newInstance(pos, entity.dimension);

			RegistryRegistry.getGridRegistry().getGridManagerForClass(this.gridClass).addGridUserToNet(block);
		
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
	public BlockPos getPos() {
		return this.pos;
	}

	@Override
	public int getDim() {
		return this.dim;
	}

	@Override
	public int getIOfunctionForSide(EnumFacing direction) {
		return this.IOFunctionForSide.get(direction).intValue();
	}

	@Override
	public void setIOfunctionForSide(EnumFacing direction, int function) {
		this.IOFunctionForSide.put(direction, new Integer(function));
	}

	@Override
	public void setOrphan(boolean isOrphan) {
		this.setOrphan = isOrphan;
	}

	@Override
	public void tick() {
		
	}

}
