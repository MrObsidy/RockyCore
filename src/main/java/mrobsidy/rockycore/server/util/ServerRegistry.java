package mrobsidy.rockycore.server.util;

import net.minecraft.server.MinecraftServer;

/**
 * This Object holds some server-specific things like the server instance and so on.
 * 
 * 
 * @author mrobsidy
 *
 */
public class ServerRegistry {
	private final MinecraftServer runningServer;
	
	public ServerRegistry(MinecraftServer server){
		this.runningServer = server;
	}
	
	public MinecraftServer getServer(){
		return this.runningServer;
	}
}
