package mrobsidy.rockycore.multiblock.internal;

import mrobsidy.rockycore.multiblock.api.IMultiblockBase;
import mrobsidy.rockycore.multiblock.api.IMultiblockComponent;
import net.minecraft.util.math.BlockPos;

public class ComponentWrapper {
	
	private final BlockPos position;
	private final int dim;
	private final String multiblockType;
	private final IMultiblockComponent wrappedComponent;
	private final IMultiblockBase wrappedBase;
	
	public ComponentWrapper(BlockPos pos, int dim, String type, IMultiblockComponent wrap, IMultiblockBase wrappedBase) {
		this.position = pos;
		this.dim = dim;
		this.multiblockType = type;
		this.wrappedComponent = wrap;
		this.wrappedBase = wrappedBase;
	}
	
	public boolean isWrappedBase() {
		if(this.wrappedBase != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isWrappedComponent() {
		return !isWrappedBase();
	}
	
	public IMultiblockBase getWrappedBase() {
		return this.wrappedBase;
	}
	
	public IMultiblockComponent getWrappedComponent() {
		return this.wrappedComponent;
	}
	
	public BlockPos getPos() {
		return this.position;
	}
	
	public int getDimension() {
		return this.dim;
	}
	
	public String getMultiblockType() {
		return this.multiblockType;
	}
}
