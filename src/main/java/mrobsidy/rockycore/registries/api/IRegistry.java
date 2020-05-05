package mrobsidy.rockycore.registries.api;

public interface IRegistry {
	public void init();
	public void reset();
	public void cleanup();
	public boolean shouldReset();
}
