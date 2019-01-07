package mrobsidy.rockycore.server.util;

import mrobsidy.rockycore.chatutil.ServerChatMessages;
import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@EventBusSubscriber
public class ServerEvents {
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
			RegistryRegistry.getMiscRegistry().lastJoinedPlayer = event.player;
	}
	
	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
		
	}
}
