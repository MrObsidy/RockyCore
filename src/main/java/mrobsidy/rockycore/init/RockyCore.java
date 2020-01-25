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


package mrobsidy.rockycore.init;

import mrobsidy.rockycore.gridnetworks.api.TileGridNode;
import mrobsidy.rockycore.misc.CommandRockyCore;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.util.client.ClientEvents;
import mrobsidy.rockycore.util.server.ServerEvents;
import mrobsidy.rockycore.util.server.ServerGameDataSaver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;



/**
 * 
 * The main class of the coremod. Sets some things in RegistryRegistry.
 * 
 * 
 * @author mrobsidy
 *
 */
@Mod(modid = RockyCore.MODID, name = RockyCore.MODNAME)
public class RockyCore {
	public static final String MODID = "rockycore";
	public static final String MODNAME = "RockyCore";

	
	@Mod.Instance
	public RockyCore instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
		System.out.println("[RockyCore] preInit event received");
		
		GameRegistry.registerTileEntity(TileGridNode.class, "rockycore:tilegridnode");
		
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			MinecraftForge.EVENT_BUS.register(new ClientEvents());
		RegistryRegistry.initAndReset();
		RegistryRegistry.constructMiscRegistry();
		
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		System.out.println("[RockyCore] init event received");
		RegistryRegistry.constructGridManagerRegistry();
		RegistryRegistry.constructMultiblockRegistry();
		
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			RegistryRegistry.setClientRegistry(Minecraft.getMinecraft());
		}
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		System.out.println("[RockyCore] postInit event received");
		RegistryRegistry.getGridManagerRegistry().initReconstruction();
	}
	
	public void serverAboutToStart(FMLServerAboutToStartEvent event){
		System.out.println("[RockyCore] ServerAboutToStart event received");
		

	}
	
	@Mod.EventHandler
	public void serverStart(FMLServerStartingEvent event){
		System.out.println("[RockyCore] serverStart event received");
		RegistryRegistry.setServerRegistry(event.getServer());
		event.registerServerCommand(new CommandRockyCore());
		
		RegistryRegistry.getGridManagerRegistry().reconstruct();
		RegistryRegistry.getGridManagerRegistry().garbageCollection();
	
		
	}
	
	@Mod.EventHandler
	public void serverStarted(FMLServerStartedEvent event){
		System.out.println("[RockyCore] serverStarted event received");
	}
	
	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent event){
		System.out.println("[RockyCore] serverStopping event received");
		//ServerGameDataSaver.saveObjectsToDisk(RegistryRegistry.getGridRegistry().getGridManagers(), "data/gridData/gridData.dat");
		RegistryRegistry.initAndReset();
		RegistryRegistry.constructGridManagerRegistry();
		RegistryRegistry.constructMultiblockRegistry();
	}
	
	@Mod.EventHandler
	public void serverStopped(){
		System.out.println("[RockyCore] serverStopped event received");
		
	}
	
	@Mod.EventHandler
	public void IMCMessage(IMCEvent event){
		for(FMLInterModComms.IMCMessage message : event.getMessages()){
			Debug.getDebugger().debug(message.getStringValue(), EnumDebugType.INFO);
		}
	}
}
