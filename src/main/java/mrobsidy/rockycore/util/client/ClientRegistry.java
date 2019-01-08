package mrobsidy.rockycore.util.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientRegistry {
	private final Minecraft runningClient;
	
	public ClientRegistry(Minecraft client){
		this.runningClient = client;
	}
	
	public Minecraft getClient(){
		return this.runningClient;
	}
}
