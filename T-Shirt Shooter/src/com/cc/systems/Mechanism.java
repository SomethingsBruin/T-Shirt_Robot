package com.cc.systems;

import com.cc.arm.Shooter;
import com.cc.arm.ArmSet;
import com.cc.outputs.motors.CCTalon;
import com.cc.outputs.motors.CCVictor;
import com.cc.arm.*;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class represents the Mechanism on the robot and is responsible for
 * operating the shooter and the pick-up mechanism of the robot.
 */
public class Mechanism 
{
    //The singleton object of the Mechanism class.
    private static Mechanism _instance = null;

    //The talon which represents the pivot of the mechanism arm.
    private CCTalon _pivot;
    //private CCVictor _pivot;

    //The victors which represent the motors which intake the balls into the mechanism.
    private CCVictor _intakeOne;
    private CCVictor _intakeTwo;
    
    //The spike that controls the light of the camera.
    private Relay _cameraLight;

    //The actual shooter mechanism and the thread which cocks the shooter.
    private Shooter _shooter;
    private ShooterReset _shooterReset;
    
    //The thread that sets the state of the arm.
    private ArmSet _armSet;
    
    //The potentiometer object of the mechanism. 
    private AnalogChannel _potent;

    private Mechanism() 
    {
        //Initializes the pivot talon.
        _pivot = new CCTalon( 5, false );
//        _pivot = new CCVictor( 5, false );

        //Initializes the victors for the intake.
        _intakeOne = new CCVictor( 6, false );
        _intakeTwo = new CCVictor( 7, false );
        
        //Initializes the spike for the camera.
        _cameraLight = new Relay( 2 );
        _cameraLight.setDirection( Relay.Direction.kBoth );
        
        //Gets singleton of the shooter obejct.
        _shooter = Shooter.getInstance();
        
        //Initializes the potentiometer on channel 1.
        _potent = new AnalogChannel( 1 );
    }

    /**
     * Returns the singleton object of the Mechanism..
     *
     * @return The singleton object of the Mechanism.
     */
    public static Mechanism getInstance() 
    {
        //If the instance of the mechanism has not been initialized yet then...
        if( _instance == null ) 
        {
            //Initialize the singletone Mechanism object.
            _instance = new Mechanism();
        }

        //Return the singleton object of the Mechanism.
        return _instance;
    }

    /**
     * Shoots the shooter and then cocks the shooter.
     */
    public void shoot() 
    {
        //If the thread has not been created...
        if( _shooterReset == null )
        {
            //Create a new thread.
            _shooterReset = new ShooterReset( _shooter, SmartDashboard.getNumber(  " Arm Shooting Delay: " ) );
        }
        
        //If we are not already shooting...
        if( !_shooterReset.isAlive() ) 
        {
            //Create a new thread and then run that thread.
            _shooterReset = new ShooterReset( _shooter, SmartDashboard.getNumber( " Arm Shooting Delay: " ) );
            _shooterReset.start();
        }
    }
    
    /**
     * Sets the motors on the mechanism to intake the ball.
     */
    public void intake()
    {
        //Sets both motors to full forward to intake the ball.
        _intakeOne.set( 1.0 );
        _intakeTwo.set( 1.0 );       
    }
    
    /**
     * Sets the motors on the mechanism to eject the ball.
     */
    public void eject()
    {
        //Set both motors to full reverse to eject the ball.
        _intakeOne.set( -1.0 );
        _intakeTwo.set( -1.0 );      
    }
    
    /**
     * Stops the intake motors on the mechanism.
     */
    public void stopIntake()
    {
        //Stops both motors.
        _intakeOne.set( 0.0 );
        _intakeTwo.set( 0.0 );       
    }
    
    /**
     * Lowers the arm on the mechanism at the given speed.
     * 
     * @param speed The speed at which to lower the arm.
     */
    public void lowerArm( double speed )
    {
        //Raises the arm at the given speed.
        _pivot.set( speed );
    }
    
    /**
     * Raises the arm on the mechanism at the given speed.
     * 
     * @param speed The speed at which to raise the arm.
     */
    public void raiseArm( double speed )
    {
        //Lowers the arm at the given speed.
        _pivot.set( -speed );
    }
    
    /**
     * Stops the arm on the mechanism were it is.
     */
    public void stopArm()
    {
        _pivot.set( 0.0 );
    }
    
    /**
     * Returns whether the mechanism is shooting.
     * 
     * @return Whether the mechanism is shooting or not.
     */
    public boolean isShooting()
    {
        //Gets whether the mechanism is shooting and returns it.
        boolean isAlive = _shooterReset != null && _shooterReset.isAlive();
        return isAlive;
    }
    
    /**
     * Returns whether the mechanism is shooting.
     * 
     * @return Whether the arm is shooting or not.
     */
    public boolean isSettingArm()
    {
        //Gets whether the mechanism is shooting and returns it.
        boolean isAlive = _armSet != null && _armSet.isAlive();
        return isAlive;
    }
    
    /**
     * Gets the current value of the potentiometer on the arm.
     * 
     * @return The value of the potentiometer.
     */
    public double getPotent()
    {
        //Finds the potentiometer value and returns it.
        double value = _potent.getValue();
        return value;
    }
    
    /**
     * Sets the state of the arm to the given place.
     * 
     * @param state The given place of the arm.
     */
    public void setArm( int state )
    {
        //If the thread has not been created...
        if( _armSet == null )
        {
            //Create a new thread.
            _armSet = new ArmSet( state );
        }
        
        //If we are not already shooting...
        if( !_armSet.isAlive() ) 
        {
            //Create a new thread and then run that thread.
            _armSet = new ArmSet( state );
            _armSet.start();
        }
    }
    
    /**
     * Turns the light of the camera on.
     */
    public void lightOn()
    {
        //Turns the camera light on.
        _cameraLight.set( Relay.Value.kForward );
    }
    
    /**
     * Turns the light of the camera off.
     */
    public void lightOff()
    {
        //Turns the camera light off.
        _cameraLight.set( Relay.Value.kOff );
    }
}