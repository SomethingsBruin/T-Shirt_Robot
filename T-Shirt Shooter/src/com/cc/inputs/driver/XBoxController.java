package com.cc.inputs.driver;

import com.cc.utility.Utility;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**                                                                                                                               
 * Class representing XBox Controller.
 */
public class XBoxController extends Driver
{
   //The contoller's X constants.
    private final double _XMAX = 0.95;
    private final double _XMIN = -0.95;
    private final double _XCENTER = 0.0;
    
    //The contoller's Y constants. Y Max and Min are switched for normalization process.
    private final double _YMAX = 0.95;
    private final double _YMIN = -0.95;
    private final double _YCENTER = 0;
    
    //The contoller's Rotation constants.
    private final double _ROTMAX = 0.95;
    private final double _ROTMIN = -0.95;
    private final double _ROTCENTER = 0.0;
    
    //The contoller's Arm constants.
    private final double _ARMMAX = -1.0;
    private final double _ARMMIN = 1.0;
    private final double _ARMCENTER = 0.0;
    
    //The joystick of the controller.
    private Joystick _joy;
    
    /**
     * Creates the XBox Controller driver object.
     */
    private XBoxController()
    {
        //Calls the contructor in Dirver.
        super();
        
        //Initializes the joysticks on channel 1 and 2.
        _joy = new Joystick( 1 );
    }
    
    /**
     * Returns the singleton instance of the driver.
     * 
     * @return Returns the singleton instance of the driver.
     */
    public static Driver getInstance()
    {
        //If the dirver is not an XBox Controller, than make it an XBoxController
        if( !( _instance instanceof XBoxController ) )
        {
            _instance = new XBoxController();
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
            System.out.print( i + " One: " + _joy.getRawAxis( i ) + " " );
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
            System.out.print( i + " One: " + _joy.getRawButton( i ) + " " );
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
        double xValue = normalize( _joy.getRawAxis( 4 ), _XMIN, _XMAX, _XCENTER );
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
        double yValue = -1 * normalize( _joy.getRawAxis( 5 ), _YMIN, _YMAX, _YCENTER );//Multiplies by -1 because the y-axis is inverted.
        yValue = Utility.expo( yValue, SmartDashboard.getNumber( " Driver Expo: " ) );
        
        //Returns the y value.
        return yValue;
    }
    
    /**
     * Gets the rotation value of the controller.
     * 
     * @return Returns the rotation value.
     */
    public double getRot()
    {
        //Finds the normalized rotation value of the controller, and then expos it.
        double rValue = normalize( _joy.getRawAxis( 1 ), _ROTMIN, _ROTMAX, _ROTCENTER );
        rValue = Utility.expo( rValue, SmartDashboard.getNumber( " Driver Expo: " ) );
        
//        //If going backwards...
//        if( getY() < -SmartDashboard.getNumber( " Rot Dead-Zone: " ) )
//        {
//            //Then reverse the rotation value.
//            rValue *= -1;
//        }
        
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
        //Finds the normalized arm value of the controller and limit range between 0.5 and -0.4.
        double aValue = normalize( _joy.getRawAxis( 3 ), _ARMMIN, _ARMMAX, _ARMCENTER );
        aValue = Utility.limitRange( aValue, SmartDashboard.getNumber( " Arm Up-Speed: " ), SmartDashboard.getNumber( " Arm Down-Speed: " ) );
        
        //Returns the arm value.
        return aValue;
    }
    
    /**
     * Gets the primary (Blue) button state.
     * 
     * @return Returns the primary button state.
     */
    public boolean getPriButton()
    {
        //Returns state of the blue button.
        boolean state = _joy.getRawButton( 4 );
        return state;
    }
    
    /**
     * Gets the secondary (Green) button state.
     * 
     * @return Returns the secondary button state.
     */
    public boolean getSecButton()
    {
        //Returns state of the green button.
        boolean state = _joy.getRawButton( 3 );
        return state;
    }     

    /**
     * Gets the fifth (Yellow) button state.
     * 
     * @return Returns the third button state.
     */
    public boolean getThirdButton()
    {
        //Returns state of the yellow button.
        boolean state = _joy.getRawButton( 1 );
        return state;
    }

    /**
     * Gets the fourth (Red) button state.
     * 
     * @return Returns the fourth button state.
     */
    public boolean getFourthButton()
    {
        //Returns state of the red button.
        boolean state = _joy.getRawButton( 2 );
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
        boolean state = _joy.getRawButton( 7 );
        return state;
    }

    /**
     * Gets the sixth (Right Trigger) button state.
     * 
     * @return Returns the sixth button state.
     */
    public boolean getSixthButton()
    {
        //Returns state of the right trigger.
        boolean state = _joy.getRawButton( 6 );
        return state;
    }

    /**
     * Gets the seventh (Left Trigger) button state.
     * 
     * @return Returns the seventh button state.
     */
    public boolean getSeventhButton()
    {
        //Returns state of the left trigger.
        boolean state = _joy.getRawButton( 5 );
        return state;
    }
}