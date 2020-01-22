package mrobsidy.rockycore.misc.debug.api;

public interface IDebugWriter {
	public void setDebugMode(EnumDebugMode mode);
	public void setDebugPriority(EnumDebugType type);
	public void debug(String text, EnumDebugType type);
}
