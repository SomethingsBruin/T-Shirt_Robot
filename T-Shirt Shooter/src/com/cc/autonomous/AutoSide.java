package com.cc.autonomous;

import com.cc.arm.CockShooter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The class which represents the AutoCommand which goes forward and then shoots
 * without determining if the goal is hot or not.
 */
public class AutoSide extends AutoCommand
{
    /**
     * Calls the constructor from AutoCommand.
     */
    public AutoSide()
    {
        super();
    }
    
    /**
     * Runs this particular AutoCommand which goes forward, then either shoots if
     * the hot goal is active or waits until it is active to shoot.
     */
    public void runAutoCommand()
    {
        //Creates and starts the timer that counts to when the hotgoal is active.
        Timer timer = new Timer();
        timer.start();
        
        //Counts the number of blobs from the camera.
        double blobs = getNumBlobs();
        
        CockShooter _object = new CockShooter();
        
        _object.start();
        
        //Moves 12 feet forward at 0.75 speed. Equivalent is 1.8 seconds at same speed.
        _chassis.move( SmartDashboard.getNumber( " Auto-Side Forward Distance: " ), SmartDashboard.getNumber( " Auto-Side Forward Speed: " ) );
       
//        _chassis.holoDrive( SmartDashboard.getNumber( " Auto-Side Forward Speed: " ) , 0.0, 0.0 );
//        Timer.delay( SmartDashboard.getNumber( " Auto-Side Forward Time: " ) );
//        _chassis.stop();
        
        Timer.delay( 0.33 );
        
        //If the goal is not hot.
        if( blobs < 2 )
        {
//            //Wait until the goal is a hot goal.
//            while( timer.get() < 6.0 )
//            {
//                //Do nothing.
//                ;
//            }
            
//            //Sets the arm to the top position.
//            _mechanism.setArm( 1 );
//            
//            //Waits until arm is done setting.
//            while( _mechanism.isSettingArm() )
//            {
//                ;
//            }
            
            //Shoots the mechanism.
            _mechanism.shoot();
        }
        else//Else shoot immeadiatly.
        {
//            //Sets the arm to the top position.
//            _mechanism.setArm( 1 );
//            
//            //Waits until arm is done setting.
//            while( _mechanism.isSettingArm() )
//            {
//                ;
//            }
            
            //Shoots the mechanism.
            _mechanism.shoot();
        }
        
        //Stops the timer.
        timer.stop();
    }
}