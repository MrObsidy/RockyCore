package mrobsidy.rockycore.example;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.api.TileEntityConsumer;
import mrobsidy.rockycore.gridnetworks.api.TileEntityGenerator;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import net.minecraft.world.World;

/**
 * Example implementation of a TileEntityGenerator
 * 
 * @author alexander
 *
 */
public class ExampleTileGenerator extends TileEntityGenerator{
	
	public ExampleTileGenerator() {
		super();
	}
	
	@Override
	public void handleConnection(TileEntityConsumer consumer) {
		Debug.INSTANCE.debug("Received connection request from " + consumer.toString(), EnumDebugType.INFO);
		connectedConsumers.add(consumer);
	}
	
	public void update(){
		for(TileEntityConsumer consumer : connectedConsumers){
			consumer.receivePower(this.getVoltage(), 1f);
		}
	}

	public float getPowerOutput() {
		return 100f;
		//lets return 100 Watts, cuz why not?
	}

	@Override
	public float getVoltage() {
		return 220;
	}

	public void setWorld(World worldIn) {
		this.world = worldIn;
	}
}
