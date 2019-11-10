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

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

import mrobsidy.rockycore.gridnetworks.internal.GridManagerRegistry;
import mrobsidy.rockycore.misc.MiscRegistry;
import mrobsidy.rockycore.registries.api.IRegistry;
import mrobsidy.rockycore.util.client.ClientRegistry;
import mrobsidy.rockycore.util.server.ServerRegistry;

/**
 * 
 * Here, all registries are registered.. 
 * (Horrible description, but that's what it does.)
 * 
 * This class is intended to be used to have common references along all of my mods.
 * @author mrobsidy
 *
 */
public class RegistryRegistry {
	private static ServerRegistry serverRegistry;
	private static ClientRegistry clientRegistry;
	private static MiscRegistry miscRegistry;
	private static GridManagerRegistry gridRegistry;
	private static ArrayList<IRegistry> customRegistries = new ArrayList<IRegistry>();
	
	private static boolean serverRegistrySetForCurrentSession; //Set if the Server Registry is set for the current session
	
	private static boolean initFirstTime; //Marker if this wasn't initialized yet
	//Please note: "true" means that we already initialized. I made a funny when doing this.
	
	public static void addCustomRegistry(IRegistry reg){
		customRegistries.add(reg);
	}
	
	public static IRegistry getCustomRegistry(Class registryClass){
		IRegistry returner = null;
		for (IRegistry reg : customRegistries){
			if (reg.getClass() == registryClass) returner = reg;
		}
		
		return returner;
	}
	
	public static void constructGridManagerRegistry(){
		gridRegistry = new GridManagerRegistry();
	}
	
	public static GridManagerRegistry getGridManagerRegistry(){
		return gridRegistry;
	}
	
	public static boolean removeCustomRegistry(IRegistry reg){
		return customRegistries.remove(reg);
	}
	
	public static void setServerRegistry(MinecraftServer server){
		serverRegistry = new ServerRegistry(server);
	}
	
	@SideOnly(Side.CLIENT)
	public static void setClientRegistry(Minecraft client){
		clientRegistry = new ClientRegistry(client);
	}
	
	public static void constructMiscRegistry(){
		miscRegistry = new MiscRegistry();
	}
	
	public static MiscRegistry getMiscRegistry(){
		return miscRegistry;
	}
	
	public static ServerRegistry getServerRegistry(){
		return serverRegistry;
	}
	@SideOnly(Side.CLIENT)
	public static ClientRegistry getClientRegistry(){
		return clientRegistry;
	}
	
	public static void initAndReset(){
		if(!initFirstTime) initFirstTime = false; //Initialize
		serverRegistrySetForCurrentSession = false; //Reset this
		serverRegistry = null; //in any case, reset the server registry
		gridRegistry = null; //reset this too
		for(IRegistry registry : customRegistries){
			if(registry.getResttable()){
				registry = null;
			}
		}
		if(!initFirstTime && FMLCommonHandler.instance().getSide() == Side.CLIENT) clientRegistry = null; //if we're initializing and we are on a client, initialize it
		if(!initFirstTime) initFirstTime = true; //if this is the fist time initializing, set a marker that the next call of this function isn't the first time this function is being called
	}
}
