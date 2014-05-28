/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cc.arm;

import com.cc.systems.Mechanism;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Ursa
 */
public class CockShooter extends Thread
{
    //The shooter object in the thread.
    private Shooter _shooter;
    private Mechanism _mechanism;
    
    /**
     * Creates the thread which shoots the shooter and then resets the shooter.
     * 
     * @param shooter The object which the thread will shoot and reset.
     */
    public CockShooter()
    {
        //Initializes the shooter and mechanism object in the class.
        _shooter = Shooter.getInstance();
        _mechanism = Mechanism.getInstance();
    }
    
    public void run()
    {      
        //Run the shooter until limit switch is released
        _shooter.turnOn();
        _mechanism.intake();
        
        //Run motor until limit switch is pressed
        while ( _shooter.getLimit() == false )
        {
            //Do nothing and wait 1/20 of a second.
            ShooterReset.yield();
            Timer.delay( 0.05 );
        }
        
        //Turn off the shooter when the limit switch is pressed.
        _shooter.turnOff();
        _mechanism.stopIntake();
    }
}
