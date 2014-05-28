package com.cc.outputs.motors;

import edu.wpi.first.wpilibj.Victor;

/**
 * A convenience for the victor speed controller which stores the orientation of
 * the motor on the chassis.
 */
public class CCVictor extends Victor
{ 
     //A flag which represents the orientation of the motor.
    private boolean _isReversed;
    
    /**
     * The constructor for the CCVictor object.
     * 
     * @param port The port that the victor is plugged into on the chassis.
     * @param isReversed The flag which represents the orientation of the robot.
     */
    public CCVictor( int port, boolean isReversed )
    {
        //Creates a normal victor plugged into the correct port.
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
        
        //Set the speed of the motor through the victor class.
        super.set( speed );
    }
}