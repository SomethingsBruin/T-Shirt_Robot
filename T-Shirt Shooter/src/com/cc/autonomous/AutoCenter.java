package com.cc.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The class which represents the AutoCommand which starts in the center position
 * figures out which goal is the hot goal, moves forward into position, turns towards
 * the hot goal, and then shoots the ball.
 */
public class AutoCenter extends AutoCommand
{
    /**
     * Calls the constructor from AutoCommand.
     */
    public AutoCenter()
    {
        super();
    }
    
    /**
     * Runs this particular AutoCommand which starts in the center position
     * figures out which goal is the hot goal, moves forward into position, turns towards
     * the hot goal, and then shoots the ball.
     */
    public void runAutoCommand()
    {
        //Creates and starts the timer that counts to when the hotgoal is active.
        Timer timer = new Timer();
        timer.start();
        
        //Counts the number of blobs from the camera.
        double blobs = getNumBlobs();
        
        //Moves 12 feet forward at 0.55 speed. Equivalent is 2.7 seconds at same speed.
        _chassis.move( SmartDashboard.getNumber( " Auto-Center Forward Distance: " ), SmartDashboard.getNumber( " Auto-Center Forward Speed: " ) );
//        _chassis.holoDrive( SmartDashboard.getNumber( " Auto-Center Forward Speed: " ) , 0.0, 0.0 );
//        Timer.delay( SmartDashboard.getNumber( " Auto-Center Forward Time: " ) );
//        _chassis.stop();
        
        //Stops the robot.
        _chassis.stop();
        
        //If the left side was hot, turn right to the hot goal
        if( blobs < 2 )
        {
            //Turn right at 0.3 speed for 0.3 seconds
            _chassis.holoDrive( 0.0, 0.0, SmartDashboard.getNumber( " Auto-Center Turn Speed: " ) );
            Timer.delay( SmartDashboard.getNumber( " Auto-Center Turn Time: " ) );
            _chassis.stop();
        }
        else//Else turn left to the hot goal
        {
            //Turn left at 0.3 speed for 0.3 seconds.
            _chassis.holoDrive( 0.0, 0.0, -SmartDashboard.getNumber( " Auto-Center Turn Speed: " ) );
            Timer.delay( SmartDashboard.getNumber( " Auto-Center Turn Time: " ) );
            _chassis.stop();
        }
        
//        //Wait until a least 6 seconds have passed in Auto
//        while( timer.get() < 6.00 )
//        {
//            //Do Nothing.
//            ;
//        }
        
        //Sets the arm to the top position.
        _mechanism.setArm( 1 );
        
        //Waits until arm is done setting.
        while( _mechanism.isSettingArm() )
        {
            ;
        }
        
        //Then shoot the mechanism.
        _mechanism.shoot();
        
        //Stops the timer.
        timer.stop();
    }
}