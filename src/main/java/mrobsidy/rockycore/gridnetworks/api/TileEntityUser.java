package mrobsidy.rockycore.gridnetworks.api;

import java.util.HashMap;

import mrobsidy.rockycore.misc.backport.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityUser extends TileEntity implements IGridUser {

	private HashMap<EnumFacing, Integer> directions = new HashMap<EnumFacing, Integer>();
	
	private int INPUT_VOLTAGE;
	private int INPUT_AMPERAGE;
	
	private boolean orphan = false;
	
	@Override
	public int getDim() {
		return this.worldObj.provider.dimensionId;
	}

	@Override
	public BlockPos getPos(){
		return new BlockPos(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public int getInputFunctionForSide(EnumFacing direction) {
		return directions.get(direction);
	}

	@Override
	public void setInputFunctionForSide(EnumFacing direction, int function) {
		directions.put(direction, function);
	}

	@Override
	public int getInputVoltage() {
		return this.INPUT_VOLTAGE;
	}

	@Override
	public int getInputAmperage() {
		return this.INPUT_AMPERAGE;
	}

	@Override
	public void setOrphan(boolean isOrphan) {
		this.orphan = true;
	}
	
}
