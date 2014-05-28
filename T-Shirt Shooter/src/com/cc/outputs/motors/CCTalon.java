package com.cc.outputs.motors;

import edu.wpi.first.wpilibj.Talon;

/**
 * A convenience for the talon speed controller which stores the orientation of
 * the motor on the chassis.
 */
public class CCTalon extends Talon
{
    //A flag which represents the orientation of the motor.
    private boolean _isReversed;
    
    /**
     * The constructor for the CCTalon object.
     * 
     * @param port The port that the talon is plugged into on the chassis.
     * @param isReversed The flag which represents the orientation of the robot.
     */
    public CCTalon( int port, boolean isReversed )
    {
        //Creates a normal talon plugged into the correct port.
        super( port );
        
        //Sets the flag which represents the orientation of the robot.
        _isReversed  = isReversed;
    }
    
    /**
     * Sets the value of the motor.
     * 
     * @param speed The value that the motor is set too.
     */
    public void set( double speed )
    {
        //If the motor is reversed, then inverse the speed to make it go the right direction.
        if( _isReversed )
        {
            //Inverse the speed.
            speed *= -1;
        }
        
        //Set the speed of the motor through the talon class.
        super.set( speed );
    }
}