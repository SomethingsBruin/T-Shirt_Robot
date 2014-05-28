/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import com.cc.autonomous.*;
import com.cc.inputs.driver.*;
import com.cc.shooter.Shooter;
import com.cc.shooter.ShooterCompressor;
import com.cc.systems.*;
import com.cc.utility.Utility;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot 
{
    //The robot driver.
    private Driver _driver;
    
    //The robot chassis.
    private Chassis _chassis;
    
    //The Shooter Compressor
    private ShooterCompressor _compressor;
    
    //The TShirt Shooter
    private Shooter _tShirtShooter;
    
    //Initializes the original PID constants for the chassis. These are dynamically changable in the Smart Dashboard.
    private final double _KP = 0.7;
    private final double _KI = 0.008;
    private final double _KD = 0.04;
    
    //The robot mechanism.
    private Mechanism _shooter;
    
    //The maximum and minimum extremes for the position of the arm
    private final double _MAX_ARM_EXTREME = -1000000;
    private final double _MIN_ARM_EXTREME = 1000000;
    
    //The maximum up and down speed of the arm.
    private final double _ARM_UP_SPEED = 1.0;
    private final double _ARM_DOWN_SPEED = -1.0;
    
    //The constants for the middle position of the arm.
    private final double _ARM_MIDDLE_UP_POSITION = 220.0;
    private final double _ARM_MIDDLE_DOWN_POSITION = 220.0;
    private final double _ARM_MIDDLE_UP_SPEED = 0.700;
    private final double _ARM_MIDDLE_DOWN_SPEED = 0.350;
          
    //The delay between ejecting the ball and shooting the ball.
    private final double _DELAY = 0.080;
    
    //The exponential constant for the driver.
    private final double _DRIVEREXPO = 2.0;
    
    //The rotational exponential deadzone for the driver.
    private final double _ROT_DEADZONE = 0.05;
    
    private final double TICKS_PER_INCH = -53.199;
    
    //The autonomous constants.
    private final double _AUTO_CENTER_TURN_SPEED = 0.3;
    private final double _AUTO_CENTER_TURN_TIME = 0.3;
    private final double _AUTO_CENTER_FORWARD_SPEED = 0.55;
    private final double _AUTO_CENTER_FORWARD_TIME = 2.7;
    private final double _AUTO_CENTER_FORWARD_DISTANCE = 95;
    private final double _AUTO_SIDE_FORWARD_TIME = 1.7;
    private final double _AUTO_SIDE_FORWARD_SPEED = 0.75;
    private final double _AUTO_SIDE_FORWARD_DISTANCE = 85;
    
    //The AutoCommand to be run in autonomous.
    private AutoCommand _autoCommand;
    
    //Declares the Smart Dashboard device which chooses the Driver and the drive type.
    private SendableChooser _driverChooser;
    private SendableChooser _driveTypeChooser;
    
    //Whether or not to use child mode
    private int _childMode = 0;
    
    //Declares the Smart Dashboard device which chooses which AutoCommand to run.
    private SendableChooser _autoCommandChooser;
    
    //A flag that insure autonomous only goes once.
    private boolean _autoFlag = true;

    /**
     * This function is run when the robot is first started up and initializes
     * the mechanism and chassis, and does all the Smart Dashboard initialization.
     */
    public void robotInit() 
    {
        //Get the chassis object.
        _chassis = Chassis.getInstance();
        
        //Puts the PID constants into the Smart Dashboard so they are dynamicly changable.
        SmartDashboard.putNumber( " P-Constant: ", _KP );
        SmartDashboard.putNumber( " I-Constant: ", _KI );
        SmartDashboard.putNumber( " D-Constant: ", _KD );
        
        //Get the mechanism object.
        _shooter = Mechanism.getInstance();
        
        //Turns the camera light on.
        _shooter.lightOn();
        
        //Creates a new Compressor
        _compressor = ShooterCompressor.getInstance();
        
        //Creates a new Tshirt Shooter
        _tShirtShooter = Shooter.getInstance();
        
        //Puts the maximum arm up and down speeds into the SmartDashboard
        SmartDashboard.putNumber( " Arm Up-Speed: " , _ARM_UP_SPEED );
        SmartDashboard.putNumber( " Arm Down-Speed: " , _ARM_DOWN_SPEED );
        
        //Puts the minimum and maximum arm extremes into the SmartDashboard.
        SmartDashboard.putNumber( " Arm Maximum Extreme: " , _MAX_ARM_EXTREME );
        SmartDashboard.putNumber( " Arm Minimum Extreme: " , _MIN_ARM_EXTREME );
        
        //Puts the middle arm position constants into the SmartDashboard.
        SmartDashboard.putNumber( " Arm Middle Up Position: " , _ARM_MIDDLE_UP_POSITION );
        SmartDashboard.putNumber( " Arm Middle Down Position: " , _ARM_MIDDLE_DOWN_POSITION );
        SmartDashboard.putNumber( " Arm Middle Up Speed: " , _ARM_MIDDLE_UP_SPEED );
        SmartDashboard.putNumber( " Arm Middle Down Speed: " , _ARM_MIDDLE_DOWN_SPEED );
        
        //Puts the arm shooting delay into the SmartDashboard.
        SmartDashboard.putNumber( " Arm Shooting Delay: " , _DELAY );
        
        //Puts the expo of the driver into the SmartDashboard.
        SmartDashboard.putNumber( " Driver Expo: " , _DRIVEREXPO );
        
        //Puts the rotational exponential deadzone into the SmartDashboard.
        SmartDashboard.putNumber( " Rot Dead-Zone: " , _ROT_DEADZONE );
        
        SmartDashboard.putNumber( " Encoder Ticks: " , TICKS_PER_INCH );
                      
        //Puts the autonomous constants into the Smart Dashboard.
        SmartDashboard.putNumber( " Auto-Center Turn Speed: " , _AUTO_CENTER_TURN_SPEED );
        SmartDashboard.putNumber( " Auto-Center Turn Time: " , _AUTO_CENTER_TURN_TIME );
        SmartDashboard.putNumber( " Auto-Center Forward Speed: " , _AUTO_CENTER_FORWARD_SPEED );
        SmartDashboard.putNumber( " Auto-Center Forward Time: " , _AUTO_CENTER_FORWARD_TIME );
        SmartDashboard.putNumber( " Auto-Center Forward Distance: ", _AUTO_CENTER_FORWARD_DISTANCE );
        SmartDashboard.putNumber( " Auto-Side Forward Speed: " , _AUTO_SIDE_FORWARD_SPEED );
        SmartDashboard.putNumber( " Auto-Side Forward Time: " , _AUTO_SIDE_FORWARD_TIME );
        SmartDashboard.putNumber( " Auto-Side Forward Distance: " , _AUTO_SIDE_FORWARD_DISTANCE );
        
        //Child mode chooser
        SmartDashboard.putNumber( " Child Mode: ", _childMode );
        
        //Initializes the chooser devices.
        _driverChooser = new SendableChooser();
        _driveTypeChooser = new SendableChooser();
        _autoCommandChooser = new SendableChooser();
        
        //Assigns a index number to each Driver type. The Airplane Controller is the default selection.
        _driverChooser.addDefault( "Airplane Controller" , new Integer( 0 ) );//0 for the Airplane Controller.
        _driverChooser.addObject( "Attack Three" , new Integer( 1 ) );//1 for the Attack Three joysticks.
        _driverChooser.addObject( "XBox Controller" , new Integer( 2 ) );//2 for the XBox Controller.
        
        //Assigns a index number to each drive type. The Normal Holo Drive is the default selection.
        _driveTypeChooser.addDefault( "Normal Holo Driver" , new Integer( 1 ) );//1 for the Normal Holo Dirve.
        _driveTypeChooser.addObject( "Relative Holo Drive" , new Integer( 0 ) );//0 for the Relative Holo Drive.
        
        //Adds each AutoCommand into the Smart Dashboard.
        _autoCommandChooser.addDefault( "Do Nothing" , new AutoNothing() );
        _autoCommandChooser.addObject( "Center Auto Command" , new AutoCenter() );
        _autoCommandChooser.addObject( "Side Auto Command" , new AutoSide() );
        
        //Puts the chooser devices into the Smart Dashboard.
        SmartDashboard.putData( "Driver Controller" , _driverChooser );
        SmartDashboard.putData( "Drive Type" , _driveTypeChooser );
        SmartDashboard.putData( "Auto Command" , _autoCommandChooser );
        
        //Finds the assigned index value of the driver type choosen
        int index = ( (Integer) _driverChooser.getSelected() ).intValue();
        
        //The type of the driver will be choosen from the given index value from the Smart Dashboard.
        switch( index )
        {
            //The XBox Controller if the index is 2.
            case 2:
                _driver = XBoxController.getInstance();
                break;
                
            //The Attack Three joysticks if the index is 1.
            case 1:
                _driver = AttackThree.getInstance();
                break;
            
            //The Airplane Controller if the index is 0 (or anything else).
            default:
            case 0:
                _driver = AirplaneController.getInstance();
                break;
        }
    }
    
    /**
     * This function is called once when robot is disabled and prompts the user
     * that the robot is disabled. Also resets the gyro on the robot to 0 degrees.
     */ 
    public void disabledInit()
    {
        //Prompts that the robot is disabled.
        System.out.println( "Robot is Disabled" );
        
        //Sets the automous flag to be false.
        _autoFlag = false;
        
        //Resets the gyro to 0 degrees.
        _chassis.resetGyro();
        
        //Turns the camera light on.
        _shooter.lightOn();     
    }
    
    /**
     * A function which is called once at the beginning of Autonomous and finds which
     * AutoCommand that will be run, resets the gyro, and cocks the mechanism.
     */
    public void autonomousInit()
    {
        //Finds the selected AutoCommand.
        _autoCommand = (AutoCommand) _autoCommandChooser.getSelected(); 
        
        //Resets the gyro
        _chassis.resetGyro();
        
        //Turns the light of the camera on.
        _shooter.lightOn();
        
    }

    /**
     * This function is called periodically during autonomous and runs the given
     * AutoCommand once.
     */
    public void autonomousPeriodic() 
    {
        //Turns the camera light on.
        _shooter.lightOn();
        
        //If the flag hasn't been raised...
        if( !_autoFlag )
        {
            //Runs the given AutoCommand and set the auto flag to be true.
            _autoCommand.runAutoCommand();
        
            _autoFlag = true;
        }
    }
    
    /**
     * A function which is called once at the beginning of Tele-Op and finds which
     * driver type is wanted, resets the gyro, and cocks the mechanism.
     */
    public void teleopInit()
    {   
        //Finds the assigned index value of the driver type choosen
        int index = ( (Integer) _driverChooser.getSelected() ).intValue();
        
        //The type of the driver will be choosen from the given index value from the Smart Dashboard.
        switch( index )
        {
            //The XBox Controller if the index is 2.
            case 2:
                _driver = XBoxController.getInstance();
                break;
                
            //The Attack Three joysticks if the index is 1.
            case 1:
                _driver = AttackThree.getInstance();
                break;
            
            //The Airplane Controller if the index is 0 (or anything else).
            default:
            case 0:
                _driver = AirplaneController.getInstance();
                break;
        }
        
        //Reset the gyro.
        _chassis.resetGyro();
    }

    /**
     * This function is called periodically during operator control and it will
     * drive the robot relative to the driver, not its starting position. The robot
     * will square itself to the wall on command.
     */
    public void teleopPeriodic() 
    {
        //Finds which drive type is wanted from the SmartDashBoard.
        int index = ( (Integer) _driveTypeChooser.getSelected() ).intValue();
        
        //Based on the selection above, choose and run the selected drive type.
        switch( index )
        {
            //1 is Normal Holo Drive.
            case 1:
                //Drives the chassis not relative to the driver.
                _chassis.holoDrive( Utility.limitRange( _driver.getY(), .25, -.25 ) , Utility.limitRange( _driver.getX( ), .25, -.25 ) , Utility.limitRange( _driver.getRot(), 0.25, -0.25 ) );
                break;
            
            //0 and default is Relative Holo Drive.
            default:
            case 0:
                //Drives the chassis relative to the driver.
                //Makes sure the power is limited if in childMode
                if( _childMode == 1 )
                {
                    _chassis.relativeHoloDrive( Utility.limitRange( _driver.getY(), .25, -.25 ) , Utility.limitRange( _driver.getX( ), .25, -.25 ) , Utility.limitRange( _driver.getRot(), 0.25, -0.25 ) );
                }
                else
                {
                    _chassis.relativeHoloDrive( _driver.getY() , _driver.getX() , Utility.limitRange( _driver.getRot(), 0.75, -0.75 ) );
                }
                break;
        }
        
//        System.out.println( _driver.getRot() );
        
        //If the analog button's sum is negative and the arm is below the minimum extreme...
        if( _driver.getArm() < 0.0 && _shooter.getPotent() < SmartDashboard.getNumber( " Arm Minimum Extreme: " ) )
        {
            //The arm on the mechanism will raise at the analog speed.
            _shooter.lowerArm( _driver.getArm() );
        }
        else if( _driver.getArm() > 0.0 && _shooter.getPotent() > SmartDashboard.getNumber( " Arm Maximum Extreme: " ) )//Else if the analog button's sum is positive and the arm is above the minimum extreme...
        {
            //The arm on the mechanism will lower at analog speed.
            _shooter.raiseArm( -1 * _driver.getArm() );
        }
        else//Else stop the arm.
        {
            _shooter.stopArm();
        }
         
        //Runs the compressor
        _compressor.runCompressor( );
        
        //Check what how long a shot must be used and calls it
        if( ( _driver.getThirdButton() && _driver instanceof AirplaneController ) || ( _driver.getPriButton( ) && _driver instanceof XBoxController ) )
        {
            //Short Shot
            _tShirtShooter.shoot( .05 );
            System.out.println("Short Shot");
        }
        if( _driver.getSecButton() )
        {
            //Medium Shot
            _tShirtShooter.shoot( .1 );
            System.out.println("Medium Shot");
        }
        if(_driver.getFourthButton() )
        {
            //Long Shot
            _tShirtShooter.shoot( .15 );
            System.out.println("Long Shot");
        }
        if( ( _driver.getFifthButton() && _driver instanceof AirplaneController ) || (_driver.getThirdButton( ) && _driver instanceof XBoxController ) )
        {
            //Empty Tank Shot
            _tShirtShooter.shoot( .5 );
            System.out.println("Empty Tank Shot");
        }
    }
    
    /**
     * A function which is called once at the beginning of test.
     */
    public void testInit()
    {
        //Finds the assigned index value of the driver type choosen
        int index = ( (Integer) _driverChooser.getSelected() ).intValue();
        
        //The type of the driver will be choosen from the given index value from the Smart Dashboard.
        switch( index )
        {
            //The XBox Controller if the index is 2.
            case 2:
                _driver = XBoxController.getInstance();
                break;
                
            //The Attack Three joysticks if the index is 1.
            case 1:
                _driver = AttackThree.getInstance();
                break;
            
            //The Airplane Controller if the index is 0 (or anything else).
            default:
            case 0:
                _driver = AirplaneController.getInstance();
                break;
        }
    }
    
    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() 
    {
        _driver.printAxes( );
    }
    
}