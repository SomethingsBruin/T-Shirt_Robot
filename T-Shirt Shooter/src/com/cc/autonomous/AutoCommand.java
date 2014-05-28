package com.cc.autonomous;

import com.cc.systems.Chassis;
import com.cc.systems.Mechanism;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The abstract class which represents any command that is called for Autonomous
 * but does not define any particular auto command.
 */
public abstract class AutoCommand 
{
    //The chassis that is used in autonomous.
    protected Chassis _chassis;
    
    //The mechanism that is used in autonomous.
    protected Mechanism _mechanism;
    
    //The network table to get the number of blobs from the camera.
    protected NetworkTable _table;
    
    /**
     * Default constructor for an AutoCommand that will get the singleton objects
     * of the chassis and the mechanism.
     */
    public AutoCommand()
    {
        //Gets the singleton of the chassis and the mechanism.
        _chassis = Chassis.getInstance();
        _mechanism = Mechanism.getInstance();
        
        //Sets the network table to the one from RoboRealm.
        _table = NetworkTable.getTable( "" );
    }
    
    /**
     * The method which runs the given AutoCommand.
     */
    public abstract void runAutoCommand();
    
    /**
     * Finds the number of blobs from the camera.
     * 
     * @return The number of blobs from RoboRealm.
     */
    protected double getNumBlobs()
    {
        //The number of blobs the camera is reading.
        //double blobs = _table.getNumber( "BLOB_COUNT" );
        double blobs = 2;
        
        //Returns the number of blobs.
        return blobs;
    }
}