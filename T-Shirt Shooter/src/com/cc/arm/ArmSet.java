package com.cc.arm;

import com.cc.systems.Mechanism;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sets the arm to the given state, either the bottom extreme, the upper extreme,
 * or in the middle.
 */
public class ArmSet extends Thread
{
    //The mechanism object in the the thread.
    private Mechanism _mechanism;
    
    //The state that the arm will be set to.
    //0 = bottom ; 1 = top ; 2 = middle.
    private int _state;
    
    /**
     * Creates the thread that sets the mechanism to the given arm.
     * 
     * @param state 
     */
    public ArmSet( int state )
    {
        //Sets the state to the given state.
        _state = state;
        
        //Gets the singleton version of the Mechanism.
        _mechanism = Mechanism.getInstance();
    }
    
    /**
     * Sets the arm to the given state.
     */
    public void run()
    {
        //Change the operation of the thread based off the given state.
        switch( _state )
        {
            case 0://The state to set the arm to the bottom extreme.
                
                //Lowers the arm until the potentiometer reads the lowest state.
                while( _mechanism.getPotent() <= SmartDashboard.getNumber( " Arm Minimum Extreme: " ) )
                {
                    _mechanism.lowerArm( SmartDashboard.getNumber( " Arm Down-Speed: " ) );
                }
                
                break;
                
            case 1://The state to set the arm to the top extreme.
                
                //Raises the arm until the potentimenter reades the highest state.
                while( _mechanism.getPotent() >= SmartDashboard.getNumber( " Arm Maximum Extreme: " ) )
                {
                    _mechanism.raiseArm( -SmartDashboard.getNumber( " Arm Up-Speed: " ) );
                }
                
                break;
                
            case 2://The state to set the arm to the middle.
                
                //If the arm is currently above the middle position...
                if( _mechanism.getPotent() < SmartDashboard.getNumber( " Arm Middle Up Position: " ) )
                {
                    //Lower the arm until in reaches the middle position.
                    while( _mechanism.getPotent() < SmartDashboard.getNumber( " Arm Middle Down Position: " ) )
                    {
                        _mechanism.lowerArm( -SmartDashboard.getNumber( " Arm Middle Down Speed: " ) );
                    }
                }
                else//Else if the arm is lower than the middle position.
                {
                    //Raise the are until in reaches the middle position.
                    while( _mechanism.getPotent() > SmartDashboard.getNumber( " Arm Middle Up Position: " ) )
                    {
                        _mechanism.raiseArm( -SmartDashboard.getNumber( " Arm Middle Up Speed: " ) );
                    }
                }
                
                break;           
        }
    }
}
