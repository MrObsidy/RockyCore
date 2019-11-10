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

import mrobsidy.rockycore.example.ExampleBlockConsumer;
import mrobsidy.rockycore.example.ExampleTileConsumer;
import mrobsidy.rockycore.example.ExampleTileGenerator;
import mrobsidy.rockycore.misc.CommandRockyCore;
import mrobsidy.rockycore.misc.Debug;
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
		
		GameRegistry.registerTileEntity(ExampleTileConsumer.class, "rockycore:exampleconsumer");
		GameRegistry.registerTileEntity(ExampleTileGenerator.class, "rockycore:examplegenerator");
		
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			MinecraftForge.EVENT_BUS.register(new ClientEvents());
		RegistryRegistry.initAndReset();
		RegistryRegistry.constructMiscRegistry();
		
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			RegistryRegistry.setClientRegistry(Minecraft.getMinecraft());
		}
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		RegistryRegistry.constructGridManagerRegistry();
	}
	
	public void serverAboutToStart(FMLServerAboutToStartEvent event){
		
	}
	
	@Mod.EventHandler
	public void serverStart(FMLServerStartingEvent event){
		RegistryRegistry.setServerRegistry(event.getServer());
		event.registerServerCommand(new CommandRockyCore());
		//ForgeVersion.getResult(this);
	}
	
	@Mod.EventHandler
	public void serverStarted(FMLServerStartedEvent event){
		
	}
	
	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent event){
		//ServerGameDataSaver.saveObjectsToDisk(RegistryRegistry.getGridRegistry().getGridManagers(), "data/gridData/gridData.dat");
	}
	
	@Mod.EventHandler
	public void serverStopped(){
		RegistryRegistry.initAndReset();
		RegistryRegistry.constructGridManagerRegistry();
	}
	
	@Mod.EventHandler
	public void IMCMessage(IMCEvent event){
		for(FMLInterModComms.IMCMessage message : event.getMessages()){
			Debug.debug(message.getStringValue());
		}
	}
}
