package mrobsidy.rockycore.gridnetworks.api;

import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import net.minecraft.util.math.BlockPos;

public interface IGridConsumer {
	public String getGridType();

	public void processPacket(GridPacket gridPacket);

	public BlockPos getPos();
	
	public void establishConnection();
	
	public float receivePower(float voltage, float current);
}
