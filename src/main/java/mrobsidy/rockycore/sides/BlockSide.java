package mrobsidy.rockycore.sides;

import java.util.HashMap;

import net.minecraft.util.math.BlockPos;

public class BlockSide {
	
	private final EnumBlockSide SIDE;
	
	private EnumInteractorSide type;
	
	private final HashMap<String, String> properties = new HashMap<String, String>();
	
	public BlockSide (EnumBlockSide side){
		this.SIDE = side;
		this.type = EnumInteractorSide.IGNORING;
	}
	
	/**
	 * Returns itself to allow for chaining.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public BlockSide setProperty(String key, String value) {
		properties.put(key, value);
		
		return this;
	}
	
	public String getProperty(String key) {
		return this.properties.get(key);
	}
	
	public EnumBlockSide getSide() {
		return this.SIDE;
	}
	
	public void setInteraction(EnumInteractorSide type) {
		this.type = type;
	}
	
	public EnumInteractorSide getInteraction() {
		return this.type;
	}
}