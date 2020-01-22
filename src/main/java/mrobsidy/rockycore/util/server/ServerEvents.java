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


package mrobsidy.rockycore.util.server;

import java.util.ArrayList;

import mrobsidy.rockycore.example.ExampleBlockConsumer;
import mrobsidy.rockycore.example.ExampleBlockGenerator;
import mrobsidy.rockycore.example.ExampleBlockNode;
import mrobsidy.rockycore.example.ExampleCreativeTab;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.registries.api.CustomData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;
// import mrobsidy.rockycore.misc.CustomWorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


public class ServerEvents {
	
	private ExampleBlockNode blockNode = new ExampleBlockNode(Material.GRASS);
	private ExampleBlockGenerator blockGenerator = new ExampleBlockGenerator(Material.GRASS);
	private ExampleBlockConsumer blockConsumer = new ExampleBlockConsumer(Material.GRASS);
	
	private ExampleCreativeTab ect = new ExampleCreativeTab("rockycoretab");
	
	private ItemBlock itemNode = new ItemBlock(blockNode);
	private ItemBlock itemGenerator = new ItemBlock(blockGenerator);
	private ItemBlock itemConsumer = new ItemBlock(blockConsumer);
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		blockNode.setRegistryName("rockycore:blocknode").setUnlocalizedName("blockNode");
		blockGenerator.setRegistryName("rockycore:blockgenerator").setUnlocalizedName("blockGenerator");
		blockConsumer.setRegistryName("rockycore:blockconsumer").setUnlocalizedName("blockConsumer");
	    event.getRegistry().registerAll(blockNode, blockGenerator, blockConsumer);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		itemNode.setRegistryName("rockycore:itemnode").setCreativeTab(ect).setUnlocalizedName("itemNode");
		itemGenerator.setRegistryName("rockycore:itemgenerator").setCreativeTab(ect).setUnlocalizedName("itemGenerator");
		itemConsumer.setRegistryName("rockycore:itemconsumer").setCreativeTab(ect).setUnlocalizedName("itemConsumer");
		event.getRegistry().registerAll(itemNode, itemGenerator, itemConsumer);
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
		//if(!(event.player instanceof EntityPlayerMP)) return;
		
		CustomData playerWrapper = new CustomData(event.player, "LastJoinedPlayer");
		
		//This needs to be fixed.
		RegistryRegistry.getMiscRegistry().addData(playerWrapper);
	}
	
	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
		
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event){
		//do stuff
	}
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event){
		if(event.getWorld().isRemote == true){
			Debug.INSTANCE.debug("Client side thread detected, discarding WorldEvent.Save", EnumDebugType.DEBUG);
			return;
		}
		
		Debug.INSTANCE.debug("Custom world data is now saving", EnumDebugType.DEBUG);
		
		ArrayList<NBTTagCompound> cmpndData = ServerGameDataSaver.relay();
		
		MapStorage storage = event.getWorld().getMapStorage();
		ServerWorldSavedData data = (ServerWorldSavedData) storage.getOrLoadData(ServerWorldSavedData.class, ServerWorldSavedData.NAME);
		
		Debug.INSTANCE.debug("Custom data: ", EnumDebugType.INFO);
		
		if (data == null) {
		    data = new ServerWorldSavedData();
		    storage.setData(ServerWorldSavedData.NAME, data);
		}
		
		for(NBTTagCompound cmpnd : cmpndData){
			data.registerCustomSaveData(cmpnd);
		}
		
		data.debug();
		
		Debug.INSTANCE.debug("Saved world data", EnumDebugType.INFO);
	}
}
