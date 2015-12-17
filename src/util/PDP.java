package util;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import config.PDPConfig;

/**
 * Gets data from the PDP
 * @author ROBOTICS
 *
 */
public class PDP {
	private PowerDistributionPanel pdp = new PowerDistributionPanel();
	private Timer tm = new Timer();
	private double[] current = new double[PDPConfig.currentArrayLength]; //TODO Make config value
	private double dI = 0;
	private double dV = 0;
	
	public PDP(){
		for(int i = 0; i < current.length; i++){
			current[i] = 0;
		}
	}
	
	public void updateCurrent(int channel){
		
		for(int i = 1; i < current.length; i++){
			current[i - 1] = current[i];
		}
		
		current[current.length-1] = getCurrent(channel);
	}
	
	/**
	 * Gets data from the PDP in the form of an array, change in voltage and current are time based
	 * @param channel the channel on the PDP that data is being retrieved from
	 * @param time amount of time the function will measure change in voltage and current for
	 * @return double array with the format [voltage, current, power, delta current, delta voltage]
	 */
	public double[] getData(int channel, double time){
		tm.start();
		
		if(tm.get() < time){
			dI += getCurrent(channel);
			dV += getVoltage();
		}
		
		else{
			tm.stop();
			tm.reset();
			dI = 0;
			dV = 0;
		}
		
		return new double[] {getVoltage(),getCurrent(channel),getPower(channel),dI,dV};
	}
	
	/**
	 * Gets data from the PDP in the form of an array, change in voltage and current are cycles based
	 * @param channel the channel on the PDP that data is being retrieved from
	 * @param cycles amount of iterations of code the function will measure change in voltage and current for
	 * @return double array with the format [voltage, current, power, delta current, delta voltage]
	 */
	public double[] getData(int channel, int cycles){
		for(int i = 0; i < cycles; i++)
		{
			dI += getCurrent(channel);
			dV += getVoltage();
		}
		
		dI = 0;
		dV = 0;
		
		return new double[] {getVoltage(),getCurrent(channel),getPower(channel),dI,dV};
	}
	
	public boolean needStop(int channel){
		if(current[(int)(current.length/2)] - current[(int)(current.length/2) + 1] > PDPConfig.minCurrentJump && current[(int)(current.length/2)] - current[(int)(current.length/2) + 1] < PDPConfig.maxCurrentJump){
			if(current[(int)(current.length/2) + 1] > PDPConfig.minCurrentValue)
				return true;
		}
		
		return false; 
	}
	
	/**
	 * gets voltage from the PDP
	 * @return the voltage from the PDP
	 */
	public double getVoltage(){
		return pdp.getVoltage();
	}
	
	/**
	 * gets the current from a specified channel on the PDP
	 * @param channel the channel on the PDP that is being measured
	 * @return the current from the channel
	 */
	public double getCurrent(int channel){
		return pdp.getCurrent(channel);
	}
	
	/**
	 * gets the wattage of the specified channel
	 * @param channel the channel on the PDP that is being measured
	 * @return the amount of power in watts
	 */
	public double getPower(int channel){
		return getVoltage() * getCurrent(channel);
	}
}
