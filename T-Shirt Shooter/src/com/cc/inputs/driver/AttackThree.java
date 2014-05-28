package com.cc.inputs.driver;

import com.cc.utility.Utility;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The class which represents the driver type for the XBox Controller.
 */
public class AttackThree extends Driver
{
    //The contoller's X constants.
    private final double _XMAX = 1.0;
    private final double _XMIN = -1.0;
    private final double _XCENTER = 0.0;
    
    //The contoller's Y constants. Y Max and Min are switched for normalization process.
    private final double _YMAX = 1.0;
    private final double _YMIN = -1.0;
    private final double _YCENTER = 0.0;
    
    //The contoller's Rotation constants.
    private final double _ROTMAX = 1.0;
    private final double _ROTMIN = -1.0;
    private final double _ROTCENTER = 0.0;
    
    //The contoller's Arm constants.
    private final double _ARMMAX = 1.0;
    private final double _ARMMIN = -1.0;
    private final double _ARMCENTER = 0.0;

    //The joysticks of the controller.
    private Joystick _attackOne; 
    private Joystick _attackTwo;
    
    /**
     * Creates the joystick driver object.
     */
    private AttackThree()
    {
        //Calls the contructor in Dirver.
        super();
        
        //Initializes the joystick on channel 1 and channel 2.
        _attackOne = new Joystick( 1 );
        _attackTwo = new Joystick( 2 );
    }
    
    /**
     * Returns the singleton instance of the driver.
     * 
     * @return Returns the singleton instance of the driver.
     */
    public static Driver getInstance()
    {
        //If the dirver is not an AttackThree, than make it an AttackThree
        if( !( _instance instanceof AttackThree ) )
        {
            _instance = new AttackThree();
        }
        
        //Return the driver.
        return _instance;
    }
    
    /**
     * Prints all the axes in the controller.
     */
    public void printAxes()
    {
        //Goes through 12 axis channels on the joystick and prints their current value.
        for( int i = 0; i <= 12; i++ )
        {
            System.out.print( i + "-One: " + _attackOne.getRawAxis( i ) + " " );
            System.out.print( i + "-Two: " + _attackTwo.getRawAxis( i ) + " " );
        }
        
        //Moves to a new line.
        System.out.println();
    }
  
    /**
     * Prints all the buttons in the controller.
     */
    public  void printButtons()
    {
        //Goes through 12 button channels on the joystick and prints their current value.
        for( int i = 0; i <= 12; i++ )
        {
            System.out.print( i + "-One: " + _attackOne.getRawButton( i ) + " " );
            System.out.print( i + "-Two: " + _attackTwo.getRawButton( i ) + " " );
        }
        
        //Moves to a new line.
        System.out.println();
    }
    
    /**
     * Gets the x value of the controller.
     * 
     * @return Returns the x value.
     */
    public  double getX()
    {
        //Finds the normalized x value of the controller, and then expos it.
        double xValue = normalize( _attackOne.getRawAxis( 1 ), _XMIN, _XMAX, _XCENTER );
        xValue = Utility.expo( xValue, SmartDashboard.getNumber( " Driver Expo: " ) );
        
        //Returns the x value.
        return xValue;
    }
    
    /**
     * Gets the y value of the controller.
     * 
     * @return Returns the y value.
     */
    public double getY()
    {
        //Finds the normalized y value of the controller, and then expos it.
        double yValue = -1 * normalize( _attackOne.getRawAxis( 2 ), _YMIN, _YMAX, _YCENTER );//Multiplies by -1 because the y-axis is inverted.
        yValue = Utility.expo( yValue, SmartDashboard.getNumber( " Driver Expo: " ) );
        
        //Returns the y value.
        return yValue;
    }
    
    /**
     * Gets the rotation value of the controller.
     * 
     * Returns the rotation value.
     */
    public double getRot()
    {
        //Finds the normalized rotation value of the controller, and then expos it.
        double rValue = normalize( _attackTwo.getRawAxis( 1 ), _ROTMIN, _ROTMAX, _ROTCENTER );
        rValue = Utility.expo( rValue, SmartDashboard.getNumber( " Driver Expo: " ) );
        
        //If going backwards...
        if( getY() < -SmartDashboard.getNumber( " Rot Dead-Zone: " ) )
        {
            //Then reverse the rotation value.
            rValue *= -1;
        }
        
        //Returns the rotation value.
        return rValue;
    }
    
    /**
     * Gets the arm value of the controller.
     * 
     * @return Returns the arm value.
     */
    public double getArm()
    {
        //Finds the normalized arm value of the controller and limit the range between 0.5 and -0.4.
        double aValue = normalize( _attackTwo.getRawAxis( 2 ), _ARMMIN, _ARMMAX, _ARMCENTER );
        aValue = Utility.limitRange( aValue, SmartDashboard.getNumber( " Arm Up-Speed: " ) , SmartDashboard.getNumber( " Arm Dowm-Speed: " ) );
        
        //Returns the arm value.
        return aValue;
    }
    
    /**
     * Gets the primary (Joystick One Trigger) button state.
     * 
     * @return Returns the primary button state.
     */
    public boolean getPriButton()
    {
        //Returns state of Joystick One Trigger.
        boolean state = _attackOne.getRawButton( 1 );
        return state;
    }
    
    /**
     * Gets the secondary (Joystick Two Trigger) button state.
     * 
     * @return Returns the secondary button state.
     */
    public boolean getSecButton()
    {
        //Returns state of Joystick Two Trigger.
        boolean state = _attackTwo.getRawButton( 1 );
        return state;
    }
    
    /**
     * Gets the third button state.
     * 
     * @return Returns the third button state.
     */
    public boolean getThirdButton()
    {
        //Returns state of the third button.
        boolean state = _attackOne.getRawButton( 2 );
        return state;
    }

    /**
     * Gets the fourth button state.
     * 
     * @return Returns the fourth button state.
     */
    public boolean getFourthButton()
    {
        //Returns state of the fourth button.
        boolean state = _attackTwo.getRawButton( 2 );
        return state;
    }
    
    /**
     * Gets the fifth button state.
     * 
     * @return Returns the fifth button state.
     */
    public boolean getFifthButton()
    {
        //Returns state of the fifth button.
        boolean state = _attackOne.getRawButton( 4 );
        return state;
    }

    /**
     * Gets the sixth button state.
     * 
     * @return Returns the sixth button state.
     */
    public boolean getSixthButton()
    {
        //Returns state of the fifth button.
        boolean state = _attackOne.getRawButton( 3 );
        return state;
    }

    /**
     * Gets the seventh button state.
     * 
     * @return Returns the seventh button state.
     */
    public boolean getSeventhButton()
    {
        //Returns state of the sixth button.
        boolean state = _attackTwo.getRawButton( 3 );
        return state;
    }
}