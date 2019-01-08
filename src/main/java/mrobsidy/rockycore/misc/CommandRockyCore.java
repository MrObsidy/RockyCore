package mrobsidy.rockycore.misc;

import mrobsidy.rockycore.gridnetworks.Grid;
import mrobsidy.rockycore.gridnetworks.GridManager;
import mrobsidy.rockycore.gridnetworks.IGridNode;
import mrobsidy.rockycore.init.RegistryRegistry;
import mrobsidy.rockycore.test.GridTest;
import mrobsidy.rockycore.test.ReferenceImplIGridNode;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandRockyCore extends CommandBase{

	@Override
	public String getName() {
		return "rockycore";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		
		return null;
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
			} else if (args[0].equalsIgnoreCase("test")){
				if(args[1].equalsIgnoreCase("createNode")){
					IGridNode node = new ReferenceImplIGridNode(new BlockPos(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])), sender.getCommandSenderEntity().dimension);
					
					//GridManager man = RegistryRegistry.getGridRegistry().getGridManagerForClass(GridTest.class);
					
					GridManager man = (GridManager) RegistryRegistry.getGridRegistry().getGridManagers().get(0);
					
					man.addNodeToNet(node);
					
					Debug.debug("Done with this: " + node.getGrid());
				} else if (args[1].equalsIgnoreCase("createGridManager")){
					//RegistryRegistry.getGridRegistry().registerGridManager(new GridManager(MiscUtil.getClassForName(args[2])));
					RegistryRegistry.getGridRegistry().registerGridManager(new GridManager(GridTest.class));
				}
			}
		} catch (Exception e){
			//Don't print it, this will be put out evertime.
			sender.sendMessage(new TextComponentString("Usage: /rockycore debugmode [console:client:server:off]"));
		}
	}
}
