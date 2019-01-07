package mrobsidy.rockycore.chatutil;

import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.util.text.TextComponentString;

/**
 * 
 * If you use this you'll probably crash the game. I don't recomment it
 * at all.
 * 
 * 
 * @author mrobsidy
 *
 */
public class ClientChatMessages {
	
	/**
	 * I discourage the use of this.
	 * (And yet, I use it in my Debug methods. So it is possible, just make sure
	 * not to get the sides mixed up. If you run this on a dedicated server, you'll crash.
	 * Even on Clients if you mess up badly enough, you'll crash. Long story short: BE CAREFUL!)
	 * 
	 * @deprecated
	 * @param text
	 */
	public static void sendMessageToThePlayer(String text){
		RegistryRegistry.getClientRegistry().getClient().player.sendMessage(new TextComponentString(ServerChatMessages.PREFIX + text));
	}
}
