package mrobsidy.rockycore.gridnetworks.api;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityGenerator extends TileEntity implements IGridGenerator, ITickable {

	private HashMap<EnumFacing, Integer> directions = new HashMap<EnumFacing, Integer>();
	
	private int OUTPUT_VOLTAGE;
	private int OUTPUT_AMPERAGE;
	
	public TileEntityGenerator(World world){
		
	}
	
	@Override
	public abstract void update();

	@Override
	public BlockPos getPosit() {
		return this.pos;
	}

	@Override
	public int getDimen() {
		return this.world.provider.getDimension();
	}

	@Override
	public int getOutputFunctionForSide(EnumFacing direction) {
		return this.directions.get(direction);
	}

	@Override
	public void setOutputFunctionForSide(EnumFacing direction, int function) {
		this.directions.put(direction, function);
	}

	@Override
	public int getOutputVoltage() {
		return OUTPUT_VOLTAGE;
	}

	@Override
	public int getOutputAmperage() {
		return OUTPUT_AMPERAGE;
	}
	
}
