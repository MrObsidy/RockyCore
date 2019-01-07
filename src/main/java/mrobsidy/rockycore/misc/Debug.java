package mrobsidy.rockycore.misc;

import mrobsidy.rockycore.chatutil.ChatFormats;
import mrobsidy.rockycore.chatutil.ClientChatMessages;
import mrobsidy.rockycore.chatutil.ServerChatMessages;
import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class Debug {
	
	public static int debugMode;
	
	public static final int MODE_OFF = 0;
	public static final int MODE_CHAT_LOCAL = 1;
	public static final int MODE_CHAT_GLOBAL = 2;
	public static final int MODE_CONSOLE = 3;
	
	private static final String PREFIX_DEBUG = ServerChatMessages.getColorAddition(ChatFormats.YELLOW) + ServerChatMessages.getColorAddition(ChatFormats.BOLD) + "[DEBUG] " + ServerChatMessages.getColorAddition(ChatFormats.CMD_RESET);
	
	public static void setDebugMode(int parDebugMode){
		debugMode = parDebugMode;
	}
	
	public static void debug(String text){
		if (debugMode == 0){
			return;
		} else if (debugMode == 2){
			ServerChatMessages.sendMessage(PREFIX_DEBUG + text);
		} else if (debugMode == 3){
			System.out.println(PREFIX_DEBUG + text);
		} else if (debugMode == 1 && FMLCommonHandler.instance().getSide() == Side.CLIENT){
				ClientChatMessages.sendMessageToThePlayer(PREFIX_DEBUG + text);
		}
	}
}
