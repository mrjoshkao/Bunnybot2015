package components;

/**
 * Wraps set of three motors that make up one side of the robot
 * @author Trevor
 *
 */
public class ThreeCimGroup {

	public Cim c1;
	public Cim c2;
	public Cim c3;
	
	/**
	 * Creates grouping given three motors
	 * @param m1Chn
	 * @param m2Chn
	 * @param m3Chn
	 */
	public ThreeCimGroup(int m1Chn, int m2Chn, int m3Chn) {
		c1 = new Cim(m1Chn);
		c2 = new Cim(m2Chn);
		c3 = new Cim(m3Chn);
	}
	
	/**
	 * Sets all motors to given speed
	 * @param velocity between -1 to 1
	 */
	public void set(double velocity) {
		c1.set(velocity);
		c2.set(velocity);
		c3.set(velocity);
	}
}
