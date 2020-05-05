package mrobsidy.rockycore.util.math;

import scala.actors.threadpool.Arrays;

public class VectorUtils {
	
	public static Vector createInvalidVector(int length) {
		double[] points = new double[length];
		Arrays.fill(points, Double.MIN_VALUE);
		return new Vector(points);
	}
	
	public static boolean areComparable(Vector a, Vector b) {
		if(a.getSize() != b.getSize() || a.getSize() == 0) return false;
		if(a.getComponent(0).getClass() != b.getComponent(0).getClass()) return false;
		if(a.getComponent(0).getClass() != (Double.class)) return false;
		return true;
	}
	/**
	 * Inverts every component (aka turns the vector the other way).
	 * 
	 * @param a
	 * @return
	 */
	public static Vector invertVector(Vector a) {
		Vector vector = new Vector(a.getSize());
		int i = 0;
		for(double component : a.getAllComponents()) {
			vector.setComponent(i, -(a.getComponent(i)));
			i++;
		}
		
		return vector;
	}
	
	/**
	 * Adds two vectors
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	
	public static Vector addVectors(Vector a, Vector b) {
		
		if(!areComparable(a, b)) return VectorUtils.createInvalidVector(a.getSize());
		
		Vector newVector = new Vector(a.getSize());
		int i = 0;
		for(double t : a.getAllComponents()) {
			newVector.setComponent(i,  t + b.getComponent(i));
			i++;
		}
		return newVector;
	}
	
	/**
	 * Subtracts b from a.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector subtractVector(Vector a, Vector b) {
		Vector invertedB = VectorUtils.invertVector(b);
		return VectorUtils.addVectors(a, invertedB);
	}
	
	public static double dotProduct(Vector a, Vector b) {
		if(!areComparable(a, b)) return Double.MIN_VALUE;
		double returnable = 0d;
		
		int i = 0;
		for(double point : a.getAllComponents()) {
			returnable += point + b.getComponent(i);
			i++;
		}
		
		return returnable;
	}
}
