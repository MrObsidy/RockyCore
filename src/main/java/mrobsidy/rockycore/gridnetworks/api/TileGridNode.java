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

package mrobsidy.rockycore.gridnetworks.api;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.internal.GridConnectionSegment;
import mrobsidy.rockycore.gridnetworks.internal.GridPacket;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.MiscUtil;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.util.misc.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public final class TileGridNode extends TileEntity {
	
	public TileGridNode() {
		//do nothing
	}
	
	private String gridType;
	
	private float resistance;
	
	private float transformationFactor;
	
	private int ID;
	
	public void setID(int parID) {
		this.ID = parID;
	}
	
	public void processPacket(GridPacket packet){
		Debug.INSTANCE.debug("Processing a packet: " + packet.toString() + " from " + packet.getGenerator().toString(), EnumDebugType.DEBUG);
		ArrayList<Integer> visitedNodes = packet.getVisitedNodes();
		ArrayList<GridConnectionSegment> segments = packet.getSegments();
		
		TileEntityGenerator offerer = packet.getGenerator();
		
		packet = null;
		
		for(int id : visitedNodes){
			Debug.INSTANCE.debug("Visited:" + id, EnumDebugType.DEBUG);
		}
			
		//if that packet already contains this node, return
		//since we dont want to loop endlessly
		if(visitedNodes.contains(this.ID)){
			Debug.INSTANCE.debug("Aborting, already containing: " + this.ID, EnumDebugType.DEBUG);
			return;
		}
		
		int newIndex = segments.size();
		
		GridConnectionSegment lastSegment = segments.get(segments.size() - 1);
		
		GridConnectionSegment thisSegment = new GridConnectionSegment(lastSegment.getVoltage() * this.transformationFactor, this.resistance);
		segments.add(thisSegment);
		
		visitedNodes.add(this.ID);
		
		Debug.INSTANCE.debug("Passing on packet", EnumDebugType.DEBUG);
		ArrayList<TileGridNode> nodes = RegistryRegistry.getGridManagerRegistry().getSurroundingNodes(this.getPos(), this.getWorld().provider.getDimension(), this.getGridType());
		for(TileGridNode node : nodes){
			Debug.INSTANCE.debug("Passed on packet to " + node.toString(), EnumDebugType.DEBUG);
			node.processPacket(new GridPacket(visitedNodes, segments, this.transformationFactor, offerer));
		}
		ArrayList<TileEntityConsumer> consumers = RegistryRegistry.getGridManagerRegistry().getSurroundingConsumers(this.getPos(), this.getWorld().provider.getDimension(), this.getGridType());
		for(TileEntityConsumer consumer : consumers){
			consumer.processPacket(new GridPacket(visitedNodes, segments, this.transformationFactor, offerer));
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		this.gridType = tag.getString("gridType");
		this.resistance = tag.getFloat("resistance");
		this.transformationFactor = tag.getFloat("transformationFactor");
		this.ID = tag.getInteger("ID");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		tag.setString("gridType", this.gridType);
		tag.setFloat("resistance", this.resistance);
		tag.setFloat("transformationFactor", this.transformationFactor);
		tag.setInteger("ID", this.ID);
		return tag;
	}
	
	public void setGridType(String parType) {
		this.gridType = parType;
	}
	
	public void setResistance(float parRes) {
		this.resistance = parRes;
	}
	
	public void setTransformationFactor(float parTrans) {
		this.transformationFactor = parTrans;
	}
	
	public String getGridType() {
		return this.gridType;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public float getResistance(){
		return this.resistance;
	}
	
	public float getTransformationFactor(){
		return this.transformationFactor;
	}
	
	@Override
	public void onLoad() {
		RegistryRegistry.getGridManagerRegistry().addNode(this);
	}
}
