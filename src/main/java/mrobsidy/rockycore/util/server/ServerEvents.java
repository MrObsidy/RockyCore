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

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.Debug;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
// import mrobsidy.rockycore.misc.CustomWorldSavedData;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@EventBusSubscriber
public class ServerEvents {
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
		//if(!(event.player instanceof EntityPlayerMP)) return;
			RegistryRegistry.getMiscRegistry().lastJoinedPlayer = event.player;
	}
	
	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
		
	}
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event){
		if(event.getWorld().isRemote == true){
			Debug.debug("Client side thread detected, discarding WorldEvent.Save");
			return;
		}
		
		Debug.debug("Custom world data is now saving");
		
		ArrayList<NBTTagCompound> cmpndData = ServerGameDataSaver.relay();
		
		MapStorage storage = event.getWorld().getMapStorage();
		ServerWorldSavedData data = (ServerWorldSavedData) storage.getOrLoadData(ServerWorldSavedData.class, ServerWorldSavedData.NAME);
		
		Debug.debug("Custom data: ");
		
		if (data == null) {
		    data = new ServerWorldSavedData();
		    storage.setData(ServerWorldSavedData.NAME, data);
		}
		
		for(NBTTagCompound cmpnd : cmpndData){
			data.registerCustomSaveData(cmpnd);
		}
		
		data.debug();
		
		Debug.debug("Saved world data");
	}
}
