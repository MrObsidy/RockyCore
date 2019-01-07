package mrobsidy.rockycore.chatutil;

import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.util.text.TextComponentString;

/**
 * This class can be used to send global chat messages.
 * 
 * @author mrobsidy
 *
 */
public class ServerChatMessages {
	
	public static final String PREFIX = getColorAddition(ChatFormats.YELLOW) + getColorAddition(ChatFormats.BOLD) + "[RockyCore] " + getColorAddition(ChatFormats.CMD_RESET);
	
	public static void sendMessage(String text){
		RegistryRegistry.getServerRegistry().getServer().getPlayerList().sendMessage(new TextComponentString(PREFIX + text));
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
