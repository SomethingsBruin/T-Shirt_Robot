package com.cc.arm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

/**
 * Represents the shooter part of the mechanism.
 */
public class Shooter
{
    //The singleton object of the shooter.
    private static Shooter _instance = null;
    
    //The relay which controlls the shooter piston.
    Relay _shooterRelay = null;
    
    //Represents the limit switch for the shooter.
    DigitalInput _limitSwitch = null;

    private Shooter()
    {
        //Sets the shooter relay to channel 1 on the Digital IO.
        _shooterRelay = new Relay( 1 );
        _shooterRelay.setDirection( Relay.Direction.kForward );
        
        //Sets the limit switch to channel 8.
        _limitSwitch = new DigitalInput( 8 );
    }
    
    /**
     * Returns the singleton object of the shooter.
     * 
     * @return The singleton object of the shooter.
     */
    public static Shooter getInstance()
    {
        //If there is not a singleton object made... 
        if ( _instance == null )
        {
            //Make the only shooter object.
            _instance = new Shooter();
        }

        //Then return the shooter object.
        return _instance;
    }

    /**
     * Turns the shooter on.
     */
    public void turnOn()
    {
        //Set the relay to go forward.
        _shooterRelay.set( Relay.Value.kForward );
    }

    /**
     * Turns the shooter off.
     */
    public void turnOff()
    {
        //Set the relay to turn off.
        _shooterRelay.set( Relay.Value.kOff );
    }
    
    /**
     * Finds the state of the limit switch on the shooter.
     * 
     * @return Return the state of the limit switch.
     */
    public boolean getLimit()
    {
        //Finds the state of the limit switch.
        boolean state = _limitSwitch.get();
        
        //Returns the state.
        return state;
    }
}