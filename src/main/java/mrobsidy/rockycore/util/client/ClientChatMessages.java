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


package mrobsidy.rockycore.util.client;

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.util.server.ServerChatMessages;
import net.minecraft.util.ChatComponentText;

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
		RegistryRegistry.getClientRegistry().getClient().thePlayer.addChatMessage(new ChatComponentText(ServerChatMessages.PREFIX + text));
	}
}
