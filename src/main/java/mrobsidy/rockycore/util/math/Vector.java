package mrobsidy.rockycore.util.math;

import java.util.ArrayList;

//Disclaimer: I wrote this class while really high and I watched the office writing it. 
//No guarantees that this class actually works as intended.

public class Vector {
	private final ArrayList<Double> components;
	private final int length;
	
	public Vector(int length) {
		components = new ArrayList<Double>();
		this.length = length;
	}
	
	public Vector(double[] comp) {
		components = new ArrayList<Double>();
		for(Double t : comp) {
			components.add(t);
		}
		this.length = comp.length;
	}
	
	@Override
	public String toString() {
		String returnable = "Vector: ";
		
		for(double component : components) {
			returnable = returnable + component + " ";
		}
		
		return returnable;
	}
	
	/**
	 * This initializes all values in the vector to a 0 so no illegalAccess errors can be thrown.
	 * 
	 */
	public Vector initializeVector() {
		int i = 0;
		while(i < length) {
			components.add(i, 0d);
			i++;
		}
		return this;
	}
	
	public Double[] getAllComponents() {
		return this.components.toArray(new Double[components.size()]);
	}
	
	/**
	 * Sets the component at index to value. Please remember that arrays start at 0.
	 * 
	 * @param index
	 * @param value
	 */
	public void setComponent(int index, Double value) {
		if(index >= this.length) throw new ArrayIndexOutOfBoundsException("Tried adding a Vector component in after the end of the vector.");
		components.add(index, value);
	}
	
	/**
	 * Gets the component at index. Please remember that arrays start at 0.
	 * 
	 * @param index
	 * @return
	 */
	public Double getComponent(int index) {
		if (index >= this.length) throw new ArrayIndexOutOfBoundsException("Tried retrieving a Vector component beyond the size of the vector");
		return components.get(index);
	}
	
	public int getDimension() {
		return this.getSize();
	}
	
	public int getSize() {
		return this.length;
	}
	
	/**
	 * Returns the length of this vector.
	 * 
	 * @return
	 */
	public double getLength() {
		double intermediate = 0d;
		
		//lmao this should work
		for(Double d : components) {
			intermediate += Math.pow(d, 2);
		}		
		return Math.sqrt(intermediate);
	}
}
