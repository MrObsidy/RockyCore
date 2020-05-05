package mrobsidy.rockycore.multiblock.internal;

import java.util.ArrayList;
import java.util.HashMap;

import mrobsidy.rockycore.misc.MiscUtil;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.multiblock.api.IMultiblockBase;
import mrobsidy.rockycore.multiblock.api.IMultiblockComponent;
import mrobsidy.rockycore.util.misc.helpers.BlockHelper;
import mrobsidy.rockycore.util.misc.helpers.BlockUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class ModularMultiblockRegistry {
	
	/**
	 * 
	 * 
	 * @author mrobsidy
	 *
	 */
	private class MultiblockContainer {
		private IMultiblockBase base;
		
		private int dim;
		
		public final ArrayList<IMultiblockComponent> invalidatingComponents = new ArrayList<IMultiblockComponent>();
		
		public final ArrayList<IMultiblockBase> invalidatingBases = new ArrayList<IMultiblockBase>();
		
		private final ArrayList<IMultiblockComponent> components = new ArrayList<IMultiblockComponent>();
		
		public boolean isValid() {
			return this.invalidatingBases.size() == 0 && this.invalidatingComponents.size() == 0;
		}
		
		public void updateValidity() {
			if(isValid() && this.hasBase()) this.base.setValid(true);
		}
		
		public ArrayList<IMultiblockComponent> getComponents(){
			return this.components;
		}
		
		public ArrayList<BlockPos> getPositions(){
			ArrayList<BlockPos> returnList = new ArrayList<BlockPos>();
			
			for(IMultiblockComponent component : this.components) {
				returnList.add(component.getPos());
			}
		
			if(this.hasBase()) returnList.add(this.base.getPos());
			
			return returnList;
		}
		
		public void addComponent(IMultiblockComponent component) {
			components.add(component);
		}
		
		public void removeComponentAtPos(BlockPos pos) {
			IMultiblockComponent toRemove = null;
			
			for(IMultiblockComponent component : this.components) {
				if(BlockUtil.comparePositions(component.getPos(), pos, this.dim, this.dim)) {
					toRemove = component;
				}
			}
			
			if(toRemove != null) {
				this.components.remove(toRemove);
			} else {
				throw new IllegalStateException("This piece of code should never be reachable. If this happens, something modified this MultiblockContainer during a IMutiblockComponent removal operation. This should never happen and is not an issue of RockyCore. Please check the StackTrace.");
			}
		}
		
		public IMultiblockBase getBase() {
			return this.base;
		}
		
		
		public void setBase(IMultiblockBase base) {
			this.base = base;
		}
		
		public boolean hasBase() {
			return base != null;
		}
		
		public void setDim(int dim) {
			this.dim = dim;
		}
		
		public int getDim() {
			return this.dim;
		}
	}
	
	private final ArrayList<MultiblockContainer> multiblocks = new ArrayList<MultiblockContainer>(); 
	
	public void addComponent(IMultiblockComponent comp) {
		Debug.getDebugger().debug("Adding Component to Multiblock structure..", EnumDebugType.DEBUG);
		
		ArrayList<MultiblockContainer> foundContainers = new ArrayList<MultiblockContainer>();
		
		BlockHelper[] surrPos = BlockUtil.getSurroundingBlocks(comp.getPos(), (WorldServer) comp.getWorld());
		
		for(MultiblockContainer cont : this.multiblocks) {
			for(BlockPos pos : cont.getPositions()) {
				for(BlockHelper wrap : surrPos) {
					if(BlockUtil.comparePositions(wrap.getPos(), pos, comp.getWorld().provider.getDimension(), cont.getDim())) {
						foundContainers.add(cont);
						Debug.getDebugger().debug("Found a container!", EnumDebugType.DEBUG);
					}
				}
			}
		}
		
		Debug.getDebugger().debug("Done finding containers.", EnumDebugType.DEBUG);
		
		if(foundContainers.size() == 0) {
			MultiblockContainer container = new MultiblockContainer();
			container.addComponent(comp);
			multiblocks.add(container);
			Debug.getDebugger().debug("Creating new multiblock..", EnumDebugType.INFO);
		} else if(foundContainers.size() == 1) {
			foundContainers.get(0).addComponent(comp);
			Debug.getDebugger().debug("Adding it to multiblock..", EnumDebugType.INFO);
		} else {
			for(MultiblockContainer container : foundContainers) {
				Debug.getDebugger().debug("Found multiple containers, setting them invalid..", EnumDebugType.INFO);
				container.invalidatingComponents.add(comp);
				container.updateValidity();
			}
		}
	}
	
	public void removeComponent(IMultiblockComponent comp) {
		Debug.getDebugger().debug("Removing multiblock component..", EnumDebugType.DEBUG);
		
		comp.setRemoved();
		
		if(comp.getOrphan()) {
			this.notifyChange();
			return;
		}
		
		boolean success = false;
		
		for(MultiblockContainer cont : this.multiblocks) {
			for(BlockPos thisPos : cont.getPositions()) {
				if(BlockUtil.comparePositions(thisPos, comp.getPos(), cont.getDim(), comp.getWorld().provider.getDimension())) {
					Debug.getDebugger().debug("Found structure, removing..", EnumDebugType.INFO);
					cont.removeComponentAtPos(comp.getPos());
					success = true;
				}
			}
		}
		
		if(!success) {
			Debug.getDebugger().debug("Tried to remove MultiblockComponent at invalid position @ " + comp.getPos().toString(), EnumDebugType.ERROR);
		}
		
		this.notifyChange();
	}
	
	public void addBase(IMultiblockBase base) {
		
		Debug.getDebugger().debug("Adding Base to Multiblock structure..", EnumDebugType.DEBUG);
		
		ArrayList<MultiblockContainer> foundContainers = new ArrayList<MultiblockContainer>();
		
		BlockHelper[] surrPos = BlockUtil.getSurroundingBlocks(base.getPos(), (WorldServer) base.getWorld());
		
		for(MultiblockContainer cont : this.multiblocks) {
			for(BlockPos pos : cont.getPositions()) {
				for(BlockHelper wrap : surrPos) {
					if(BlockUtil.comparePositions(wrap.getPos(), pos, base.getWorld().provider.getDimension(), cont.getDim())) {
						Debug.getDebugger().debug("Found a container!", EnumDebugType.DEBUG);
						foundContainers.add(cont);
					}
				}
			}
		}
		
		if(foundContainers.size() == 0) {
			MultiblockContainer container = new MultiblockContainer();
			container.setBase(base);
			multiblocks.add(container);
			Debug.getDebugger().debug("Creating new multiblock..", EnumDebugType.INFO);
		} else if(foundContainers.size() == 1) {
			Debug.getDebugger().debug("Adding it to container..", EnumDebugType.INFO);
			foundContainers.get(0).setBase(base);
			base.setValid(true);
		} else {
			Debug.getDebugger().debug("Found multiple containers, invalidating them all..", EnumDebugType.INFO);
			base.setValid(false);
			for(MultiblockContainer container : foundContainers) {
				container.invalidatingBases.add(base);
				container.updateValidity();
			}
		}
	}
	
	public void removeBase(IMultiblockBase base) {
		Debug.getDebugger().debug("Removing a Multiblock base", EnumDebugType.DEBUG);
		
		base.setRemoved();
		
		if(base.getOrphan()) {
			this.notifyChange();
			return;
		}
		
		boolean success = false;
		
		for(MultiblockContainer cont : this.multiblocks) {
			if(BlockUtil.comparePositions(cont.getBase().getPos(), base.getPos(), cont.getDim(), base.getWorld().provider.getDimension())) {
				Debug.getDebugger().debug("Found multiblock. removing..", EnumDebugType.INFO);
				cont.setBase(null);
				success = true;
			}
		}
		
		if(!success) {
			Debug.getDebugger().debug("Tried to remove MultiblockBase at invalid position @ " + base.getPos().toString(), EnumDebugType.ERROR);
		}
		
		this.notifyChange();
	}
	
	/**
	 * Internal method, used when a MultiblockBase is added to try to reconnect 
	 * all "broken" (invalid) Bases and Components to a Multiblock.
	 * 
	 * (before you ask, no, it's not an issue that this can only remove one component/base at a time, because this gets called
	 * once per change anyways!)
	 */
	private void notifyChange() {
		ArrayList<MultiblockContainer> invalidContainers = new ArrayList<MultiblockContainer>();
		
		for(MultiblockContainer container : this.multiblocks) {
			if(!container.isValid()) {
				invalidContainers.add(container);
			}
		}
		
		Debug.getDebugger().debug("Found " + invalidContainers.size() + "invalid container", EnumDebugType.DEBUG);
		
		for(MultiblockContainer container : invalidContainers) {
			IMultiblockBase removedBase = null;
			
			for(IMultiblockBase base : container.invalidatingBases) {
				if(base.getOrphan()) {
					removedBase = base;
				}
			}
			if(removedBase != null) container.invalidatingBases.remove(removedBase);
			
			IMultiblockComponent removedComponent = null;
			for(IMultiblockComponent component : container.invalidatingComponents) {
				if(component.getOrphan()) {
					removedComponent = component;
				}
			}
			if(removedComponent != null) container.invalidatingComponents.remove(removedComponent);
			
			container.updateValidity();
		}
	}
}
