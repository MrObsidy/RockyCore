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


package mrobsidy.rockycore.misc;

import mrobsidy.rockycore.init.RegistryRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandRockyCore extends CommandBase{

	@Override
	public String getName() {
		return "rockycore";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Woo, magic useless method!!";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			
			if(sender.getEntityWorld().isRemote) return;
			
			if (args[0].equalsIgnoreCase("debugmode")){
				if (args[1].equalsIgnoreCase("console")){
					Debug.setDebugMode(Debug.MODE_CONSOLE);
				} else if (args[1].equalsIgnoreCase("client")){
					Debug.setDebugMode(Debug.MODE_CHAT_LOCAL);
				} else if (args[1].equalsIgnoreCase("server")){
					Debug.setDebugMode(Debug.MODE_CHAT_GLOBAL);
				} else if (args[1].equalsIgnoreCase("off")){
					Debug.setDebugMode(Debug.MODE_OFF);
				}
				
				Debug.debug("Debug mode set to: " + Debug.debugMode);
				Debug.debug("Please note that client mode debug doesn't work properly when on multiplayer servers.");
			}
		} catch (Exception e){
			//Don't print it, this will be put out evertime.
			
			Debug.debug(e.getMessage());
			for(StackTraceElement message : e.getStackTrace()){
				Debug.debug(message.toString());
			}
			
			sender.sendMessage(new TextComponentString("Usage: /rockycore debugmode [console:client:server:off]"));
		}
	}
}
