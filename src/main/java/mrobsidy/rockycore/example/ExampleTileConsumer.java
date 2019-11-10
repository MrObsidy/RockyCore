package mrobsidy.rockycore.example;

import mrobsidy.rockycore.gridnetworks.api.implementation.TileEntityConsumer;
import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import mrobsidy.rockycore.misc.Debug;

public class ExampleTileConsumer extends TileEntityConsumer {

	@Override
	public float receivePower(float voltage, float current) {
		return 0;
	}

	@Override
	public void processPacket(GridPacket packet){
		Debug.debug("Received packet: " + packet.toString() + " from " + packet.getGenerator().toString());
	}
	
	@Override
	public void update() {
		//do stuff
	}

	@Override
	public String getGridType() {
		return "ELECTRIC";
	}

	@Override
	public void establishConnection() {
		
	}

}
