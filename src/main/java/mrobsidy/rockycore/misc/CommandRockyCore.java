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

import mrobsidy.rockycore.gridnetworks.api.TileEntityConsumer;
import mrobsidy.rockycore.gridnetworks.api.TileEntityGenerator;
import mrobsidy.rockycore.gridnetworks.api.TileGridNode;
import mrobsidy.rockycore.gridnetworks.internal.GridManager;
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

			} else if (args[0].equalsIgnoreCase("gridmanager")) {
				if(args[1].equalsIgnoreCase("list")) {
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "The following grids have been found: (this may take a moment)"));
					ArrayList<GridManager> mans = RegistryRegistry.getGridManagerRegistry().getManagers();
					for(GridManager man : mans) {
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Found GridManager of type: " +  man.getGridType()));
					}
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Done."));
				} else if(args[1].equalsIgnoreCase("rungc")) {
					Debug.getDebugger().debug("Running garbage collection.. (Output is in console)", EnumDebugType.WARNING);
					RegistryRegistry.getGridManagerRegistry().garbageCollection();
				} else if(args[1].equalsIgnoreCase("dump")) {
					RegistryRegistry.getGridManagerRegistry().dump();
				} else if(args[1].equalsIgnoreCase("select")){
					
					String parMan = "";
					
					if(args.length > 2) {
						parMan = args[2];
					} else {
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "You need to specify a manager and an operation. Try /rockycore gridmanager select help"));
						return;
					}
					
					if(args[2] == "help") {
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Usage: /rockycore gridmanager select <gridType> <listnodes|listconsumers|listgenerators|forcepackets|flushlists>"));
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager select <gridType> <listnodes>: Lists all nodes"));
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager select <gridType> <listgenerators>: Lists all generators"));
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager select <gridType> <listconsumers>: Lists all consumer"));
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager select <gridType> <forcepackets>: Forces all generators to send out packets"));
						sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager select <gridType> <flushlists>: Resets all internal lists and force all Consumers, Grids and Generators to re-register into their GridManager. THIS IS VERY RESOURCE INTENSIVE!"));
						return;
					}
					
					ArrayList<GridManager> mans = RegistryRegistry.getGridManagerRegistry().getManagers();
					
					boolean found = false;
					
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Please note that this is a lenghty process. Excuse any server lag."));
					
					ServerChatMessages.sendMessage("WARNING - POSSIBLE SERVER LAG INCOMING! Player " + sender.getName() + " is listing/forcing packets for a grid manager. This may take a moment.");
					
					for(GridManager man : mans) {
						Debug.getDebugger().debug("Checking Manager of type " + man.getGridType() + " with parMan " + parMan, EnumDebugType.DEBUG);
						if(man.getGridType().contentEquals(parMan)) {
							Debug.getDebugger().debug("Found manager! Trying to find ", EnumDebugType.DEBUG);
							if(args[3].equalsIgnoreCase("listnodes")) {
								ArrayList<TileGridNode> nodes = man.getNodes();
								for(TileGridNode node : nodes) {
									sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Node @ " + node.getPos().toString()));
								}
								sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Done listing nodes."));
							} else if(args[3].equalsIgnoreCase("listgenerators")) {
								ArrayList<TileEntityGenerator> gens = man.getGenerators();
								for(TileEntityGenerator gen : gens) {
									sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Generator @ " + gen.getPos().toString()));
								}
								sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Done listing generators."));
							} else if(args[3].equalsIgnoreCase("listconsumers")) {
								ArrayList<TileEntityConsumer> cons = man.getConsumers();
								for(TileEntityConsumer con : cons) {
									sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Consumer @ " + con.getPos().toString()));
								}
								sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Done listing consumers."));
							} else if(args[3].equalsIgnoreCase("forcepackets")) {
								sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Forcing packet relay for type " + parMan + "."));
								man.triggerPacket();
								sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Done."));
							} else if(args[3].equalsIgnoreCase("flushlists")) {
								sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Starting complete reconstruction..."));
								ServerChatMessages.sendMessage("Possibly extreme Lagspike incoming!");
								RegistryRegistry.getGridManagerRegistry().flush();
								sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Done!"));
								ServerChatMessages.sendMessage("Done with the LagSpike!!");
							}
							found = true;
							break;
						}
					}
					if(!found) sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Your specified GridManager was not found our you listed an invalid operation. Try /rockycore gridmanager select help."));
				} else {
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "Usage: /rockycore gridmanager <list|select>"));
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager runGC: runs the GridManager garbage collection (this may take a moment, may help with lags or maybe some exotic bugs)."));
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager dump: Dumps all nodes, generators and consumers to the console (for debug)."));
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager list: lists all GridManagers."));
					sender.sendMessage(new TextComponentString(Debug.getPrefix() + "/rockycore gridmanager select <gridtype>: selects a GridManager for further operations. For more help, try /rockycore gridmanager select help"));
				}
			}
		} catch (Exception e){
			//Don't print it, this will be put out evertime.
			
			e.printStackTrace();
		}
	}
}
