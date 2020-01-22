package mrobsidy.rockycore.misc.debug.api;

public enum EnumDebugType {
	DEBUG, INFO, WARNING, ERROR;
	
	public static boolean getPriority(EnumDebugType given, EnumDebugType threshold) {
		boolean priority = false;
		
		if(threshold == DEBUG) {
			priority = true;
		} else if(threshold == INFO && given != DEBUG) {
			priority = true;
		} else if(threshold == WARNING && given != DEBUG && given != INFO) {
			priority = true;
		} else if(threshold == ERROR && given == ERROR) {
			priority = true;
		}
		
		return priority;
	}
}
