package mrobsidy.rockycore.example;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.api.IGridConsumer;
import mrobsidy.rockycore.gridnetworks.api.implementation.TileEntityGenerator;
import mrobsidy.rockycore.misc.Debug;

public class ExampleTileGenerator extends TileEntityGenerator{
	
	private final ArrayList<IGridConsumer> connectedConsumers = new ArrayList<IGridConsumer>();
	
	public ExampleTileGenerator() {
		super();
	}
	
	@Override
	public void handleConnection(IGridConsumer consumer) {
		Debug.debug("Received connection request from " + consumer.toString());
		connectedConsumers.add(consumer);
	}
	
	@Override
	public void update(){
		for(IGridConsumer consumer : connectedConsumers){
			consumer.receivePower(this.getVoltage(), 1f);
		}
	}

	@Override
	public float getPowerOutput() {
		return 100f;
		//lets return 100 Watts, cuz why not?
	}

	@Override
	public String getGridType() {
		return "ELECTRIC";
	}

	@Override
	public float getVoltage() {
		return 220;
	}
}
