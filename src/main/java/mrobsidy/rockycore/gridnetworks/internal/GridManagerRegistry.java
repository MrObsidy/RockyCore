/**
 * 
 *  RockyCore
 *  Copyright (C) 2018-2019 MrObsidy
 *  
 *  
 *  This file is part of RockyCore.
 *
 *  RockyCore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  RockyCore is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with RockyCore.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package mrobsidy.rockycore.gridnetworks.internal;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.api.TileEntityConsumer;
import mrobsidy.rockycore.gridnetworks.api.TileEntityGenerator;
import mrobsidy.rockycore.gridnetworks.api.TileGridNode;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;


public class GridManagerRegistry {
	
	private final ArrayList<GridManager> GRID_MANAGERS = new ArrayList<>();

	private ArrayList<TileGridNode> nodeReconstructionList;
	private ArrayList<TileEntityConsumer> consumerReconstructionList;
	private ArrayList<TileEntityGenerator> generatorReconstructionList;
	
	private boolean reconstructionMode;
	
	public GridManagerRegistry(){}
	
	/**
	 * 
	 * Don't run this if you don't know, what you're doing.
	 * 
	 */
	public void garbageCollection() {
		ArrayList<GridManager> removables = new ArrayList<GridManager>();
		int oldSize = GRID_MANAGERS.size();
		for(GridManager man : GRID_MANAGERS) {
			Debug.getDebugger().debug("Checking GridManager of type " + man.getGridType(), EnumDebugType.INFO);
			Debug.getDebugger().debug("Amount of nodes of man: " + man.getNodes().size(), EnumDebugType.INFO);
			Debug.getDebugger().debug("Amount of generators of man: " + man.getGenerators().size(), EnumDebugType.INFO);
			Debug.getDebugger().debug("Amount of consumers of man: " + man.getConsumers().size(), EnumDebugType.INFO);
			if(man.getNodes().size() == 0 && man.getConsumers().size() == 0 && man.getGenerators().size() == 0) {
				removables.add(man);
			}
		}
		GRID_MANAGERS.removeAll(removables);
		Debug.getDebugger().debug("Running garbage collection, removed " + (oldSize - GRID_MANAGERS.size()) + " empty GridManagers", EnumDebugType.WARNING);
		removables = null;
	}
	
	
	public void dump() {
		for(GridManager man : GRID_MANAGERS) {
			man.dump();
		}
	}
	
	public void addNode(TileGridNode node){
		
		if(reconstructionMode) {
			this.addNodeToReconstructionList(node);
			return;
		}
		
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if (currMan.getGridType().contentEquals(node.getGridType())){
				manager = currMan;
				break;
			}
		}
		
		if (manager == null){
			manager = new GridManager(node.getGridType());
			GRID_MANAGERS.add(manager);
		}
		
		manager.addNode(node);
	}
	
	public void removeNode(TileGridNode node){
		
		Debug.getDebugger().debug("Node: " + node.toString(), EnumDebugType.DEBUG);
		
		GridManager manager = null;
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType().contentEquals(node.getGridType())){
				manager = man;
				break;
			}
		}
		manager.removeNode(node);
		
	}
	
	public void addGenerator(TileEntityGenerator generator){
		
		if(reconstructionMode) {
			this.addGeneratorToReconstructionList(generator);
			return;
		}
		
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if (currMan.getGridType().contentEquals(generator.getGridType())){
				manager = currMan;
				break;
			}
		}
		
		if (manager == null){
			manager = new GridManager(generator.getGridType());
			GRID_MANAGERS.add(manager);
		}
		
		manager.addGenerator(generator);
	}
	
	public void removeGenerator(TileEntityGenerator generator){
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if(currMan.getGridType().contentEquals(generator.getGridType())){
				manager = currMan;
				manager.removeGenerator(generator);
				break;
			}
		}
	}
	
	public void addConsumer(TileEntityConsumer consumer){
		
		if(reconstructionMode) {
			this.addConsumerToReconstructionList(consumer);
			return;
		}
		
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if (currMan.getGridType().contentEquals(consumer.getGridType())){
				manager = currMan;
				break;
			}
		}
		
		if (manager == null){
			manager = new GridManager(consumer.getGridType());
			GRID_MANAGERS.add(manager);
		}
		
		
		manager.addConsumer(consumer);
	}

	public void removeConsumer(TileEntityConsumer consumer){
		GridManager manager = null;
		for(GridManager currMan : GRID_MANAGERS){
			if(currMan.getGridType().contentEquals(consumer.getGridType())){
				manager = currMan;
				manager.removeConsumer(consumer);
				break;
			}
		}
	}
	
	public void initReconstruction() {
		System.out.println("Initializing reconstruction");
		this.nodeReconstructionList = new ArrayList<TileGridNode>();
		this.consumerReconstructionList = new ArrayList<TileEntityConsumer>();
		this.generatorReconstructionList = new ArrayList<TileEntityGenerator>();
		this.reconstructionMode = true;
		Debug.getDebugger().debug("Done initializing reconstruction", EnumDebugType.INFO);
	}
	
	private void addNodeToReconstructionList(TileGridNode node) {
		Debug.getDebugger().debug("Adding node @" + node.getPos().toString() + " to reconstruction list", EnumDebugType.DEBUG);
		this.nodeReconstructionList.add(node);
	}
	
	private void addConsumerToReconstructionList(TileEntityConsumer consumer) {
		Debug.getDebugger().debug("Adding a consumer @ " + consumer.getPos().toString() + " to the reconstruction list", EnumDebugType.DEBUG);
		this.consumerReconstructionList.add(consumer);
	}

	private void addGeneratorToReconstructionList(TileEntityGenerator generator) {
		Debug.getDebugger().debug("Adding a generator @ " + generator.getPos().toString() + " to the reconstruction list", EnumDebugType.DEBUG);
		this.generatorReconstructionList.add(generator);
	}
	
	public void reconstruct() {
		
		this.reconstructionMode = false;
		
		Debug.getDebugger().debug("Reconstructing, this may take a moment", EnumDebugType.INFO);
		for(TileGridNode node : this.nodeReconstructionList) {
			Debug.getDebugger().debug("Reconstructing node @ " + node.getPos().toString(), EnumDebugType.DEBUG);
			this.addNode(node);
		}
		
		Debug.getDebugger().debug("Done with node reconstruction, reconstructing consumers..", EnumDebugType.INFO);
		for(TileEntityConsumer consumer : this.consumerReconstructionList) {
			Debug.getDebugger().debug("Reconstructing node @ " + consumer.getPos().toString(), EnumDebugType.DEBUG);
			this.addConsumer(consumer);
		}
		
		Debug.getDebugger().debug("Done with consumer reconstruction, reconstructing generators..", EnumDebugType.INFO);
		for(TileEntityGenerator generator : this.generatorReconstructionList) {
			Debug.getDebugger().debug("Reconstructing node @ " + generator.getPos().toString(), EnumDebugType.DEBUG);
			this.addGenerator(generator);
		}
		
		Debug.getDebugger().debug("Done with reconstruction.", EnumDebugType.INFO);
		Debug.getDebugger().debug("Resetting reconstruction ArrayLists...", EnumDebugType.INFO);
		
		this.nodeReconstructionList = null;
		this.consumerReconstructionList = null;
		this.generatorReconstructionList = null;
		
		for(GridManager man : this.GRID_MANAGERS) {
			this.relayPacket(man.getGridType());
		}
	}
	
	public TileGridNode getNodeAtPos(String gridType, BlockPos pos, World world){
		TileGridNode returnNode = null;
		GridManager nMan = null;
		
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType() == gridType){
				nMan = man;
				break;
			}
		}
		
		if(nMan != null){
			Debug.getDebugger().debug("found manager, retrieving node...", EnumDebugType.DEBUG);
			returnNode = nMan.getNodeAtPos(pos, world);
		} else {
			returnNode = null;
		}
			
		return returnNode;
	}
	
	public void relayPacket(String gridType){
		for(GridManager man : GRID_MANAGERS){
			if(man.getGridType().contentEquals(gridType)){
				man.triggerPacket();
				break;
			}
		}
	}
	
	public ArrayList<TileEntityConsumer> getSurroundingConsumers(BlockPos pos, int dim, String gridType) {
		ArrayList<TileEntityConsumer> consumers = new ArrayList<TileEntityConsumer>();
		for(GridManager manager : GRID_MANAGERS){
			if(manager.getGridType().contentEquals(gridType)){
				consumers = manager.getSurroundingConsumers(pos, dim);
				break;
			}
		}
		
		return consumers;
	}
	
	public ArrayList<TileGridNode> getSurroundingNodes(BlockPos pos, int dim, String gridType){
		ArrayList<TileGridNode> nodes = new ArrayList<TileGridNode>();
		for(GridManager manager : GRID_MANAGERS){
			if(manager.getGridType().contentEquals(gridType)){
				
				Debug.getDebugger().debug("Found manager: " + manager.getGridType(), EnumDebugType.DEBUG);
				
				nodes = manager.getSurroundingNodes(pos, dim);
				break;
			}
		}
		
		return nodes;
	}

	public ArrayList<GridManager> getManagers() {
		return this.GRID_MANAGERS;
	}

	public void flush() {
		for(GridManager man : this.GRID_MANAGERS) {
			man.flush();
		}
		
		Debug.getDebugger().debug("Removed all old managers, starting reconstruction...", EnumDebugType.WARNING);
		
		this.GRID_MANAGERS.clear();
		
		ArrayList<TileEntity> entities = new ArrayList<TileEntity>();
		
		for(WorldServer w : RegistryRegistry.getServerRegistry().getServer().worlds) {
			entities.addAll(w.loadedTileEntityList);
		}
		Debug.getDebugger().debug("Retrieved all TileEntities, size: " + entities.size(), EnumDebugType.INFO);
		Debug.getDebugger().debug("(The bigger this number, the more lag you can expect)", EnumDebugType.INFO);
		
		ArrayList<TileGridNode> nodes = new ArrayList<TileGridNode>();
		ArrayList<TileEntityConsumer> cons = new ArrayList<TileEntityConsumer>();
		ArrayList<TileEntityGenerator> gens = new ArrayList<TileEntityGenerator>();
		
		for(TileEntity entity : entities) {
			
			Debug.getDebugger().debug("Checking TileEntity of type: " + entity.toString(), EnumDebugType.DEBUG);
			
			if(entity instanceof TileGridNode) nodes.add((TileGridNode) entity);
			if(entity instanceof TileEntityConsumer) cons.add((TileEntityConsumer) entity);
			if(entity instanceof TileEntityGenerator) gens.add((TileEntityGenerator) entity);
		}
		
		Debug.getDebugger().debug("Done sorting Consumers, Generators and Nodes.", EnumDebugType.INFO);
		
		Debug.getDebugger().debug("Amount of consumers found: " + cons.size(), EnumDebugType.INFO);
		Debug.getDebugger().debug("Amoung of generators found: " + gens.size(), EnumDebugType.INFO);
		Debug.getDebugger().debug("Amount of nodes found: " + nodes.size(), EnumDebugType.INFO);
		
		Debug.getDebugger().debug("Re-adding everything...", EnumDebugType.WARNING);
		
		for(TileGridNode node: nodes) {
			this.addNode(node);
		}
		
		for(TileEntityGenerator gen : gens) {
			this.addGenerator(gen);
		}
		
		for(TileEntityConsumer con : cons) {
			this.addConsumer(con);
		}
		
		Debug.getDebugger().debug("Done registering. Forcing packet sending..", EnumDebugType.INFO);
		
		for(GridManager man : this.GRID_MANAGERS) {
			Debug.getDebugger().debug("Triggering packet for " + man.getGridType(), EnumDebugType.DEBUG);
			man.triggerPacket();
		}
		
		Debug.getDebugger().debug("Done. The game should be done lagging now.", EnumDebugType.WARNING);
	}
}
