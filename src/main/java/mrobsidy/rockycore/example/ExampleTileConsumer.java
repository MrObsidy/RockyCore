package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.TileEntityConsumer;
import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;

/**
 * 
 * Example implementation of a TileEntityConsumer
 * 
 * @author alexander
 *
 */
public class ExampleTileConsumer extends TileEntityConsumer {
	
	public ExampleTileConsumer() {
		super();
	}
	
	public float receivePower(float voltage, float current) {
		return 0;
	}

	public void processPacket(GridPacket packet){
		Debug.INSTANCE.debug("Received packet: " + packet.toString() + " from " + packet.getGenerator().toString(), EnumDebugType.DEBUG);
		packet.getGenerator().handleConnection(this);
	}
	
	@Override
	public void update() {
		//do stuff
	}

	public void establishConnection() {
		
	}

}
