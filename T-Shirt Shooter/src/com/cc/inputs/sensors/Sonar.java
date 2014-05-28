package com.cc.inputs.sensors;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * A class which represents a sonar sensor.
 */
public class Sonar
{
    //The raw analog input.
    private AnalogChannel _sonar;
    
    //The constant which represents how many ticks are in an inch for the sensor.
    private final double _TICKSPERINCH = 4.840;
    
    /**
     * A constructor which creates the sonar object. 
     * 
     * @param channel The channel the sonar is plugged into on the analog module.
     */
    public Sonar( int channel )
    {
        //Gets the analog input from the given channel.
        _sonar = new AnalogChannel( channel ); 
    }
    
    /**
     * Gets the distance from an object in front of the sensor in inches.
     * 
     * @return Returns the distance from an object in inches.
     */
    public double getDistance()
    {
        //Finds the raw sonar value and converts it into inches.
        double distance = _sonar.getValue();
        distance /= _TICKSPERINCH;
        
        //Returns the inches from an object.
        return distance;
    }
}