package mrobsidy.rockycore.multiblock.api;

import java.util.ArrayList;

import mrobsidy.rockycore.multiblock.internal.ModularMultiblockRegistry;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblockBase extends IMultiblockPart {
	public String getMultiblockType();
	
	public void setValid(boolean valid);
	
	public boolean isValid();
	
	public ArrayList<IMultiblockComponent> getComponents();
	
	public void addComponent(IMultiblockComponent component);
	
	public void removeComponent(IMultiblockComponent component);
}
