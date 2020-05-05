package mrobsidy.rockycore.util.math;

public class CatenaryHelper {
	
	/**
	 * Calculates the catenary curve between two poins.
	 * 
	 * If the dimensions of starting and ending do not match, this method will return null.
	 * This method will only accept three-dimensional vectors. 4D catenaries are cool, but not really
	 * all that useful.
	 * 
	 * @param starting - the first Point of the catenary
	 * @param ending - the second point of the catenary
	 * @param ropeLength - the length of the rope connecting the two points
	 * @param resolution - the amount of vertices this method returns
	 * @return an array of length resolution that contains the vectors to the points of the catenary.
	 * 
	 * 
	 * This method is based heavily off of the getConnectionCatenary() function from https://github.com/BluSunrize/ImmersiveEngineering/blob/1.14/src/main/java/blusunrize/immersiveengineering/api/ApiUtils.java#L457 .
	 */
	public static Vector[] getCatenaryCurve(Vector starting, Vector ending, double slack, int resolution) {
		if(starting.getDimension() != ending.getDimension()) return null;
		if(starting.getDimension() != 3) return null;
				
		Vector diff = VectorUtils.subtractVector(starting, ending);
		
		double dx = diff.getComponent(0);
		double dy = diff.getComponent(1);
		double dz = diff.getComponent(2);
		
		final int vertices = resolution;
		double dw = Math.sqrt(dx*dx+dz*dz);
		double k = Math.sqrt(dx*dx+dy*dy+dz*dz)*slack;
		double l = 0;
		int limiter = 0;
		while(limiter < 300)
		{
			limiter++;
			l += 0.01;
			if(Math.sinh(l)/l >= Math.sqrt(k*k-dy*dy)/dw)
				break;
		}
		double a = dw/2/l;
		double offsetX = (0+dw-a*Math.log((k+dy)/(k-dy)))*0.5;
		double offsetY = (dy+0-k*Math.cosh(l)/Math.sinh(l))*0.5;
		Vector[] vex = new Vector[vertices+1];

		vex[0] = new Vector(new double[] { starting.getComponent(0), starting.getComponent(1), starting.getComponent(2)});
		for(int i = 1; i < vertices; i++)
		{
			float posRelative = i/(float)vertices;
			double x = 0+dx*posRelative;
			double z = 0+dz*posRelative;
			double y = a*Math.cosh((dw*posRelative-offsetX)/a)+offsetY;
			vex[i] = new Vector(new double[] {starting.getComponent(0)+x, starting.getComponent(1)+y, starting.getComponent(2)+z});
		}
		vex[vertices] = new Vector(new double[] {ending.getComponent(0), ending.getComponent(1), ending.getComponent(2)});

		return vex;
	}
}
