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


package mrobsidy.rockycore.misc.debug;

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.ChatFormats;
import mrobsidy.rockycore.misc.debug.api.EnumDebugMode;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.misc.debug.api.IDebugWriter;
import mrobsidy.rockycore.util.client.ClientChatMessages;
import mrobsidy.rockycore.util.server.ServerChatMessages;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class Debug implements IDebugWriter {	
	
	public static final Debug INSTANCE = new Debug();
	
	private static EnumDebugMode debugMode;
	
	private static EnumDebugType level;
	
	private static final String PREFIX_ROCKYCORE = ServerChatMessages.getColorAddition(ChatFormats.YELLOW)+ ServerChatMessages.getColorAddition(ChatFormats.BOLD) + "[RockyCore] " + ServerChatMessages.getColorAddition(ChatFormats.CMD_RESET);
	private static final String PREFIX_DEBUG = ServerChatMessages.getColorAddition(ChatFormats.WHITE) + ServerChatMessages.getColorAddition(ChatFormats.BOLD) + "[DEBUG] " + ServerChatMessages.getColorAddition(ChatFormats.CMD_RESET);	
	private static final String PREFIX_INFO = ServerChatMessages.getColorAddition(ChatFormats.GREEN) + ServerChatMessages.getColorAddition(ChatFormats.BOLD) + "[DEBUG] " + ServerChatMessages.getColorAddition(ChatFormats.CMD_RESET);
	private static final String PREFIX_WARNING = ServerChatMessages.getColorAddition(ChatFormats.LIGHT_PURPLE) + ServerChatMessages.getColorAddition(ChatFormats.BOLD) + "[DEBUG] " + ServerChatMessages.getColorAddition(ChatFormats.CMD_RESET);
	private static final String PREFIX_ERROR = ServerChatMessages.getColorAddition(ChatFormats.RED) + ServerChatMessages.getColorAddition(ChatFormats.BOLD) + "[DEBUG] " + ServerChatMessages.getColorAddition(ChatFormats.CMD_RESET);
	
	public EnumDebugType getLevel() {
		return this.level;
	}
	
	private static String getAppropriatePrefix(EnumDebugType type) {
		
		switch(type){
			case DEBUG: return PREFIX_DEBUG;
			case INFO: return PREFIX_INFO;
			case WARNING: return PREFIX_WARNING;
			case ERROR: return PREFIX_ERROR;
		}
		
		return PREFIX_ERROR + " Also, could not retrieve debug prefix! How did you do that?? ";
	}
	
	public Debug() {
		this.debugMode = EnumDebugMode.OFF;
		this.level = EnumDebugType.ERROR;
	}
	
	public static String getPrefix() {
		return PREFIX_ROCKYCORE;
	}
	
	public void setDebugMode(EnumDebugMode mode){
		debugMode = mode;
	}
	
	@Override
	public void setDebugPriority(EnumDebugType type) {
		this.level = type;
	}
	
	public EnumDebugMode getDebugMode() {
		return debugMode;
	}
	
	public void debug(String text, EnumDebugType type){
		if (debugMode == EnumDebugMode.OFF){
			return;
		} else if (debugMode == EnumDebugMode.SERVER){
			
			try {
				if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) throw new Exception();
			} catch (Exception e) {
				System.out.println("WRONG SIDE THREAD DETECTED!! CONTACT MOD AUTHOR!!");
				e.printStackTrace();
				return;
			}
				
			if(EnumDebugType.getPriority(type, this.level)) {
				ServerChatMessages.sendMessage(getAppropriatePrefix(type) + text);
			}
				
		} else if (debugMode == EnumDebugMode.CONSOLE){
			if(EnumDebugType.getPriority(type, this.level)) {
				System.out.println(getAppropriatePrefix(type) + text);
			}
		} else if (debugMode == EnumDebugMode.CLIENT && FMLCommonHandler.instance().getSide() == Side.CLIENT){
			if(EnumDebugType.getPriority(type, this.level)) {
				ClientChatMessages.sendMessageToThePlayer(getAppropriatePrefix(type) + text);
			}
		}
	}

	
}
