package mrobsidy.rockycore.registries.api;

import java.util.ArrayList;

public class CustomData {
	
	private final Object data;
	private final String name;
	
	public CustomData(Object parData, String parName){
		this.data = parData;
		this.name = parName;
	}
	
	public Class getDataClass(){
		return this.data.getClass();
	}
	
	public Object getData(){
		return this.data;
	}
	
	public String getName(){
		return this.name;
	}
	
}
