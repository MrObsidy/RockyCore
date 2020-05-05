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

import java.util.ArrayList;

import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.misc.debug.Debug;
import mrobsidy.rockycore.misc.debug.api.EnumDebugMode;
import mrobsidy.rockycore.misc.debug.api.EnumDebugType;
import mrobsidy.rockycore.util.server.ServerChatMessages;
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
			
			if(args.length == 0) {
				
				sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Usage: /rockycore <debugmode|gridmanager>"));
				sender.sendMessage(new TextComponentString(Debug.getPrefix() + "For more info on a subcommand, type: /rockycore <subcommand> help"));
				return;
			}
			
			if (args[0].equalsIgnoreCase("debugmode")){
				if (args[1].equalsIgnoreCase("console")){
					Debug.getDebugger().setDebugMode(EnumDebugMode.CONSOLE);
					if(args[2].equalsIgnoreCase("debug")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.DEBUG);
					} else if(args[2].equalsIgnoreCase("info")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.INFO);
					} else if(args[2].equalsIgnoreCase("warning")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.WARNING);
					} else if(args[2].equalsIgnoreCase("error")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.ERROR);
					} else {
						sender.sendMessage(new TextComponentString("Your debug mode was not recognized. Known modes are debug, info, warning and error."));
					}

				} else if (args[1].equalsIgnoreCase("client")){
					Debug.getDebugger().setDebugMode(EnumDebugMode.CLIENT);
					//Debug.debug("Please note that client mode debug doesn't work properly when on multiplayer servers.");
					if(args[2].equalsIgnoreCase("debug")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.DEBUG);
					} else if(args[2].equalsIgnoreCase("info")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.INFO);
					} else if(args[2].equalsIgnoreCase("warning")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.WARNING);
					} else if(args[2].equalsIgnoreCase("error")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.ERROR);
					} else {
						sender.sendMessage(new TextComponentString("Your debug mode was not recognized. Known modes are debug, info, warning and error."));
					}

				} else if (args[1].equalsIgnoreCase("server")){
					Debug.getDebugger().setDebugMode(EnumDebugMode.SERVER);
					if(args[2].equalsIgnoreCase("debug")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.DEBUG);
					} else if(args[2].equalsIgnoreCase("info")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.INFO);
					} else if(args[2].equalsIgnoreCase("warning")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.WARNING);
					} else if(args[2].equalsIgnoreCase("error")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.ERROR);
					} else {
						sender.sendMessage(new TextComponentString("Your debug mode was not recognized. Known modes are debug, info, warning and error."));
					}

				} else if (args[1].equalsIgnoreCase("off")){
					Debug.getDebugger().setDebugMode(EnumDebugMode.OFF);
					if(args[2].equalsIgnoreCase("debug")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.DEBUG);
					} else if(args[2].equalsIgnoreCase("info")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.INFO);
					} else if(args[2].equalsIgnoreCase("warning")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.WARNING);
					} else if(args[2].equalsIgnoreCase("error")) {
						Debug.getDebugger().setDebugPriority(EnumDebugType.ERROR);
					} else {
						sender.sendMessage(new TextComponentString("Your debug mode was not recognized. Known modes are debug, info, warning and error."));
					}

				} else {
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Sets the debug message destination with set minimum severity level."));
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Usage: /rockycore debugmode <console|client|server|off> <debug|info|warning|error>"));
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Please note that /rockycore debugmode client is unreliable and should not be used."));
				}
				
				
				Debug.getDebugger().debug("Debug mode set to: " + Debug.getDebugger().getDebugMode() + ", severity level: " + Debug.getDebugger().getLevel(), EnumDebugType.INFO);

			}
		} catch (Exception e){
			//Don't print it, this will be put out evertime.
			
			e.printStackTrace();
		}
	}
}
