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

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.ChatFormats;
import mrobsidy.rockycore.misc.debug.Debug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * This class can be used to send global chat messages.
 * 
 * @author mrobsidy
 *
 */
public class ServerChatMessages {
	
	
	public static void sendMessage(String text){
		
		if(RegistryRegistry.getServerRegistry() == null) {
			System.out.println("[RockyCore] " + text);
		}
		
		for (World world : RegistryRegistry.getServerRegistry().getServer().worlds){
			for (EntityPlayer player : world.playerEntities){
					player.sendMessage(new TextComponentString(Debug.getPrefix() + text));
			}
		}
	}
	
	public static String getColorAddition(ChatFormats format){
		switch (format){
			case DARK_RED: return "\u00A74";
			case RED: return "\u00A7c";
			case GOLD: return "\u00A76";
			case YELLOW: return "\u00A7e";
			case DARK_GREEN: return "\u00A72";
			case GREEN: return "\u00A7a";
			case AQUA: return "\u00A7b";
			case DARK_AQUA: return "\u00A73";
			case DARK_BLUE: return "\u00A71";
			case BLUE: return "\u00A79";
			case LIGHT_PURPLE: return "\u00A7d";
			case DARK_PURPLE: return "\u00A75";
			case WHITE: return "\u00A7f";
			case GRAY: return "\u00A77";
			case DARK_GRAY: return "\u00A78";
			case BLACK: return "\u00A70";
			
			case CMD_RESET: return "\u00A7r";
			case BOLD: return "\u00A7l";
			case ITALIC: return "\u00A7o";
			case UNDERLINE: return "\u00A7n";
			case STRIKE: return "\u00A7m";
			case CMD_MAGICLETTERS: return "\u00A7k";
		}
		return null;
	}
}
