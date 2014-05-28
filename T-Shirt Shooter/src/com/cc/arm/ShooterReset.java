package com.cc.arm;

import com.cc.systems.Mechanism;

import edu.wpi.first.wpilibj.Timer;

/**
 * The class which is a thread that shoots the shooter then resets the shooter.
 */
public class ShooterReset extends Thread
{
    //The shooter object in the thread.
    private Shooter _shooter;
    
    //The mechanism object in the thread.
    private Mechanism _mechanism;
    
    //The delay between running the intake and shooting.
    private double _delay;
    
    /**
     * Creates the thread which shoots the shooter and then resets the shooter.
     * 
     * @param shooter The object which the thread will shoot and reset.
     */
    public ShooterReset( Shooter shooter, double delay )
    {
        //Initializes the shooter and mechanism object in the class.
        _shooter = shooter;
        _mechanism = Mechanism.getInstance();
        
        //Sets the delay between the ejection and the shooting.
        _delay = delay;
    }
    
    public void run()
    {
        //Start ejecting the ball and wait one second to shoot the ball.
        _mechanism.eject();
        
        //Wait 0.1 seconds to shoot the mechanism.
        Timer.delay( _delay );
        
        //Run the shooter until limit switch is released
        _shooter.turnOn();

        //Wait one second so it can clear the switch before monitoring the limit switch again.
        Timer.delay( 1.0 );
        
        //Turn off the ejection.
        _mechanism.stopIntake();
        
        //Run motor until limit switch is pressed
        while ( _shooter.getLimit() == false )
        {
            //Do nothing and wait 1/20 of a second.
            ShooterReset.yield();
            Timer.delay( 0.05 );
        }
        
        //Turn off the shooter when the limit switch is pressed.
        _shooter.turnOff();
    }
}